package com.example.employeemanagementauth.repository;

import com.example.employeemanagementauth.model.Department;
import com.example.employeemanagementauth.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentsRepository extends JpaRepository<Department, Long> {
    Optional<Department> findByDepartmentName(String departmentName);

    @Query("SELECT d FROM Department d WHERE d.parentDepartment.id = ?1")
    public Optional<List<Department>> findEmployeeByParentDepartment(Long parentId);
}
