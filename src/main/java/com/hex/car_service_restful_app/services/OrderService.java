package com.hex.car_service_restful_app.services;

import com.hex.car_service_restful_app.entities.Order;
import com.hex.car_service_restful_app.entities.User;
import com.hex.car_service_restful_app.exceptions.NotFoundException;
import com.hex.car_service_restful_app.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void save(Order order, User user) {

        order.setUser(user);
        order.setCompleted(false);

        orderRepository.save(order);
    }

    public List<Order> getAll(User user) {

        return orderRepository.findAll();
    }

    public List<Order> getActive(User user) {

        return orderRepository.findByUserIdAndIsCompleted(user.getId(), false);
    }

    public List<Order> getCompleted(User user) {

        return orderRepository.findByUserIdAndIsCompleted(user.getId(), true);
    }

    public void edit(String id, Order updatedOrder) {

        Order orderFromDb = orderRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new NotFoundException(String.format("Order with id: %s not found", id)));

        BeanUtils.copyProperties(updatedOrder, orderFromDb);
        orderRepository.save(orderFromDb);
    }

    public void delete(String id) {

        orderRepository.deleteById(Long.valueOf(id));
    }
}
