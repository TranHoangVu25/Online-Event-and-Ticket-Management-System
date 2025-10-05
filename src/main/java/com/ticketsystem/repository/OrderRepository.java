package com.ticketsystem.repository;

import com.ticketsystem.dto.response.OrderInformationResponse;
import com.ticketsystem.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {

    //query hiển thị thông tin order trên màn user's order
    @Query(value = """
 SELECT new com.ticketsystem.dto.response.OrderInformationResponse(
        o.id,
        o.createdAt,
        e.name,
        e.thumbnailUrl,
        e.startTime,
        tc.name,
        tc.id,
        od.quantity,
        o.totalAmount,
        o.status,
        o.user.fullName
    )
    FROM OrderDetail od
    JOIN od.order o
    JOIN od.ticketClass tc
    JOIN tc.event e
    JOIN e.location l
    WHERE o.user.id = :userId
""")
    List<OrderInformationResponse> findAllOrderByUserId(
            @Param("userId") Integer userId
    );


    //query hiển thị thông tin order trên màn admin order
    @Query(value = """
 SELECT new com.ticketsystem.dto.response.OrderInformationResponse(
        o.id,
        o.createdAt,
        e.name,
        e.thumbnailUrl,
        e.startTime,
        tc.name,
        tc.id,
        od.quantity,
        o.totalAmount,
        o.status,
        o.user.fullName
    )
    FROM OrderDetail od
    JOIN od.order o
    JOIN od.ticketClass tc
    JOIN tc.event e
    JOIN e.location l
""")
    List<OrderInformationResponse> findAllOrder();
}
