package com.hex.car_service_restful_app.repositories;

import com.hex.car_service_restful_app.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

    User findByActivationCode(String code);
}
