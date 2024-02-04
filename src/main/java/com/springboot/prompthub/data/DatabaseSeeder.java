package com.springboot.prompthub.data;

import com.springboot.prompthub.repository.RoleRepository;
import com.springboot.prompthub.utils.AppConstant;
import com.springboot.prompthub.models.entity.*;
import com.springboot.prompthub.repository.ProjectRepository;
import com.springboot.prompthub.repository.PromptRepository;
import com.springboot.prompthub.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Array;
import java.util.*;

@Component
public class DatabaseSeeder implements CommandLineRunner {
    private final DataFactory dataFactory;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final PromptRepository promptRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseSeeder(
            DataFactory dataFactory,
            UserRepository userRepository,
            ProjectRepository projectRepository,
            PromptRepository promptRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.dataFactory = dataFactory;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.promptRepository = promptRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        int usersCount = userRepository.findAll().size();
        if(usersCount != 0) return;

        Random random = new Random();

        Role roleAdmin = new Role();
        roleAdmin.setId(UUID.randomUUID().toString());
        roleAdmin.setName(AppConstant.ROLE_ADMIN);
        Set<Role> rolesForAdmin = new HashSet<>();
        rolesForAdmin.add(roleAdmin);

        Role roleUser = new Role();
        roleUser.setId(UUID.randomUUID().toString());
        roleUser.setName(AppConstant.ROLE_USER);
        Set<Role> rolesForUser = new HashSet<>();
        rolesForUser.add(roleUser);

        List<Role> roles = new ArrayList<>();
        roles.add(roleUser);
        roles.add(roleAdmin);

        User admin = new User();
        admin.setEmail(AppConstant.DEFAULT_ADMIN_EMAIL);
        admin.setPassword(passwordEncoder.encode(AppConstant.DEFAULT_ADMIN_PASSWORD));
        admin.setRoles(rolesForAdmin);

        List<User> users = dataFactory.generateUsers(10);

        users.forEach(user -> {
            user.setRoles(rolesForUser);
            setAuditableBy(user, admin);
        });

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

        roleRepository.saveAll(roles);
        userRepository.save(admin);
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
