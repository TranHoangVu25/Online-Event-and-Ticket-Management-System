package com.ticketsystem.repository;

import com.ticketsystem.dto.RevenueDTO;
import com.ticketsystem.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface RevenueRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT new com.ticketsystem.dto.RevenueDTO(" +
            "YEAR(o.createdAt), MONTH(o.createdAt), SUM(o.totalAmount)) " +
            "FROM Order o " +
            "WHERE o.status = 1 " + // Chỉ lấy đơn hàng đã thành công
            "AND YEAR(o.createdAt) = :year " +
            "GROUP BY YEAR(o.createdAt), MONTH(o.createdAt) " +
            "ORDER BY YEAR(o.createdAt), MONTH(o.createdAt)")
    List<RevenueDTO> findMonthlyRevenueByYear(@Param("year") Integer year);

    @Query("SELECT new com.ticketsystem.dto.RevenueDTO(" +
            "YEAR(o.createdAt), MONTH(o.createdAt), SUM(o.totalAmount)) " +
            "FROM Order o " +
            "WHERE o.status = 1 " +
            "AND o.createdAt BETWEEN :startDate AND :endDate " +
            "GROUP BY YEAR(o.createdAt), MONTH(o.createdAt) " +
            "ORDER BY YEAR(o.createdAt), MONTH(o.createdAt)")
    List<RevenueDTO> findMonthlyRevenueByDateRange(@Param("startDate") LocalDateTime startDate,
                                                   @Param("endDate") LocalDateTime endDate);

    @Query("SELECT new com.ticketsystem.dto.RevenueDTO(" +
            "YEAR(o.createdAt), MONTH(o.createdAt), SUM(o.totalAmount)) " +
            "FROM Order o " +
            "WHERE o.status = 1 " +
            "GROUP BY YEAR(o.createdAt), MONTH(o.createdAt) " +
            "ORDER BY YEAR(o.createdAt) DESC, MONTH(o.createdAt) DESC " +
            "LIMIT 12")
    List<RevenueDTO> findLast12MonthsRevenue();
}
