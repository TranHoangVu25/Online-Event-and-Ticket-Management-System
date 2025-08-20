package com.ticketsystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "order_detail")
@Getter // Sử dụng Getter
@Setter // Sử dụng Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {

    @EmbeddedId
    private OrderDetailId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    @JoinColumn(name = "order_id")
    @JsonBackReference
    @ToString.Exclude // Quan trọng: Ngăn vòng lặp vô hạn trong toString()
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("ticketClassId")
    @JoinColumn(name = "ticket_class_id")
    @ToString.Exclude // Quan trọng: Ngăn vòng lặp vô hạn trong toString()
    private TicketClass ticketClass;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, precision = 18, scale = 0)
    private BigDecimal price;

    // **PHẦN SỬA LỖI QUAN TRỌNG NHẤT**
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDetail that = (OrderDetail) o;
        return Objects.equals(id, that.id); // So sánh trên ID (EmbeddedId)
    }

    @Override
    public int hashCode() {
        return Objects.hash(id); // Băm trên ID (EmbeddedId)
    }
}