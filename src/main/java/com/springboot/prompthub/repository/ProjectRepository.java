package com.springboot.prompthub.repository;

import com.springboot.prompthub.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, String> { }
