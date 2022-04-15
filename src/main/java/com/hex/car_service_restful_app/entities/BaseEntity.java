package com.hex.car_service_restful_app.entities;

import lombok.Data;

import javax.persistence.*;

@MappedSuperclass
@Access(AccessType.FIELD)
@Data
abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false, insertable = false)
    private Long id;
}
