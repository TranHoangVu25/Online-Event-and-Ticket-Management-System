package com.ticketsystem.controller.admin;

import com.ticketsystem.entity.Order;
import com.ticketsystem.entity.User;
import com.ticketsystem.service.OrderService;
import com.ticketsystem.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminOrderController {
    UserService userService;
    OrderService orderService;

    @GetMapping("/admin-orders")
    public String getOrder(Model model){
        List<Order> orders = orderService.getAllOrder();
        model.addAttribute(orders);
        return "/admin/admin-orders";
    }
}
