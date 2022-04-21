package com.hex.car_service_restful_app.entities;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order extends BaseEntity {

    private Date placedAt;

    @NotNull(message = "Выберите дату и время для записи")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date executionDate;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @NotEmpty(message = "Выберите услуги")
    @ManyToMany
    @JoinTable(
            name = "orders_services",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "services_id", referencedColumnName = "id")
    )
    private List<CarService> services;

    private boolean isCompleted;

    @PrePersist
    void placedAt() {
        this.placedAt = new Date();
    }
}
