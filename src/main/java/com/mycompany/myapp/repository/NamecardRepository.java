package com.mycompany.myapp.repository;

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

    NameCard findByUserAndIsUserTrue(User user);

    @Modifying
    @Transactional
    @Query("UPDATE NameCard n SET n.name = :name, n.company = :company, n.department = :department, n.position = :position, n.mobile = :mobile, n.email = :email, n.tel = :tel, n.fax = :fax, n.homepage = :homepage, n.address = :address")
    void updateNamecard(@Param("name") String name, @Param("company") String company, @Param("department") String department, @Param("position") String position, @Param("mobile") String mobile, @Param("email") String email, @Param("tel") String tel, @Param("fax") String fax, @Param("homepage") String homepage, @Param("address") String address);

}
