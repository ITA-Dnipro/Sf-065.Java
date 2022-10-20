package com.officemap.repositories;

import com.officemap.entities.Building;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuildingsRepository extends JpaRepository<Building, Long> {
}
