package com.hex.car_service_restful_app.services;

import com.hex.car_service_restful_app.dto.OrderDto;
import com.hex.car_service_restful_app.entities.Order;
import com.hex.car_service_restful_app.entities.User;
import com.hex.car_service_restful_app.exceptions.NotFoundException;
import com.hex.car_service_restful_app.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public void save(OrderDto orderDto, User user) {

        Order order = new Order();

        order.setExecutionDate(orderDto.getExecutionDate());
        order.setServices(orderDto.getServices());
        order.setUser(user);
        order.setCompleted(false);

        orderRepository.save(order);
    }

    public List<OrderDto> getAll(User user) {

        List<Order> orders = orderRepository.findByUserId(user.getId());
        List<OrderDto> orderDtos = new ArrayList<>();

        for (Order order : orders) {
            orderDtos.add(new OrderDto(order));
        }

        return orderDtos;
    }

    public List<OrderDto> getActive(User user) {

        List<Order> orders = orderRepository.findByUserIdAndIsCompleted(user.getId(), false);
        List<OrderDto> orderDtos = new ArrayList<>();

        for (Order order : orders) {
            orderDtos.add(new OrderDto(order));
        }

        return orderDtos;
    }

    public List<OrderDto> getCompleted(User user) {

        List<Order> orders = orderRepository.findByUserIdAndIsCompleted(user.getId(), true);
        List<OrderDto> orderDtos = new ArrayList<>();

        for (Order order : orders) {
            orderDtos.add(new OrderDto(order));
        }

        return orderDtos;
    }

    public void edit(String orderId, OrderDto updatedOrder, User user) {

        Order orderToUpdate = orderRepository.findByIdAndUserId(Long.valueOf(orderId), user.getId())
                .orElseThrow(() -> new NotFoundException(
                        String.format("Order with id %s not found, or not belong to this user", orderId)));

        orderToUpdate.setExecutionDate(updatedOrder.getExecutionDate());
        orderToUpdate.setServices(updatedOrder.getServices());

        orderRepository.save(orderToUpdate);
    }

    public void delete(String orderId, User user) {
        orderRepository.deleteByIdAndUserId(Long.valueOf(orderId), user.getId());
    }
}
