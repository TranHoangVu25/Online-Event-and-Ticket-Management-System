package com.ticketsystem.controller;

import com.ticketsystem.dto.request.UserCreationRequest;
import com.ticketsystem.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
@RequiredArgsConstructor
public class AuthenticateController {
    UserService userService;
    @GetMapping("/login")
    String login(){
        return "login";
    }

    @GetMapping("/register")
    String getFormRegister(Model model){
        model.addAttribute("user",new UserCreationRequest());
        return "register";
    }

    @PostMapping("/register")
    String register(@ModelAttribute("user") @Valid UserCreationRequest request) throws Exception {
        log.info(request.getEmail());
        log.info(request.getFullName());

        userService.createUser(request);
        return "redirect:/login";
    }
}
