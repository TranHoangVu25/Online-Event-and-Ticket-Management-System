package com.ticketsystem.service;

import com.ticketsystem.dto.request.PaymentRequest;
import com.ticketsystem.entity.Payment;
import com.ticketsystem.mapper.PaymentMapper;
import com.ticketsystem.repository.PaymentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    PaymentRepository paymentRepository;
    PaymentMapper paymentMapper;
    OrderService orderService;

    public Payment createPayment(PaymentRequest request){
        Payment payment = paymentMapper.toPayment(request);
        return paymentRepository.save(payment);
    }
}
