package com.officemap.repository;

import com.officemap.entity.Floor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FloorsRepository extends JpaRepository<Floor, Long> {
}
