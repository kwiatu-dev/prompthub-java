package com.springboot.prompthub.repository;

import com.springboot.prompthub.models.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, String> { }
