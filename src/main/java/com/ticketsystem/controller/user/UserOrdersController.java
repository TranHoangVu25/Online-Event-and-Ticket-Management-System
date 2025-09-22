package com.ticketsystem.controller.user;

import com.ticketsystem.dto.request.OrderCreationRequest;
import com.ticketsystem.dto.response.*;
import com.ticketsystem.entity.Order;
import com.ticketsystem.entity.Payment;
import com.ticketsystem.entity.TicketClass;
import com.ticketsystem.service.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserOrdersController {
    OrderService orderService;
    EventService eventService;
    TicketClassService ticketClassService;
    PaymentService paymentService;
    OrderDetailService orderDetailService;


    @GetMapping("/customer-orders")
    String getPayment(Model model){
        List<FormUserOrderResponse> orderForm = new ArrayList<>();
        List<OrderDetailResponse> orderDetails = orderDetailService.getOrderDetails();

        for (OrderDetailResponse orderDetail : orderDetails) {
            TicketClass ticketClass = ticketClassService.getTicketClass(orderDetail.getId().getTicketClassId());
            Order order = orderService.getOrderById(orderDetail.getId().getOrderId());
            orderForm.add(new FormUserOrderResponse(ticketClass, order, orderDetail));
        }
        model.addAttribute("order-request",new OrderCreationRequest());
        model.addAttribute("orderForm",orderForm);
        return "customer/customer-orders";
    }

    @PostMapping("/checkout/{userId}")
    public String checkout(@PathVariable int userId,
                           @ModelAttribute OrderCreationRequest request,
                           @ModelAttribute("eventForm") EventFormBuyTicket eventForm,
                           RedirectAttributes redirectAttributes) {

        log.info("Payment method: {}", request.getMethod());
        request.getTickets().forEach((ticketClassId, qty) ->
                log.info("TicketClassId: {}, Quantity: {}", ticketClassId, qty)
        );

        // Tạo order
        Order order = orderService.createOrder(request, userId);

        // Tạo payment
        Payment payment = paymentService.createPayment(order, request.getMethod());

        redirectAttributes.addFlashAttribute("successMessage", "Thanh toán thành công!");
        return "redirect:/user/main-event";
    }

}
