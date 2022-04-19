package com.hex.car_service_restful_app.controllers;

import com.hex.car_service_restful_app.entities.CarService;
import com.hex.car_service_restful_app.services.CarServiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "Car service controller",
        description = "Get all services or services with selected type." +
                " Create, update and delete operations require 'ADMIN' authority")
@RestController
@RequestMapping("/api/v1/services/")
@RequiredArgsConstructor
public class CarServiceController {

    private final CarServiceService carServiceService;

    @Operation(summary = "Returns all car services from database")
    @GetMapping
    public List<CarService> getAll() {
        return carServiceService.getAll();
    }

    @Operation(summary = "Returns car services of selected type." +
            " Type is enum, so write it with upper case chars")
    @GetMapping("{type}")
    public List<CarService> getByType(@PathVariable String type) {
        return carServiceService.getByType(type);
    }

    @Operation(summary = "Add new car service to database. Requires 'ADMIN' authority")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public void add(@RequestBody @Valid CarService carService) {
        carServiceService.add(carService);
    }

    //@PathVariable("id") Message messageFromDB Получать сразу объект из базы по id
    @Operation(summary = "Update car service. Requires 'ADMIN' authority")
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("{id}")
    public void edit(@PathVariable String id,
                     @RequestBody @Valid CarService carService) {

        carServiceService.edit(id, carService);
    }

    @Operation(summary = "Delete car service. Requires 'ADMIN' authority")
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        carServiceService.delete(id);
    }
}
