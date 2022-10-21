package com.example.employeemanagementauth.runner;

import com.example.employeemanagementauth.model.User;
import com.example.employeemanagementauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

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
        createUser();
    }

   void createUser(){
        User pesho = new User();
        pesho.setUsername("pesho");
        pesho.setPassword(passwordEncoder.encode("pesho"));
        pesho.setEmail("pesho@gmail.com");
        pesho.setEnabled(true);
        userRepository.save(pesho);
    }
}
