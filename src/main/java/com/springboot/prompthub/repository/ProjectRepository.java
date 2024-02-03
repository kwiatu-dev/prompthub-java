package com.springboot.prompthub.repository;

import com.springboot.prompthub.models.entity.Project;
import com.springboot.prompthub.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("ProjectRepository")
public interface ProjectRepository extends JpaRepository<Project, String>, SoftDelete {
    List<Project> findAllByCreatedBy(User user);
    Optional<Project> findByCreatedByAndId(User user, String Id);
}
