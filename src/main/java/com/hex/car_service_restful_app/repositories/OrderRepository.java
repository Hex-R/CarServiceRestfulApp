package com.hex.car_service_restful_app.repositories;

import com.hex.car_service_restful_app.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserIdAndIsCompleted(Long userId, boolean isCompleted);
}
