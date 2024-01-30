package com.springboot.prompthub.repository;

import com.springboot.prompthub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> { }
