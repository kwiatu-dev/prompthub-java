package com.springboot.prompthub.repository;

import com.springboot.prompthub.entity.Prompt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromptRepository extends JpaRepository<Prompt, String> { }
