package com.mozi.domain.user.entity;

import com.mozi.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String oauthId;

    private String oauthProvider;

    @Column(unique = true)
    private String email;

    private String password;

    @Column(unique = true)
    private String nickname;

    @Column(name = "is_agreed")
    private boolean agreed;

    public  User(String oauthId, String oauthProvider, String email, String password, String nickname) {
        this.oauthId = oauthId;
        this.oauthProvider = oauthProvider;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.agreed = true;
    }
}
