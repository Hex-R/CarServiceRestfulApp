package com.hex.car_service_restful_app.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@Table(name = "car_service")
public class CarService extends BaseEntity {

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Укажите тип сервиса")
    private ServiceType type;

    @NotBlank(message = "Укажите категорию сервиса")
    private String category;

    @NotBlank(message = "Укажите название сервиса")
    private String name;

    private String description;

    @NotNull(message = "Укажите цену сервиса")
    private int price;

    public String toString() {return name;}
}
