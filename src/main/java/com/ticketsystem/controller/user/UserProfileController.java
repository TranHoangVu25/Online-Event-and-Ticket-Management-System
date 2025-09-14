package com.ticketsystem.controller.user;

import com.ticketsystem.dto.request.UserUpdateRequest;
import com.ticketsystem.dto.response.UserResponse;
import com.ticketsystem.entity.User;
import com.ticketsystem.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserProfileController {
    UserService userService;

    @GetMapping("/profile/{userName}")
    public String getUserProfile(@PathVariable String userName,  HttpSession session, Model model){
        UserResponse user = userService.getUserByUserName(userName);
        model.addAttribute("user",user);
        return "/customer/customer-profile";
    }
    @PostMapping("/profile/{userName}")
    public String updateUserProfile(@PathVariable String userName,
                                    @Valid @ModelAttribute UserUpdateRequest request){
        UserResponse user = userService.getUserByUserName(userName);
        userService.updateUser(request,user.getId());
        return "redirect:/user/profile/"+userName;
    }
}
