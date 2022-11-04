package com.officemap.repository;

import com.officemap.database.Structure;
import com.officemap.entity.StructureType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StructureRepository extends JpaRepository<Structure, Long> {
//    List<Structure> findAllByType(Iterable<StructureType> type);
//    List<Structure> findByParentId(Long parentId);
}
