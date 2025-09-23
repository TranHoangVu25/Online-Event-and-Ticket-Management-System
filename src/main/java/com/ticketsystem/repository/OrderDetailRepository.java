package com.ticketsystem.repository;

import com.ticketsystem.dto.response.FormOrderDetailResponse;
import com.ticketsystem.dto.response.OrderDetailResponse;
import com.ticketsystem.entity.OrderDetail;
import com.ticketsystem.entity.OrderDetailId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, OrderDetailId> {
    @Query(value = """
 SELECT new com.ticketsystem.dto.response.FormOrderDetailResponse(
        o.id,
        CAST(o.createdAt AS java.time.LocalDateTime),
        e.name,
        e.thumbnailUrl,
        CAST(e.startTime AS java.time.LocalDateTime),
        l.addressDetail,
        tc.name,
        od.quantity,
        o.totalAmount
    )
    FROM OrderDetail od
    JOIN od.order o
    JOIN od.ticketClass tc
    JOIN tc.event e
    JOIN e.location l
    WHERE o.id = :orderId AND tc.id = :ticketClassId
""")
    FormOrderDetailResponse findOrderDetailInfoNative(
            @Param("orderId") Integer orderId,
            @Param("ticketClassId") Integer ticketClassId
    );

    @Query("""
    SELECT new com.ticketsystem.dto.response.OrderDetailResponse(
        od.id,
        od.quantity,
        od.price
    )
    FROM OrderDetail od
    JOIN od.order o
    WHERE o.user.id = :userId
""")
    List<OrderDetailResponse> findByUserId(@Param("userId") Integer userId);


}
