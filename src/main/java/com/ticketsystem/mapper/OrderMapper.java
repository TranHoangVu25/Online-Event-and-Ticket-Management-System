package com.ticketsystem.mapper;


import com.ticketsystem.dto.request.OrderCreationRequest;
import com.ticketsystem.dto.response.OrderResponse;
import com.ticketsystem.entity.Order;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    Order toOrder(OrderCreationRequest request);
    OrderResponse toOrderResponse(Order order);
//    void updateOrder(@MappingTarget Order order, OrderCreationRequest request);
}
