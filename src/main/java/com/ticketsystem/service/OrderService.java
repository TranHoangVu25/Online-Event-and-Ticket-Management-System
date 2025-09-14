package com.ticketsystem.service;

import com.ticketsystem.dto.request.OrderRequest;
import com.ticketsystem.entity.Order;
import com.ticketsystem.mapper.OrderMapper;
import com.ticketsystem.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    OrderRepository orderRepository;
    OrderMapper orderMapper;

    public Order createOrder(OrderRequest request){
        Order order = orderMapper.toOrder(request);
        return orderRepository.save(order);
    }
}
