package com.hex.car_service_restful_app.services;

import com.hex.car_service_restful_app.entities.Role;
import com.hex.car_service_restful_app.entities.User;
import com.hex.car_service_restful_app.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    /*@Value("${hostname}")
    private String hostname;*/

    private final UserRepository userRepository;

    //private final MailSenderService mailSenderService;

    private final PasswordEncoder passwordEncoder;

    public boolean addUser(User user) {
        User userFromDb = userRepository.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        //user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);

        //sendMessage(user);

        return true;
    }

    /*public boolean updateUser(User user, UserDetailsForm userDetailsForm) {

        String newPassword = userDetailsForm.getPassword();

        if (StringUtils.hasLength(newPassword)) {

            if (StringUtils.hasText(newPassword) && newPassword.length() >= 6 && newPassword.length() <= 30) {
                user.setPassword(passwordEncoder.encode(userDetailsForm.getPassword()));
            } else return false;
        }

        user.setEmail(userDetailsForm.getEmail());
        user.setPhoneNumber(userDetailsForm.getPhoneNumber());

        userRepository.save(user);
        return true;
    }*/

    /*private void sendMessage(User user) {
        if (!user.getEmail().isBlank()) {
            String message = String.format(
                    "Здравствуйте, %s! \n" +
                            "Для подтверждения вашего email перейдите по ссылке http://%s/register/activation/%s",
                    user.getUsername(),
                    hostname,
                    user.getActivationCode()
            );

            mailSenderService.send(user.getEmail(), "Код активации", message);
        }
    }*/

    /*public boolean activateUser(String code) {

        User user = userRepository.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActive(true);
        user.setActivationCode(null);
        userRepository.save(user);

        return true;
    }*/

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }
}
