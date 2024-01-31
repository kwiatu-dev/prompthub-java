package com.springboot.prompthub.data;

import com.github.javafaker.Faker;
import com.springboot.prompthub.utils.AppConstant;
import com.springboot.prompthub.models.entity.Message;
import com.springboot.prompthub.models.entity.Project;
import com.springboot.prompthub.models.entity.Prompt;
import com.springboot.prompthub.models.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class DataFactory {
    private final Faker faker;
    private final PasswordEncoder passwordEncoder;

    public DataFactory(
            Faker faker,
            PasswordEncoder passwordEncoder){
        this.faker = faker;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> generateUsers(int n){
        return IntStream.rangeClosed(1, n)
                .mapToObj(i -> {
                    User user = new User();
                    user.setId(UUID.randomUUID().toString());
                    user.setEmail(faker.internet().emailAddress());
                    user.setPassword(passwordEncoder.encode(AppConstant.DEFAULT_USER_PASSWORD));

                    return user;
                })
                .collect(Collectors.toList());
    }

    public List<Project> generateProjects(int n){
        return IntStream.rangeClosed(1, n)
                .mapToObj(i -> {
                    Project project = new Project();
                    project.setId(UUID.randomUUID().toString());
                    project.setName(faker.lorem().characters(0, 128, true));
                    project.setDescription(faker.lorem().characters(0, 256, true));

                    return project;
                })
                .collect(Collectors.toList());
    }

    public List<Prompt> generatePrompts(int n){
        return IntStream.rangeClosed(1, n)
                .mapToObj(i -> {
                    Prompt prompt = new Prompt();
                    prompt.setId(UUID.randomUUID().toString());
                    prompt.setName(faker.lorem().characters(0, 128, true));
                    prompt.setDescription(faker.lorem().characters(0, 256, true));
                    prompt.setModel(AppConstant.LM_MODELS[faker.random().nextInt(0, AppConstant.LM_MODELS.length - 1)]);
                    prompt.setTokens(faker.random().nextInt(13, 16000));
                    prompt.setMessages(generateMessages(faker.random().nextInt(0, 10)));

                    return prompt;
                })
                .collect(Collectors.toList());
    }

    public List<Message> generateMessages(int n){
        return IntStream.rangeClosed(1, n)
                .mapToObj(i -> {
                    Message message = new Message();
                    message.setRole(AppConstant.LM_ROLES[faker.random().nextInt(0, AppConstant.LM_ROLES.length - 1)]);
                    message.setContent(faker.lorem().characters(13, 256, false));

                    return message;
                })
                .collect(Collectors.toList());
    }
}
