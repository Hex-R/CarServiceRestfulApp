package com.hex.car_service_restful_app.repositories;

import com.hex.car_service_restful_app.entities.CarService;
import com.hex.car_service_restful_app.entities.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarServiceRepository extends JpaRepository<CarService, Long> {

    List<CarService> findByType(ServiceType serviceType);
}
