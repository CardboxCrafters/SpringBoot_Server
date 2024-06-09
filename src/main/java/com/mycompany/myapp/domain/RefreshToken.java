package com.mycompany.myapp.domain;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Entity
@Builder @Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token", length = 1000)
    private String token;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;

    @Column()
    private Date expirationTime;

    public void updateExpirationTime(Date expTime){
        this.expirationTime = expTime;
    }
    public void updateToken(String token){
        this.token = token;
    }

}