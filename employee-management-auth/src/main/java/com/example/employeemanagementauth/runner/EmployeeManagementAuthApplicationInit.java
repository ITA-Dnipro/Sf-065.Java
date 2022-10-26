package com.example.employeemanagementauth.runner;

import com.example.employeemanagementauth.model.User;
import com.example.employeemanagementauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class EmployeeManagementAuthApplicationInit implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeManagementAuthApplicationInit(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    @Override
    public void run(String... args) throws Exception {
        createUsers();

    }

    void createUsers() {
        User pesho = User.builder()
                .username("pesho")
                .password(passwordEncoder.encode("pesho"))
                .email("pesho@gmail.com")
                .created(Instant.now())
                .enabled(true)
                .build();

        userRepository.save(pesho);

        User gosho = User.builder()
                .username("gosho")
                .password(passwordEncoder.encode("gosho"))
                .email("gosho@gmail.com")
                .created(Instant.now())
                .enabled(true)
                .build();

        userRepository.save(gosho);

        User tosho = User.builder()
                .username("tosho")
                .password(passwordEncoder.encode("tosho"))
                .email("tosho@gmail.com")
                .created(Instant.now())
                .enabled(true)
                .build();

        userRepository.save(tosho);

        User sasho = User.builder()
                .username("sasho")
                .password(passwordEncoder.encode("sasho"))
                .email("sasho@gmail.com")
                .created(Instant.now())
                .enabled(true)
                .build();

        userRepository.save(sasho);

        User stamat = User.builder()
                .username("stamat")
                .password(passwordEncoder.encode("stamat"))
                .email("stamat@gmail.com")
                .created(Instant.now())
                .enabled(true)
                .build();

        userRepository.save(stamat);
    }
}
