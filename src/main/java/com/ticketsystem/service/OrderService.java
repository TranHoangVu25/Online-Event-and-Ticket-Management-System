package com.ticketsystem.service;

import com.ticketsystem.dto.request.OrderCreationRequest;
import com.ticketsystem.dto.response.OrderInformationResponse;
import com.ticketsystem.entity.*;
import com.ticketsystem.repository.CouponRepository;
import com.ticketsystem.repository.OrderRepository;
import com.ticketsystem.repository.TicketClassRepository;
import com.ticketsystem.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    TicketClassRepository ticketClassRepository;
    OrderRepository orderRepository;
    UserRepository userRepository;
    CouponRepository couponRepository;

    //tạo order,orderDetail
    public Order createOrder(OrderCreationRequest requests, int userId) {
        try {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Order order = new Order();
        order.setUser(user);
        order.setTotalAmount(BigDecimal.ZERO);
            order.setStatus(0); // 0 = pending, 1 = paid, 2 = cancelled
        Order savedOrder = orderRepository.save(order);

        Set<OrderDetail> details = new HashSet<>();
        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<Integer, Integer> entry : requests.getTickets().entrySet()) {
            Integer ticketClassId = entry.getKey();
            Integer quantity = entry.getValue();

            TicketClass ticketClass = ticketClassRepository.findById(ticketClassId)
                    .orElseThrow(() -> new RuntimeException("TicketClass not found"));

            int remaining = ticketClass.getTotalQuantity() - ticketClass.getSoldQuantity();

            if ("COD".equalsIgnoreCase(requests.getMethod())) {
                if (remaining < quantity) {
                    throw new RuntimeException("Not enough tickets available");
                }
                // 🔑 Cập nhật soldQuantity thay vì remainingQuantity
                ticketClass.setSoldQuantity(ticketClass.getSoldQuantity() + quantity);
                ticketClassRepository.save(ticketClass);
                log.info("COD order → sold {} tickets for TicketClassId={}, now soldQuantity={}",
                        quantity, ticketClassId, ticketClass.getSoldQuantity());
            }

            OrderDetailId id = new OrderDetailId(savedOrder.getId(), ticketClass.getId());

            OrderDetail detail = new OrderDetail();
            detail.setId(id);
            detail.setOrder(savedOrder);
            detail.setTicketClass(ticketClass);
            detail.setQuantity(quantity);
            detail.setPrice(ticketClass.getPrice());

            details.add(detail);

            total = total.add(ticketClass.getPrice()
                    .multiply(BigDecimal.valueOf(quantity)));
        }

            final int SCALE = 2; //Làm tròn đến 2 chữ số thập phân (cho tiền tệ)
            final RoundingMode ROUNDING_MODE = RoundingMode.HALF_UP; // Quy tắc làm tròn truyền thống

            //type 1: tiền mặt, 2: phần trăm
            String coupon_code = requests.getCouponCode();

            //kiểm tra có mã coupon hay không
            if (coupon_code != null && !coupon_code.trim().isEmpty()) {
                Coupon coupon = couponRepository.findByCode(coupon_code)
                        .orElseThrow(() -> new RuntimeException("Mã giảm giá không tồn tại hoặc không hợp lệ. "));

                BigDecimal sub_condition = new BigDecimal(String.valueOf(coupon.getCondition()));


                //so sánh total với điều kiện áp mã
                if (total.compareTo(sub_condition) >= 0) {
                    //với loại mã là tiền mặt
                    if (coupon.getType() == 1) {
                        BigDecimal discount = new BigDecimal(coupon.getDiscount());
                        total = total.subtract(discount);
                    }
                    //với loại mã là phần trăm
                    else if (coupon.getType() == 2) {
                        BigDecimal one_hundred = new BigDecimal(100);
                        BigDecimal discount = new BigDecimal(coupon.getDiscount());
                        BigDecimal percent = discount.divide(one_hundred, SCALE + 2, ROUNDING_MODE);
                        total = total.subtract(total.multiply(percent));
                    }
                }
                savedOrder.setCoupon(coupon);
            }

            //lưu
        savedOrder.setOrderDetails(details);
            if (total.compareTo(BigDecimal.ZERO) < 0) {
                savedOrder.setTotalAmount(BigDecimal.ZERO);
            } else {
                savedOrder.setTotalAmount(total);
            }
            return orderRepository.save(savedOrder);
        } catch (Exception e) {
            log.error("Lỗi: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public Order getOrderById(int id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("id not found"));
    }

    public List<OrderInformationResponse> getAllOrderByUserId(Integer userId) {
        return orderRepository.findAllOrderByUserId(userId);
    }

    public List<OrderInformationResponse> getAllOrder() {return orderRepository.findAllOrder();
    }

    public Order confirmOrder(int orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new RuntimeException("Order's id not found"));
        order.setStatus(1);
        return  orderRepository.save(order);
    }

    public Order cancelOrder(int orderId){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(()-> new RuntimeException("Order's id not found"));
        order.setStatus(2);

        return  orderRepository.save(order);
    }
}
