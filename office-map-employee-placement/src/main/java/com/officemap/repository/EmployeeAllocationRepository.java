package com.officemap.repository;

import com.officemap.entity.EmployeeAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeAllocationRepository extends JpaRepository<EmployeeAllocation, Long> {
    EmployeeAllocation findByDesk(Long id);
}
