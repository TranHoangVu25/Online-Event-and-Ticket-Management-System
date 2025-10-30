package com.ticketsystem.controller;

import com.ticketsystem.config.CustomJwtDecoder;
import com.ticketsystem.dto.request.AuthenticationRequest;
import com.ticketsystem.dto.request.ForgotPasswordDTO;
import com.ticketsystem.dto.request.UserCreationRequest;
import com.ticketsystem.dto.response.AuthenticationResponse;
import com.ticketsystem.dto.response.UserResponse;
import com.ticketsystem.dto.response.UserResponse1;
import com.ticketsystem.entity.User;
import com.ticketsystem.repository.UserRepository;
import com.ticketsystem.service.AuthService;
import com.ticketsystem.service.AuthenticationService;
import com.ticketsystem.service.SpamProtectionService;
import com.ticketsystem.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
@RequiredArgsConstructor
public class AuthenticateController {
    UserService userService;
    UserRepository userRepository;
    AuthenticationService authenticationService;
    CustomJwtDecoder customJwtDecoder;
    SpamProtectionService spamProtectionService;
    AuthService authService;

    @GetMapping("/login")
    String login(Model model){
        model.addAttribute("request", new AuthenticationRequest());
        return "login";
    }

    @PostMapping("/login")
    String getLogin(HttpSession session,
                    @ModelAttribute("request") AuthenticationRequest request,
                    Model model)
            throws Exception {
        try {
            AuthenticationResponse response = authenticationService.authenticate(request);
            String jwt = response.getToken();
            session.setAttribute("jwt",jwt);
//            log.info("jwt====="+jwt);
            Jwt decodedJwt  = customJwtDecoder.decode(jwt);
            String scope = decodedJwt.getClaimAsString("scope");
            String userName = decodedJwt.getClaimAsString("sub");
            String full_name = decodedJwt.getClaimAsString("full_name");
            int userId = Integer.parseInt(decodedJwt.getClaimAsString("userId"));

            UserResponse1 user = userService.getUser(userId);

            log.info("must ========="+ user.isMustChangePassword());

            session.setAttribute("userName",userName);
            session.setAttribute("userId", userId);
            session.setAttribute("full_name",full_name);

            if (scope.contains("ROLE_ADMIN")){
                LocalDateTime login_time = LocalDateTime.now();
                User user1 = userRepository.findById(userId)
                        .orElseThrow(()-> new RuntimeException("In login. User not found"));
                user1.setLastLogin(login_time);
                userRepository.save(user1);
                return "redirect:/admin-home";
            }
//            else if (){
            else if (user.isMustChangePassword() && scope.contains("ROLE_USER")){
                User userEntity = userRepository.findById(user.getId())
                        .orElseThrow(() -> new RuntimeException("User not found"));

                userEntity.setMustChangePassword(false);
                userRepository.save(userEntity);
                return "redirect:/user/change-password";
            }
            else return "redirect:/home-page";
        }catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "login";
        }
    }

    @GetMapping("/register")
    String getFormRegister(Model model){
        model.addAttribute("user",new UserCreationRequest());
        return "register";
    }

    @PostMapping("/register")
    String register(@Valid @ModelAttribute("user")  UserCreationRequest user,
                    BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors()){
            return "register";
        }
        if(userRepository.existsByUsername(user.getUsername())){
            bindingResult.rejectValue("username","error_useName","Username đã tồn tại");
            return "register";
        }
        if(userRepository.existsByEmail(user.getEmail())){
            bindingResult.rejectValue("email","error_email","Email đã tồn tại");
            return "register";
        }
        userService.createUser(user);
        return "redirect:/login";
    }

    @GetMapping("/forgot-password")
    public String showForgotPassword(Model model) {
        model.addAttribute("forgotDTO", new ForgotPasswordDTO());
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    @ResponseBody
    public ResponseEntity<Map<String, String>> doForgot(
            @Valid @RequestBody ForgotPasswordDTO forgotDTO,
            BindingResult br,
            HttpServletRequest request) {

        log.info("in forgot password ================");
        Map<String, String> response = new HashMap<>();
        String ipAddress = request.getRemoteAddr();

        if (spamProtectionService.isBlocked(ipAddress)) {
            response.put("mess", "Bạn đã yêu cầu quá nhiều lần. Vui lòng thử lại sau 15 phút.");
            response.put("type", "error");
            return ResponseEntity.status(429).body(response);
        }

        if (br.hasErrors()) {
            response.put("mess", "Dữ liệu không hợp lệ");
            response.put("type", "error");
            return ResponseEntity.badRequest().body(response);
        }

        try {
            String message = authService.forgotPassword(forgotDTO);
            spamProtectionService.recordRequest(ipAddress);
            response.put("mess", message);
            response.put("type", "success");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("mess", e.getMessage());
            response.put("type", "error");
            return ResponseEntity.badRequest().body(response);
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Xóa toàn bộ session, bao gồm cả "loggedInUser"
        session.invalidate();
        // Chuyển hướng về trang chủ
        return "redirect:/login";
    }
}
