package com.edutech.usuarios.config;

import com.edutech.usuarios.model.User;
import com.edutech.usuarios.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final UserRepository userRepository;

    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        User user1 = new User("John Doe", "user1@gmail.com", "password1");
        User user2 = new User("Jona Hill", "user2@gmail.com", "password2");

        userRepository.save(user1);
        userRepository.save(user2);
    }
}