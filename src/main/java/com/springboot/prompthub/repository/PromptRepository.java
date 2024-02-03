package com.springboot.prompthub.repository;

import com.springboot.prompthub.models.entity.Project;
import com.springboot.prompthub.models.entity.Prompt;
import com.springboot.prompthub.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("PromptRepository")
public interface PromptRepository extends JpaRepository<Prompt, String>, SoftDelete {
    List<Prompt> findAllByCreatedByAndProject(User user, Project project);
    Optional<Prompt> findByCreatedByAndProjectAndId(User user, Project project, String promptId);
}
