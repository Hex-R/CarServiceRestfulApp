package com.hex.car_service_restful_app.controllers;

import com.hex.car_service_restful_app.entities.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders/")
@RequiredArgsConstructor
public class OrderController {

    @GetMapping
    public List<CarService> getAllServices() {

    }
}
