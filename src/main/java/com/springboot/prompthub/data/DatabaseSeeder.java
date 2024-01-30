package com.springboot.prompthub.data;

import com.springboot.prompthub.constant.Constant;
import com.springboot.prompthub.entity.BaseEntity;
import com.springboot.prompthub.entity.Project;
import com.springboot.prompthub.entity.Prompt;
import com.springboot.prompthub.entity.User;
import com.springboot.prompthub.repository.ProjectRepository;
import com.springboot.prompthub.repository.PromptRepository;
import com.springboot.prompthub.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    private final DataFactory dataFactory;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final PromptRepository promptRepository;

    public DatabaseSeeder(
            DataFactory dataFactory,
            UserRepository userRepository,
            ProjectRepository projectRepository,
            PromptRepository promptRepository) {
        this.dataFactory = dataFactory;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.promptRepository = promptRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        int usersCount = userRepository.findAll().size();

        if(usersCount != 0){
            return;
        }

        Random random = new Random();

        User admin = new User();
        admin.setEmail(Constant.DEFAULT_ADMIN_EMAIL);
        admin.setPassword(Constant.DEFAULT_ADMIN_PASSWORD);

        List<User> users = dataFactory.generateUsers(10);
        users.forEach(user -> setAuditableBy(user, admin));
        users.add(admin);

        List<Project> projects = dataFactory.generateProjects(10);

        projects.forEach(project -> {
            User user = users.get(random.nextInt(users.size()));
            setAuditableBy(project, user);
        });

        List<Prompt> prompts = dataFactory.generatePrompts(30);

        prompts.forEach(prompt -> {
            Project project = projects.get(random.nextInt(projects.size()));
            prompt.setProject(project);
            setAuditableBy(prompt, project.getCreatedBy());
        });

        userRepository.saveAll(users);
        projectRepository.saveAll(projects);
        promptRepository.saveAll(prompts);
    }

    private void setAuditableBy(Object object, User auditableBy){
        if (object instanceof BaseEntity entity) {
            entity.setCreatedBy(auditableBy);
            entity.setModifiedBy(auditableBy);
        }
    }
}
