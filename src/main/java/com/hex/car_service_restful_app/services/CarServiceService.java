package com.hex.car_service_restful_app.services;

import com.hex.car_service_restful_app.entities.CarService;
import com.hex.car_service_restful_app.entities.ServiceType;
import com.hex.car_service_restful_app.exceptions.NotFoundException;
import com.hex.car_service_restful_app.repositories.CarServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
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

        for (ServiceType serviceType : ServiceType.values()){
            if (serviceType.name().equals(type)){
                return carServiceRepository.findByType(ServiceType.valueOf(type));
            }
        }

        throw new NotFoundException(String.format("Car service type %s not found", type));
    }

    public void add(CarService carService) {
        carServiceRepository.save(carService);
    }

    public void edit(String id, CarService updatedService) {

        CarService serviceFromDb = carServiceRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new NotFoundException(String.format("Car service with id: %s not found", id)));

        BeanUtils.copyProperties(updatedService, serviceFromDb, "id");
        carServiceRepository.save(serviceFromDb);
    }

    public void delete(String id) {
        if (!carServiceRepository.existsById(Long.valueOf(id))){
            throw new NotFoundException(String.format("Car service with id %s not found", id));
        }
        carServiceRepository.deleteById(Long.valueOf(id));
    }
}
