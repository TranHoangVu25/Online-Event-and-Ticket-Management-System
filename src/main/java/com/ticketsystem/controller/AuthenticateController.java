package com.ticketsystem.controller;

import com.ticketsystem.dto.request.AuthenticationRequest;
import com.ticketsystem.dto.request.UserCreationRequest;
import com.ticketsystem.dto.response.AuthenticationResponse;
import com.ticketsystem.repository.UserRepository;
import com.ticketsystem.service.AuthenticationService;
import com.ticketsystem.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
@RequiredArgsConstructor
public class AuthenticateController {
    UserService userService;
    UserRepository userRepository;
    AuthenticationService authenticationService;

    @GetMapping("/login")
    String login(Model model){
        model.addAttribute("request", new AuthenticationRequest());
        return "login";
    }

    @PostMapping("/login")
    String getLogin(Model model,@ModelAttribute("request") AuthenticationRequest request) throws Exception {
        try {
            AuthenticationResponse response = authenticationService.authenticate(request);
            String jwt = response.getToken();
            log.info("JWT generated: {}", jwt);
            model.addAttribute("jwt",jwt);
            return "redirect:/test?token=" + jwt;
        }catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "login"; // quay lại login nếu sai
        }
    }

    @GetMapping("/test")
    public String testPage(@RequestParam(required = false) String token, Model model) {
        if (token != null) {
            model.addAttribute("jwt", token);
        } else {
            model.addAttribute("jwt", "Chưa có token, hãy đăng nhập!");
        }
        return "test"; // test.html
    }
    @GetMapping("/register")
    String getFormRegister(Model model){
        model.addAttribute("user",new UserCreationRequest());
        return "register";
    }

    @PostMapping("/register")
    String register(@Valid @ModelAttribute("user")  UserCreationRequest user,
                    BindingResult bindingResult) throws Exception {
        log.info(user.getEmail());
        log.info(user.getFullName());
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
}
