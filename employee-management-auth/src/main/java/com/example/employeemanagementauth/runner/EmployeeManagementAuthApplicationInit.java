package com.example.employeemanagementauth.runner;

import com.example.employeemanagementauth.model.Department;
import com.example.employeemanagementauth.model.User;
import com.example.employeemanagementauth.repository.DepartmentsRepository;
import com.example.employeemanagementauth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.*;

import static java.util.Map.entry;

@Component
public class EmployeeManagementAuthApplicationInit implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final DepartmentsRepository departmentsRepository;

    @Autowired
    public EmployeeManagementAuthApplicationInit(UserRepository userRepository, PasswordEncoder passwordEncoder, DepartmentsRepository departmentsRepository) {
        this.userRepository = userRepository;
        this.departmentsRepository = departmentsRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }


    @Override
    public void run(String... args) throws Exception {
        createUsers();
        createDepartments();
        setUserToDepartments();
       // setSubdepartments();
    }

    @Transactional
    void createUsers() {
        User pesho = User.builder()
                .username("pesho")
                .password(passwordEncoder.encode("pesho"))
                .email("pesho@gmail.com")
                .created(Instant.now())
                .enabled(true)
                .phone("444-442-2233-233")
                .position("engineer")
                .properties(Map.ofEntries(
                        entry("family", "3 children"),
                        entry("wife", "Anna")
                ))
                .build();

        userRepository.save(pesho);

        User gosho = User.builder()
                .username("gosho")
                .password(passwordEncoder.encode("gosho"))
                .email("gosho@gmail.com")
                .created(Instant.now())
                .enabled(true)
                .phone("444-442-2233-2333")
                .position("engineer-manager")
                .properties(Map.ofEntries(
                        entry("wife", "Anna")
                ))
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

    @Transactional
    void createDepartments() {

        Department marketing = Department.builder()
                .departmentName("marketing")
                .enabled(true)
                .subDepartments(new HashSet<>())
                .build();
        departmentsRepository.save(marketing);

        Department sales = Department.builder()
                .departmentName("sales")
                .enabled(true)
                .subDepartments(new HashSet<>())
                .build();

        departmentsRepository.save(sales);

        Department finance = Department.builder()
                .departmentName("finance")
                .enabled(true)
                .subDepartments(new HashSet<>())
                .build();


        departmentsRepository.save(finance);

        Department operations = Department.builder()
                .departmentName("operations")
                .enabled(true)
                .subDepartments(new HashSet<>())
                .build();

        departmentsRepository.save(operations);

        Department hr = Department.builder()
                .departmentName("hr")
                .enabled(true)
                .subDepartments(new HashSet<>())
                .build();

        departmentsRepository.save(hr);

    }

    @Transactional
    void setUserToDepartments() {
        User pesho = userRepository.findByUsername("pesho").orElseThrow(() -> new UsernameNotFoundException("user is not found"));
        User gosho = userRepository.findByUsername("gosho").orElseThrow(() -> new UsernameNotFoundException("user is not found"));
        User sasho = userRepository.findByUsername("sasho").orElseThrow(() -> new UsernameNotFoundException("user is not found"));
        Department mark = departmentsRepository.findByDepartmentName("marketing").orElseThrow();
        Department sales = departmentsRepository.findByDepartmentName("sales").orElseThrow();
        pesho.setDepartment(mark);
        gosho.setDepartment(sales);
        sasho.setDepartment(sales);
        userRepository.save(pesho);
        userRepository.save(gosho);
        userRepository.save(sasho);


    }

//    @Transactional
//    void setSubdepartments() {
//        Department mark = departmentsRepository.findByDepartmentName("marketing").orElseThrow();
//        Department sales = departmentsRepository.findByDepartmentName("sales").orElseThrow();
//        Department finance = departmentsRepository.findByDepartmentName("finance").orElseThrow();
//        Department hr = departmentsRepository.findByDepartmentName("hr").orElseThrow();
//        Department operations = departmentsRepository.findByDepartmentName("operations").orElseThrow();
//        finance.setSubDepartments(new HashSet<>());
//        Department financeChildMark = finance.addSubDepartment(mark.getDepartmentName());
//        departmentsRepository.save(financeChildMark);
//        departmentsRepository.save(finance.addSubDepartment(sales.getDepartmentName()));
//        hr.setSubDepartments(new HashSet<>());
//        departmentsRepository.save(hr.addSubDepartment(finance.getDepartmentName()));
//    }
}
