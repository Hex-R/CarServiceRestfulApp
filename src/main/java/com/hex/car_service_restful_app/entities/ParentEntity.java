package com.hex.car_service_restful_app.entities;

import javax.persistence.*;

@MappedSuperclass
@Access(AccessType.FIELD)
abstract class ParentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, updatable = false, insertable = false)
    private Long id;
}
