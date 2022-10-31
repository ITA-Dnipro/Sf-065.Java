package com.officemap.mapper;

import com.officemap.database.Structure;
import com.officemap.dto.StructureDto;
import com.officemap.entity.StructureType;

public class StructureMapper {

    public static Structure structureDtoToEntity(StructureDto structureDto) {
        StructureType type = StructureType.valueOf(structureDto.getStructureType());

        Structure structureEntity = new Structure();
        structureEntity.setStructureType(type);
        structureEntity.setName(structureDto.getName());
        structureEntity.setParentId(structureDto.getParentId());
        structureEntity.setDetails(structureDto.getDetails());
        return structureEntity;
    }

    public static StructureDto structureEntityToDto(Structure structureEntity) {
        StructureDto structureDto = new StructureDto();
        structureDto.setId(structureEntity.getId());
        structureDto.setStructureType(structureEntity.getStructureType().getType());
        structureDto.setName(structureEntity.getName());
        structureDto.setParentId(structureEntity.getParentId());
        structureDto.setDetails(structureEntity.getDetails());
        return structureDto;
    }
}
