package com.mycompany.myapp.domain;

import com.mycompany.myapp.domain.base.BaseEntity;
import com.mycompany.myapp.domain.enums.UserStatus;
import com.mycompany.myapp.web.dto.NamecardRequestDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder @Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class NameCard extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(columnDefinition = "Boolean default false")
    private Boolean isUser;

    private String name;

    private String company;

    private String department;

    private String address;

    private String position;

    private String mobile;

    private String tel;

    private String fax;

    private String email;

    private String homepage;

    private String url;

    public void updateNamecard(NamecardRequestDto.NamecardDto request, Category category){
        this.category = category;
        this.name = request.getName();
        this.company = request.getCompany();
        this.department = request.getDepartment();
        this.address = request.getAddress();
        this.position = request.getPosition();
        this.mobile = request.getMobile();
        this.tel = request.getTel();
        this.fax = request.getFax();
        this.email = request.getEmail();
        this.homepage = request.getHomepage();
    }
}
