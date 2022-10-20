package com.officemap.repositories;

import com.officemap.entities.EmployeeAllocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeAllocationRepository extends JpaRepository<EmployeeAllocation, Long> {
    EmployeeAllocation findByDesk(Long deskId);
}
