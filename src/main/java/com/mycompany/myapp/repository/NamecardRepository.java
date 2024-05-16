package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Category;
import com.mycompany.myapp.domain.NameCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NamecardRepository extends JpaRepository<NameCard, Long> {
    List<NameCard> findByCategory(Category category);
}
