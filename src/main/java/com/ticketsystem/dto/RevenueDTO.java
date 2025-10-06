package com.ticketsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenueDTO {
    private Integer year;
    private Integer month;
    private Double revenue;

    public RevenueDTO(Integer year, Integer month, BigDecimal revenue) {
        this.year = year;
        this.month = month;
        this.revenue = revenue != null ? revenue.doubleValue() : 0.0;
    }
}