package com.hex.car_service_restful_app.repositories;

import com.hex.car_service_restful_app.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

    List<Order> findByUserIdAndIsCompleted(Long userId, boolean isCompleted);

    Optional<Order> findByIdAndUserId(Long orderId, Long userId);

    void deleteByIdAndUserId(Long orderId, Long userId);
}
