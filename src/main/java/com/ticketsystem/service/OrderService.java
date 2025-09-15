package com.ticketsystem.service;

import com.ticketsystem.dto.request.OrderRequest;
import com.ticketsystem.entity.*;
import com.ticketsystem.repository.OrderRepository;
import com.ticketsystem.repository.TicketClassRepository;
import com.ticketsystem.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    TicketClassRepository ticketClassRepository;
    OrderRepository orderRepository;
    UserRepository userRepository;

    public Order createOrder(OrderRequest requests,int userId){
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("Id not found"));
        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(BigDecimal.valueOf(0));
        Order savedOrder = orderRepository.save(order);

        Set<OrderDetail> details = new HashSet<>();
        BigDecimal total = BigDecimal.ZERO;
        for (Map.Entry<Integer,Integer> entry:requests.getTickets().entrySet()){
            TicketClass ticketClass = ticketClassRepository.findById(entry.getKey())
                    .orElseThrow(()-> new RuntimeException("Id not found"));
            OrderDetailId id = new OrderDetailId(savedOrder.getId(), ticketClass.getId());

            OrderDetail detail = new OrderDetail();
            detail.setId(id);
            detail.setOrder(savedOrder);
            detail.setTicketClass(ticketClass);
            detail.setQuantity(entry.getValue());
            detail.setPrice(ticketClass.getPrice());

            details.add(detail);

            total = total.add(ticketClass.getPrice()
                    .multiply(BigDecimal.valueOf(entry.getValue())));
        }
        savedOrder.setOrderDetails(details);
        savedOrder.setTotalAmount(total);
        return orderRepository.save(savedOrder);
    }
}
