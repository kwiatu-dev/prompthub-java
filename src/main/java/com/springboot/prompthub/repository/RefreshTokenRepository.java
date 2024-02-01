package com.springboot.prompthub.repository;

import com.github.javafaker.Bool;
import com.springboot.prompthub.models.entity.RefreshToken;
import com.springboot.prompthub.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Ref;
import java.util.List;
import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByToken(String token);
    Boolean existsByToken(String token);
    List<RefreshToken> findAllByUser(User user);
}
