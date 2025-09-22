package com.mozi.domain.user.entity;

import com.mozi.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
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

    protected User() {}

    @Builder
    private  User(String oauthId, String oauthProvider, String email, String password, String nickname, boolean agreed) {
        this.oauthId = oauthId;
        this.oauthProvider = oauthProvider;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.agreed = agreed;
    }

    public static User create(String email, String password, PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .agreed(true)
                .build();
    }
}
