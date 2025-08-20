package com.ticketsystem.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "ticket_class")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PACKAGE)
public class TicketClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    Event event;

    @Column(nullable = false, length = 100)
    String name;

    @Column(nullable = false, precision = 18, scale = 0)
    BigDecimal price;

    @Column(name = "total_quantity", nullable = false)
    int totalQuantity;

    @Column(name = "sold_quantity", nullable = false)
    int soldQuantity = 0;

    @OneToMany(mappedBy = "ticketClass")
    Set<OrderDetail> orderDetails;
}