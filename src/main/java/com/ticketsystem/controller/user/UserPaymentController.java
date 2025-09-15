package com.ticketsystem.controller.user;

import com.ticketsystem.dto.response.EventFormBuyTicket;
import com.ticketsystem.dto.response.EventResponse;
import com.ticketsystem.dto.response.TicketClassResponse;
import com.ticketsystem.service.EventService;
import com.ticketsystem.service.TicketClassService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.List;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserPaymentController {
    EventService eventService;
    TicketClassService ticketClassService;

//    @GetMapping("/payment")
//    String getPayment(){
//        return "customer/payment";
//    }
}
