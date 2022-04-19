package com.hex.car_service_restful_app.dto;

import com.hex.car_service_restful_app.entities.CarService;
import com.hex.car_service_restful_app.entities.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDto {

    @NotNull(message = "Выберите дату и время для записи")
    private Date executionDate;

    @NotEmpty(message = "Выберите услуги")
    private List<CarService> services;

    public OrderDto(Order order) {
        this.executionDate = order.getExecutionDate();
        this.services = order.getServices();
    }
}
