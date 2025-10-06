package com.ticketsystem.dto.response;

import com.ticketsystem.dto.RevenueDTO;
import lombok.Data;

import java.util.List;

@Data
public class RevenueResponse {
    private List<RevenueDTO> monthlyRevenue;
    private Double totalRevenue;
    private String period;
    private String message;
}