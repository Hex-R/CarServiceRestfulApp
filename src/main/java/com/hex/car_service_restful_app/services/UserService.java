package com.hex.car_service_restful_app.services;

import com.hex.car_service_restful_app.dto.AuthenticationRequestDto;
import com.hex.car_service_restful_app.dto.UserRegistrationDto;
import com.hex.car_service_restful_app.dto.UserUpdateDto;
import com.hex.car_service_restful_app.entities.Role;
import com.hex.car_service_restful_app.entities.User;
import com.hex.car_service_restful_app.exceptions.*;
import com.hex.car_service_restful_app.jwt.JwtTokenProvider;
import com.hex.car_service_restful_app.repositories.UserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       @Lazy AuthenticationManager authenticationManager,
                       @Lazy JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    public void createUser(UserRegistrationDto userDto) {

        if (userRepository.existsByUsername(userDto.getUsername())) {
            throw new UserExistsException();
        }

        if (userRepository.existsByEmail(userDto.getEmail())) {
            throw new EmailAlreadyInUseException();
        }

        if (userRepository.existsByPhoneNumber(userDto.getPhoneNumber())) {
            throw new PhoneNumberAlreadyInUseException();
        }

        if (!userDto.getPassword().equals(userDto.getPasswordConfirmation())){
            throw new PasswordConfirmationException();
        }

        User user = new User();

        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        user.setPhoneNumber(userDto.getPhoneNumber());

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));

        userRepository.save(user);
    }

    public String login(AuthenticationRequestDto requestDto) {

        String username = requestDto.getUsername();
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
        User user = (User) loadUserByUsername(username);

        return jwtTokenProvider.createToken(username, user.getRoles());
    }

    public void updateCurrentUser(UserUpdateDto updatedUser, User currentUser) {

        String newPassword = updatedUser.getPassword();

        if (StringUtils.hasLength(newPassword)) {

            if (StringUtils.hasText(newPassword) && newPassword.length() >= 6 && newPassword.length() <= 30) {

                if (updatedUser.getPassword().equals(updatedUser.getPasswordConfirmation())){
                    currentUser.setPassword(passwordEncoder.encode(newPassword));
                }else throw new PasswordConfirmationException();

            }else  throw new PasswordIncorrectException();
        }

        currentUser.setEmail(updatedUser.getEmail());
        currentUser.setPhoneNumber(updatedUser.getPhoneNumber());

        userRepository.save(currentUser);
    }

    public void deleteUser(String id) {
        if (!userRepository.existsById(Long.valueOf(id))){
            throw new NotFoundException(String.format("User with id %s not found", id));
        }
        userRepository.deleteById(Long.valueOf(id));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User with username: %s not found", username)));
    }
}
