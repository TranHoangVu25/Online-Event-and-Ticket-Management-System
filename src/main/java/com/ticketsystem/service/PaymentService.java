package com.ticketsystem.service;
import com.ticketsystem.entity.Order;
import com.ticketsystem.entity.Payment;
import com.ticketsystem.repository.PaymentRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE,makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    PaymentRepository paymentRepository;

    public Payment createPayment(Order order,String method){
        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setAmount(order.getTotalAmount());
        payment.setCreatedAt(LocalDateTime.now());
        payment.setStatus(1);
        payment.setMethod(method);
        return paymentRepository.save(payment);
    }
}
