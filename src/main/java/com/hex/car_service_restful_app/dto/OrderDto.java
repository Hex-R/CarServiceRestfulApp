package com.hex.car_service_restful_app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hex.car_service_restful_app.entities.CarService;
import com.hex.car_service_restful_app.entities.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
public class OrderDto {

    private Long id;

    @NotNull(message = "Выберите дату и время для записи")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime executionDate;

    @NotEmpty(message = "Выберите услуги")
    private List<CarService> services;

    public OrderDto(Order order) {
        this.id = order.getId();
        this.executionDate = order.getExecutionDate();
        this.services = order.getServices();
    }
}
