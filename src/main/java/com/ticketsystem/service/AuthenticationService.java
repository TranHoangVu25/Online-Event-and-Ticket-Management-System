package com.ticketsystem.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.ticketsystem.dto.request.AuthenticationRequest;
import com.ticketsystem.dto.request.IntrospectRequest;
import com.ticketsystem.dto.response.AuthenticationResponse;
import com.ticketsystem.dto.response.IntrospectResponse;
import com.ticketsystem.entity.User;
import com.ticketsystem.repository.UserRepository;
import com.ticketsystem.utils.ErrorCode;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;

    @NonFinal //không bị inject contructor
    @Value("${jwt.signerKey}") //anotation này được sử dụng để đọc biến trong file .yaml
    //https://generate-random.org/
    protected String SIGN_KEY;

    @NonFinal
    @Value("${jwt.valid-duration}")
    protected Long VALID_DURATION;

    @NonFinal
    @Value("${jwt.refreshable-duration}")
    protected Long REFRESHABLE_DURATION;

    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        boolean isValid = true;
        //thêm khối try catch để nếu verifyToken trả về exception thì trả về false
        try {
            verifyToken(token, false);

        } catch (Exception e) {
            isValid = false;
        }
        return IntrospectResponse.builder()
                .valid(isValid)
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) throws Exception {
        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new Exception("USER_NOT_EXISTED"));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean authenticated = passwordEncoder.matches(request.getPassword(), user
                .getPasswordHash());

        if (!authenticated) {
            throw new Exception("UNAUTHENTICATED");
        }
        var token = generateToken(user);
        log.info(token);
        log.info("in authenticate service");
        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    private SignedJWT verifyToken(String token, boolean isRefresh) throws Exception {
        //Tạo đối tượng để xác minh chữ ký JWT bằng khóa bí mật SIGN_KEY
        JWSVerifier verifier = new MACVerifier(SIGN_KEY.getBytes());

        //Phân tích chuỗi token thành đối tượng SignedJWT để truy xuất header, payload và chữ ký
        SignedJWT signedJWT = SignedJWT.parse(token);

        // lấy ngày hết hạn của token
        Date expiryTime = (isRefresh) ?
                new Date(signedJWT.getJWTClaimsSet().getIssueTime()
                        .toInstant().plus(REFRESHABLE_DURATION, ChronoUnit.SECONDS).toEpochMilli())
                : signedJWT.getJWTClaimsSet().getExpirationTime();

        //kiểm tra token hợp lệ
        var verified = signedJWT.verify(verifier);

        if (!(verified && expiryTime.after(new Date()))) // time hết hạn sau time hiện tại
        {
            log.info("Token was expired");
            throw (new Exception(String.valueOf(ErrorCode.UNAUTHENTICATED)));
        }
//
//        //kiểm tra xem nếu token đã tồn tại trong bảng InvalidatedToken thì trả về lỗi
//        if(invalidatedTokenRepository.existsById(signedJWT.getJWTClaimsSet().getJWTID())){
//            log.info("jwt id was existed in InvalidatedToken table");
//            throw (new AppException(ErrorCode.UNAUTHENTICATED));
//        }
        return signedJWT;
    }

    private String generateToken(User user) {

        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("THV.com")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(VALID_DURATION, ChronoUnit.SECONDS).toEpochMilli()
                ))

                .claim("scope", buildScope(user))

                //#16 thêm vào ID của jwt để lưu trữ token gần nhất mới hết hạn trong db
                .jwtID(UUID.randomUUID().toString())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);
        try {
            jwsObject.sign(new MACSigner(SIGN_KEY.getBytes()));

            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Cannot create token ", e);
            throw new RuntimeException(e);
        }
    }

    //hàm thêm scope(role) vào trong jwt
    private String buildScope(User user) {
        StringJoiner stringJoiner = new StringJoiner(" ");

        if (user.getRole() != null) {
            stringJoiner.add("ROLE_" + user.getRole().getRoleName());
        }
        return stringJoiner.toString();
    }
}
