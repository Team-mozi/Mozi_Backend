package com.mozi.domain.user.repository;

import com.mozi.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmailAndActivatedTrue(String email);

    Optional<User> findByEmailAndActivatedTrue(String email);

    boolean existsByNicknameAndActivatedTrue(String nickname);
}
