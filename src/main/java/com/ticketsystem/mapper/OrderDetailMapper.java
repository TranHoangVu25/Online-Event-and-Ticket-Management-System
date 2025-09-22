package com.ticketsystem.mapper;


import com.ticketsystem.dto.request.OrderCreationRequest;
import com.ticketsystem.dto.request.OrderDetailRequest;
import com.ticketsystem.dto.response.OrderDetailResponse;
import com.ticketsystem.dto.response.OrderResponse;
import com.ticketsystem.entity.Order;
import com.ticketsystem.entity.OrderDetail;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {

    OrderDetail toOrderDetail(OrderDetailRequest request);
    OrderDetailResponse toOrderDetailResponse(OrderDetail orderDetail);
    void updateOrder(@MappingTarget Order order, OrderCreationRequest request);
}
