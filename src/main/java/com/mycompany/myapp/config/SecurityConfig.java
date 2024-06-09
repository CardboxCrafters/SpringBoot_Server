package com.mycompany.myapp.config;

import com.mycompany.myapp.security.CustomAuthenticationProvider;
import com.mycompany.myapp.security.JWTRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final JWTRequestFilter jwtRequestFilter;
    private final CustomAuthenticationProvider customAuthenticationProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/**").permitAll()
                //.antMatchers("/name","/sms-certification/send","/sms-certification/confirm", "/v2/api-docs", "/swagger-resources/**", "/webjars/**","/swagger/**","/swagger-ui/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().disable()
                .headers().disable()
                .httpBasic().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/name") // 첫화면
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class).authenticationProvider(customAuthenticationProvider);

        return http.build();
    }
    @Configuration
    public class PasswordEncoderConfig {
        @Bean
        public PasswordEncoder passwordEncoder() {
            return PasswordEncoderFactories.createDelegatingPasswordEncoder();
        }
    }
}