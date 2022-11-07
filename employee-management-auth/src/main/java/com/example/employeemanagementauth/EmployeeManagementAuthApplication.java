package com.example.employeemanagementauth;




import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;




@SpringBootApplication
@EnableAsync
public class EmployeeManagementAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagementAuthApplication.class, args);
	}

}
