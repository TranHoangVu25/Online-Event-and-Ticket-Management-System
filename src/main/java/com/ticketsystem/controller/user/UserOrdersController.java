//package com.ticketsystem.controller.user;
//
//import com.ticketsystem.dto.request.OrderRequest;
//import com.ticketsystem.dto.response.EventFormBuyTicket;
//import com.ticketsystem.dto.response.EventResponse;
//import com.ticketsystem.dto.response.TicketClassResponse;
//import com.ticketsystem.entity.Order;
//import com.ticketsystem.service.EventService;
//import com.ticketsystem.service.OrderService;
//import com.ticketsystem.service.TicketClassService;
//import lombok.AccessLevel;
//import lombok.RequiredArgsConstructor;
//import lombok.experimental.FieldDefaults;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//@Controller
//@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
//@Slf4j
//@RequiredArgsConstructor
//@RequestMapping("/user")
//public class UserOrdersController {
//    OrderService orderService;
//    EventService eventService;
//    TicketClassService ticketClassService;
//
//    @GetMapping("/buy-ticket/{id}")
//    public String showBuyTicket(@PathVariable int id, Model model) {
//        EventResponse event = eventService.getEvent(id);
//        List<TicketClassResponse> ticketClassResponses = ticketClassService.getTicketClasses(id);
//        BigDecimal totalPrice = ticketClassService.totalPrice1(ticketClassResponses.get(0));
//        List<Integer> remainTicket = ticketClassService.calculateRemainTicket(ticketClassResponses);
//        EventFormBuyTicket eventForm = new EventFormBuyTicket(event,ticketClassResponses,totalPrice,remainTicket);
//        model.addAttribute("eventForm", eventForm);
//        return "customer/buy-ticket";
//    }
//
//    @GetMapping("/customer-orders")
//    String getPayment(Model model){
//
//        model.addAttribute("order-request",new OrderRequest());
//        return "customer/customer-orders";
//    }
//    @PostMapping("/customer-orders/{userId}")
//    public String createOrder(@PathVariable int userId,
//                              @ModelAttribute("orderRequest") OrderRequest request,
//                              Model model) {
//        Order order = orderService.createOrder(request, userId);
//
//        model.addAttribute("order", order);
//        return "orders/order-success";
//    }
//}
