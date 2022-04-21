package com.hex.car_service_restful_app.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
@Data
@Table(name = "orders")
public class Order extends BaseEntity {

    private LocalDateTime placedAt;

    @NotNull(message = "Выберите дату и время для записи")
    private LocalDateTime executionDate;

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
        this.placedAt = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    }
}
