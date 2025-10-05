package com.ticketsystem.repository;

import com.ticketsystem.dto.response.FormOrderDetailResponse;
import com.ticketsystem.dto.response.UserOderResponse;
import com.ticketsystem.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    @Query(value = """
 SELECT new com.ticketsystem.dto.response.UserOderResponse(
        o.id,
        o.createdAt,
        e.name,
        e.thumbnailUrl,
        e.startTime,
        tc.name,
        tc.id,
        od.quantity,
        o.totalAmount
    )
    FROM OrderDetail od
    JOIN od.order o
    JOIN od.ticketClass tc
    JOIN tc.event e
    JOIN e.location l
    WHERE o.user.id = :userId
""")
    List<UserOderResponse> findAllOrder(
            @Param("userId") Integer userId
    );
}
