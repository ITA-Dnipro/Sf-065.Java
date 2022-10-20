package com.officemap.repositories;

import com.officemap.entities.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FloorsRepository extends JpaRepository<Floor, Long> {
}
