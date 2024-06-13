package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Address;
import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.domain.NameCard;
import com.mycompany.myapp.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface NamecardRepository extends JpaRepository<NameCard, Long> {
    List<NameCard> findByCategory(Category category);

    List<NameCard> findByUserAndIsUserFalse(User user);

    NameCard findByUserAndIsUserTrue(User user);

    List<NameCard> findByNameContaining(String keyword);
}
