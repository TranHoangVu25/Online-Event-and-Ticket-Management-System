package com.ticketsystem.controller.user;

import com.ticketsystem.dto.request.ChangePasswordRequest;
import com.ticketsystem.dto.request.UserUpdateRequest;
import com.ticketsystem.dto.response.UserResponse;
import com.ticketsystem.dto.response.UserResponse1;
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
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserProfileController {
    UserService userService;
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    AuthenticationService authenticationService;

    @GetMapping("/profile")
    public String getUserProfile(
                                 HttpSession session,
                                 Model model) {
        String userName = (String) session.getAttribute("userName");
        UserResponse user = userService.getUserByUserName(userName);
        model.addAttribute("user", user);
        return "/customer/customer-profile";
    }

    @PostMapping("/profile")
    public String updateUserProfile(
            @Valid @ModelAttribute UserUpdateRequest request,
            HttpSession session) {
        String userName = (String) session.getAttribute("userName");
        UserResponse user = userService.getUserByUserName(userName);
        userService.updateUser(request, user.getId());
        return "redirect:/user/profile/" + userName;
    }

    // GET: hiển thị form
    @GetMapping("/change-password")
    public String showChangePasswordForm(Model model, HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        UserResponse1 user = userService.getUser(userId);

        if (userId == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);

        model.addAttribute("changePasswordRequest", new ChangePasswordRequest());
        return "/customer/customer-change-password";
    }

    // POST: xử lý đổi mật khẩu
    @PostMapping("/change-password")
    public String handleChangePassword(
            @Valid @ModelAttribute("changePasswordRequest") ChangePasswordRequest request,
            BindingResult result,
            HttpSession session,
            Model model) {

        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not existed"));

        if (!authenticationService.checkPassword(request.getOldPassword(), user.getPasswordHash())) {
            result.rejectValue("oldPassword", "error.oldPassword", "Mật khẩu cũ không đúng");
        }

        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            result.rejectValue("confirmNewPassword", "error.confirmNewPassword", "Xác nhận mật khẩu không khớp");
        }

        if (result.hasErrors()) {
            model.addAttribute("changePasswordRequest", request);
            return "/customer/customer-change-password";
        }
        UserResponse1 user1 = userService.getUser(userId);

        userService.changePassword(request.getNewPassword(), userId);
        model.addAttribute("successMessage", "Đổi mật khẩu thành công!");
        model.addAttribute("user",user1);
        return "/customer/customer-change-password";
    }
}
