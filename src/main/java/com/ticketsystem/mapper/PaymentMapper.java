package com.ticketsystem.mapper;


import com.ticketsystem.dto.request.OrderRequest;
import com.ticketsystem.dto.request.PaymentRequest;
import com.ticketsystem.dto.response.OrderResponse;
import com.ticketsystem.dto.response.PaymentResponse;
import com.ticketsystem.entity.Order;
import com.ticketsystem.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(target = "order",ignore = true)
    @Mapping(target = "status",ignore = true)
    Payment toPayment(PaymentRequest request);
    PaymentResponse toPaymentResponse(Payment Payment);
    void updatePayment(@MappingTarget Payment Payment, PaymentRequest request);
}
