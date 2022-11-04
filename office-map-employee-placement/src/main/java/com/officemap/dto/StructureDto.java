package com.officemap.dto;

public class StructureDto {
    private Long id;
    private String structureType;
    private String name;
    private Long parentId;
    private String details;


    public StructureDto(Long id, String structureType, String name, Long parentId, String details) {
        this.id = id;
        this.structureType = structureType;
        this.name = name;
        this.parentId = parentId;
        this.details = details;
    }

    public StructureDto() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStructureType() {
        return structureType;
    }

    public void setStructureType(String structureType) {
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
        this.details = details;
    }
}
