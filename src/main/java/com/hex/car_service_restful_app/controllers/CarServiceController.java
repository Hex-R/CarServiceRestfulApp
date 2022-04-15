package com.hex.car_service_restful_app.controllers;

import com.hex.car_service_restful_app.entities.CarService;
import com.hex.car_service_restful_app.services.CarServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/services/")
@RequiredArgsConstructor
public class CarServiceController {

    private final CarServiceService carServiceService;

    @GetMapping
    public List<CarService> getAll() {
        return carServiceService.getAll();
    }

    @GetMapping("{type}")
    public List<CarService> getByType(@PathVariable String type) {
        return carServiceService.getByType(type);
    }

    @PostMapping
    public void create(@RequestBody @Valid )

}
