package com.springboot.prompthub.repository;

import com.springboot.prompthub.models.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(String name);
}
