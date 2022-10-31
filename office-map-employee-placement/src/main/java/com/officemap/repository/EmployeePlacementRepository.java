package com.officemap.repository;

import com.officemap.database.EmployeePlacement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeePlacementRepository extends JpaRepository<EmployeePlacement, Long> {
}
