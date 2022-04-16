package com.hex.car_service_restful_app.controllers;

import com.hex.car_service_restful_app.entities.CarService;
import com.hex.car_service_restful_app.services.CarServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public ResponseEntity<?> add(@RequestBody @Valid CarService carService) {

        carServiceService.add(carService);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //@PathVariable("id") Message messageFromDB Получать сразу объект из базы по id
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("{id}")
    public void edit(@PathVariable String id,
                     @RequestBody @Valid CarService carService) {

        carServiceService.edit(id, carService);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("{id}")
    public void delete(@PathVariable String id) {
        carServiceService.delete(id);
    }
}
