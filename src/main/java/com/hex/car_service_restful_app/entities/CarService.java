package com.hex.car_service_restful_app.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "car_service")
public class CarService extends BaseEntity {

    @Enumerated(EnumType.STRING)
    private ServiceType type;

    private String name;

    private String description;

    private int price;

    public String toString() {return name;}
}
