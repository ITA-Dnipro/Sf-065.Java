package com.officemap.database;

import com.officemap.entity.DeskType;
import com.officemap.entity.StructureType;

import javax.persistence.*;

import static com.officemap.entity.StructureType.COUNTRY;
import static com.officemap.entity.StructureType.DESK;

@Entity
@Table(name = "structure")
public class Structure {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
//    @Column(nullable = false)
    private StructureType structureType;
//    @Column(nullable = false)
    private String name;
//    @Column(nullable = false)
    private Long parentId;
    private String details;

    public Structure() {
    }

    public Structure(Long id, StructureType structureType, String name, Long parentId, String details) {
        this.id = id;
        this.structureType = structureType;
        this.name = name;
        this.parentId = parentId;
        this.details = details;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StructureType getStructureType() {
        return structureType;
    }

    public void setStructureType(StructureType structureType) {
        this.structureType = structureType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
            this.parentId = parentId;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        if(structureType == DESK) {
            this.details = DeskType.AVAILABLE.getStatus();
        } else {
            this.details = details;
        }
    }
}
