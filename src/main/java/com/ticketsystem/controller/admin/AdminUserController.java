package com.ticketsystem.controller.admin;

import com.ticketsystem.dto.request.UserCreationRequest;
import com.ticketsystem.dto.request.UserUpdateRequest;
import com.ticketsystem.dto.response.UserResponse;
import com.ticketsystem.dto.response.UserResponse1;
import com.ticketsystem.entity.User;
import com.ticketsystem.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminUserController {
    UserService userService;
    @GetMapping("/admin-users")
    public String getUser(Model model){
        List<UserResponse> users = userService.getUsers();
        model.addAttribute("users",users);
        model.addAttribute("userCreation",new UserCreationRequest());
        model.addAttribute("userUpdate", new UserUpdateRequest()); // thêm dòng này
        return "admin/admin-users";
    }

//    @GetMapping("/admin-users/{id}")
//    public String getUserById(@PathVariable int id,Model model){
//        UserResponse userUpdate = userService.getUser(id);
//        model.addAttribute("userUpdate",userUpdate);
//        return "/admin/admin-users";
//    }

    @GetMapping("/admin-users/{id}")
    @ResponseBody
    public UserResponse1 getUserById(@PathVariable int id) {
        return userService.getUser(id);
    }


    @PostMapping("/admin-create-users")
    public String createUser(
            @ModelAttribute("userCreation") @Valid UserCreationRequest request) throws Exception {
            userService.createUser(request);
            return "redirect:/admin/admin-users";
    }

    @PostMapping("/admin-update-user/{id}")
    public String updateUser(@PathVariable int id,
                             @Valid UserUpdateRequest request,
                             Model model){
        UserResponse userUpdate = userService.updateUser(request,id);
        model.addAttribute("userUpdate", userUpdate);
        model.addAttribute("id", id);
        return "redirect:/admin/admin-users";
    }
}
