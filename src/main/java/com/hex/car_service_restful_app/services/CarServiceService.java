package com.hex.car_service_restful_app.services;

import com.hex.car_service_restful_app.entities.CarService;
import com.hex.car_service_restful_app.entities.ServiceType;
import com.hex.car_service_restful_app.repositories.CarServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceService {

    private final CarServiceRepository carServiceRepository;


    public List<CarService> getAll() {
        return carServiceRepository.findAll();
    }

    public List<CarService> getByType(String type) {
        return carServiceRepository.findByType(ServiceType.valueOf(type));
    }
}
