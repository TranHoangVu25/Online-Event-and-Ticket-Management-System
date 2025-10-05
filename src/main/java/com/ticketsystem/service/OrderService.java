package com.ticketsystem.service;

import com.ticketsystem.dto.request.OrderCreationRequest;
import com.ticketsystem.dto.response.OrderResponse;
import com.ticketsystem.dto.response.UserOderResponse;
import com.ticketsystem.entity.*;
import com.ticketsystem.mapper.OrderMapper;
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
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    TicketClassRepository ticketClassRepository;
    OrderRepository orderRepository;
    UserRepository userRepository;
    OrderMapper orderMapper;

    public Order createOrder(OrderCreationRequest requests, int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(BigDecimal.ZERO);
        order.setStatus(0); // 0 = pending, 1 = paid, 2 = cancelled...
        Order savedOrder = orderRepository.save(order);

        Set<OrderDetail> details = new HashSet<>();
        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<Integer, Integer> entry : requests.getTickets().entrySet()) {
            Integer ticketClassId = entry.getKey();
            Integer quantity = entry.getValue();

            TicketClass ticketClass = ticketClassRepository.findById(ticketClassId)
                    .orElseThrow(() -> new RuntimeException("TicketClass not found"));

            int remaining = ticketClass.getTotalQuantity() - ticketClass.getSoldQuantity();

            if ("COD".equalsIgnoreCase(requests.getMethod())) {
                if (remaining < quantity) {
                    throw new RuntimeException("Not enough tickets available");
                }
                // 🔑 Cập nhật soldQuantity thay vì remainingQuantity
                ticketClass.setSoldQuantity(ticketClass.getSoldQuantity() + quantity);
                ticketClassRepository.save(ticketClass);
                log.info("COD order → sold {} tickets for TicketClassId={}, now soldQuantity={}",
                        quantity, ticketClassId, ticketClass.getSoldQuantity());
            }

            OrderDetailId id = new OrderDetailId(savedOrder.getId(), ticketClass.getId());

            OrderDetail detail = new OrderDetail();
            detail.setId(id);
            detail.setOrder(savedOrder);
            detail.setTicketClass(ticketClass);
            detail.setQuantity(quantity);
            detail.setPrice(ticketClass.getPrice());

            details.add(detail);

            total = total.add(ticketClass.getPrice()
                    .multiply(BigDecimal.valueOf(quantity)));
        }

        savedOrder.setOrderDetails(details);
        savedOrder.setTotalAmount(total);
        return orderRepository.save(savedOrder);
    }

    public Order getOrderById(int id){
        return orderRepository.findById(id).orElseThrow(()-> new RuntimeException("id not found"));
    }
    public List<Order> getAllOrder(){
        return orderRepository.findAll();
    }

    public List<UserOderResponse> getAllOrderByUserId(Integer userId){
        return orderRepository.findAllOrder(userId);
    }
}
