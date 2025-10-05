package com.ticketsystem.controller.admin;

import com.ticketsystem.dto.response.FormOrderDetailResponse;
import com.ticketsystem.dto.response.OrderInformationResponse;
import com.ticketsystem.entity.Order;
import com.ticketsystem.entity.User;
import com.ticketsystem.service.OrderDetailService;
import com.ticketsystem.service.OrderService;
import com.ticketsystem.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.Normalizer;
import java.util.List;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminOrderController {
    UserService userService;
    OrderService orderService;
    OrderDetailService orderDetailService;

    @GetMapping("/admin-orders")
    public String getOrder(Model model){
        List<OrderInformationResponse> orderForm = orderService.getAllOrder();
        model.addAttribute("orderForm",orderForm);
        return "/admin/admin-orders";
    }
    @GetMapping("admin-order-detail/{orderId}/{ticketClassId}")
    @ResponseBody
    public FormOrderDetailResponse getOrderDetailById(
            @PathVariable int orderId,
            @PathVariable int ticketClassId
    ){
        FormOrderDetailResponse response = orderDetailService.getOrderDetailById(orderId,ticketClassId);
        log.info("Order's detail information:"+response);
        return response;
    }

    @PostMapping("admin-order-confirm/{orderId}")
    public String confirmOder(
            @PathVariable int orderId
    ){
        orderService.confirmOrder(orderId);
        return "redirect:/admin/admin-orders";
    }

    @PostMapping("admin-order-cancel/{orderId}")
    public String cancelOrder(
            @PathVariable int orderId
    ){
        orderService.cancelOrder(orderId);
        log.info("in cancel Order controller");
        return "redirect:/admin/admin-orders";
    }
}
