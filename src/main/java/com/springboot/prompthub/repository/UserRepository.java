package com.springboot.prompthub.repository;

import com.springboot.prompthub.models.entity.User;
import com.springboot.prompthub.utils.UserPrincipal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository("UserRepository")
public interface UserRepository extends JpaRepository<User, String>, SoftDelete {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
}
