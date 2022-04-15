package com.hex.car_service_restful_app.entities;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "usr")
public class User extends BaseEntity implements UserDetails {

    @JsonView(Views.RegistrationForm.class)
    @NotBlank(message = "Логин не может быть пустым")
    @Size(min = 4, max = 30, message = "Необходимо 4 - 30 символов")
    private String username;

    @JsonView(Views.RegistrationForm.class)
    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 6, max = 70, message = "Необходимо 6 - 30 символов")
    private String password;

    @JsonView(Views.RegistrationForm.class)
    @Transient
    private String passwordConfirmation;

    @JsonView(Views.RegistrationForm.class)
    @Email(message = "Введите корректный адрес почты")
    @NotBlank(message = "Email не может быть пустым")
    @Size(max = 50, message = "Максимум 50 символов")
    private String email;

    @JsonView(Views.RegistrationForm.class)
    @NotBlank(message = "Номер телефона не может быть пустым")
    @Size(min = 7, max = 12, message = "Необходимо 7 - 12 знаков без пробелов и скобок")
    private String phoneNumber;

    @OneToMany
    private List<Order> serviceOrders;

    private String activationCode;

    private Boolean active;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return getActive();
    }
}
