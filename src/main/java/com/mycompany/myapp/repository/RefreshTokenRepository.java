package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Address;
import com.mycompany.myapp.domain.RefreshToken;
import com.mycompany.myapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByToken(String token);
    RefreshToken findByUser(User user);
}
