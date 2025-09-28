package com.mozi.domain.user.entity;

import com.mozi.global.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

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

    private String refreshToken;

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void logout() {
        this.refreshToken = null;
    }

    public void withdraw() {
        super.unActivated();
        this.refreshToken = null;
    }

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
}
