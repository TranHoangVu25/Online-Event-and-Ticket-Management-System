package com.ticketsystem.service;

import com.ticketsystem.dto.request.OrderCreationRequest;
import com.ticketsystem.dto.response.FormOrderDetailResponse;
import com.ticketsystem.dto.response.OrderDetailResponse;
import com.ticketsystem.dto.response.OrderResponse;
import com.ticketsystem.entity.*;
import com.ticketsystem.mapper.OrderDetailMapper;
import com.ticketsystem.mapper.OrderMapper;
import com.ticketsystem.repository.OrderDetailRepository;
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
public class OrderDetailService {
    OrderDetailMapper orderDetailMapper;
    OrderDetailRepository orderDetailRepository;

    public List<OrderDetailResponse> getOrderDetails(){
        return orderDetailRepository.findAll().stream().map(orderDetailMapper::toOrderDetailResponse).toList();
    }

    public FormOrderDetailResponse getOrderDetailById( int orderId,int ticketId){
        return orderDetailRepository.findOrderDetailInfoNative(orderId,ticketId);
    }
}
