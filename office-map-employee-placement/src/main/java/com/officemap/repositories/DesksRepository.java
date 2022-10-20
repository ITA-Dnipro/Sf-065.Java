package com.officemap.repositories;

import com.officemap.entities.Desk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DesksRepository extends JpaRepository<Desk, Long> {
}
