package com.ticketsystem.service;

import com.ticketsystem.dto.RevenueDTO;
import com.ticketsystem.dto.response.RevenueResponse;
import com.ticketsystem.repository.RevenueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RevenueService {

    private final RevenueRepository revenueRepository;

    public RevenueResponse getMonthlyRevenue(Integer year) {
        try {
            List<RevenueDTO> monthlyData = revenueRepository.findMonthlyRevenueByYear(year);

            Double total = monthlyData.stream()
                    .mapToDouble(RevenueDTO::getRevenue)
                    .sum();

            RevenueResponse response = new RevenueResponse();
            response.setMonthlyRevenue(monthlyData);
            response.setTotalRevenue(total);
            response.setPeriod("year");
            response.setMessage("Thành công");

            return response;
        } catch (Exception e) {
            RevenueResponse response = new RevenueResponse();
            response.setMessage("Lỗi khi lấy dữ liệu: " + e.getMessage());
            return response;
        }
    }

    public RevenueResponse getMonthlyRevenueByPeriod(String period) {
        try {
            LocalDateTime endDate = LocalDateTime.now();
            LocalDateTime startDate;

            switch (period.toLowerCase()) {
                case "month":
                    startDate = endDate.minusMonths(1);
                    break;
                case "year":
                    startDate = endDate.minusYears(1);
                    break;
                case "week":
                default:
                    startDate = endDate.minusWeeks(1);
                    break;
            }

            List<RevenueDTO> monthlyData = revenueRepository.findMonthlyRevenueByDateRange(startDate, endDate);

            // Nếu không có dữ liệu, trả về dữ liệu 12 tháng gần nhất
            if (monthlyData.isEmpty()) {
                monthlyData = revenueRepository.findLast12MonthsRevenue();
                period = "last12months";
            }

            Double total = monthlyData.stream()
                    .mapToDouble(RevenueDTO::getRevenue)
                    .sum();

            RevenueResponse response = new RevenueResponse();
            response.setMonthlyRevenue(monthlyData);
            response.setTotalRevenue(total);
            response.setPeriod(period);
            response.setMessage("Thành công");

            return response;
        } catch (Exception e) {
            RevenueResponse response = new RevenueResponse();
            response.setMessage("Lỗi khi lấy dữ liệu: " + e.getMessage());
            return response;
        }
    }

    public RevenueResponse getRevenueStatistics() {
        try {
            // Lấy dữ liệu 12 tháng gần nhất
            List<RevenueDTO> monthlyData = revenueRepository.findLast12MonthsRevenue();

            Double total = monthlyData.stream()
                    .mapToDouble(RevenueDTO::getRevenue)
                    .sum();

            RevenueResponse response = new RevenueResponse();
            response.setMonthlyRevenue(monthlyData);
            response.setTotalRevenue(total);
            response.setPeriod("last12months");
            response.setMessage("Thành công");

            return response;
        } catch (Exception e) {
            RevenueResponse response = new RevenueResponse();
            response.setMessage("Lỗi khi lấy dữ liệu: " + e.getMessage());
            return response;
        }
    }
}
