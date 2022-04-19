package com.hex.car_service_restful_app.config;

import com.hex.car_service_restful_app.jwt.AuthEntryPointJwt;
import com.hex.car_service_restful_app.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;

    private final AuthEntryPointJwt unauthorizedHandler;

    @Value("${prop.swagger.enabled}")
    private boolean swaggerEnabled;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {

        if (swaggerEnabled){
            httpSecurity
                    .authorizeRequests()
                    .antMatchers("/api/api-docs/**", "/api/swagger-ui.html", "/api/swagger-ui/**")
                    .permitAll();
        }

        httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/users/registration", "/api/v1/users/login", "/api/v1/services/*").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
