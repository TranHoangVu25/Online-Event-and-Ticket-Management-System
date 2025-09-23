package com.ticketsystem.controller;

import com.nimbusds.jwt.SignedJWT;
import com.ticketsystem.config.CustomJwtDecoder;
import com.ticketsystem.dto.request.AuthenticationRequest;
import com.ticketsystem.dto.request.UserCreationRequest;
import com.ticketsystem.dto.response.AuthenticationResponse;
import com.ticketsystem.dto.response.UserResponse;
import com.ticketsystem.entity.User;
import com.ticketsystem.repository.UserRepository;
import com.ticketsystem.service.AuthenticationService;
import com.ticketsystem.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
@RequiredArgsConstructor
public class AuthenticateController {
    UserService userService;
    UserRepository userRepository;
    AuthenticationService authenticationService;
    CustomJwtDecoder customJwtDecoder;

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
            Jwt decodedJwt  = customJwtDecoder.decode(jwt);
            String scope = decodedJwt.getClaimAsString("scope");
            String userName = decodedJwt.getClaimAsString("sub");
//            log.info("USER NAME:"+userName);
            session.setAttribute("userName",userName);
            Integer userId = userRepository.findIdByUsername(userName)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            session.setAttribute("userId", userId);
//                        log.info("USER ID:============"+userId);

            if (scope.contains("ROLE_ADMIN")){
                return "redirect:/admin/users";
            }
            else{
                return "redirect:/home-page";
            }
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
//        log.info(user.getEmail());
//        log.info(user.getFullName());
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
//        if(userRepository.existsByPhoneNumber(user.getPhoneNumber())){
//            bindingResult.rejectValue("phoneNumber","error_phoneNumber","Số điện thoại đã tồn tại");
//            return "register";
//        }
        userService.createUser(user);
        return "redirect:/login";
    }

    @GetMapping("/forgot-password")
    public String forgotPassword(){
        return "forgot-password";
    }
}
