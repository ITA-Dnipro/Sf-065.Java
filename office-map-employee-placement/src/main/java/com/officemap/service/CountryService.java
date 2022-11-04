package com.officemap.service;

import com.officemap.database.Structure;
import com.officemap.dto.StructureDto;
import com.officemap.mapper.StructureMapper;
import com.officemap.repository.StructureRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.officemap.entity.StructureType.COUNTRY;

@Service
public class CountryService extends StructureMapper {
    private final StructureRepository structureRepository;

    public CountryService(StructureRepository structureRepository) {
        this.structureRepository = structureRepository;
    }

    public List<StructureDto> getCountriesList() {
        List<Structure> countriesList = structureRepository.findAll()
                        .stream().filter(structure -> structure.getStructureType().equals(COUNTRY)).toList();
        List<StructureDto> countryDtoList = new ArrayList<>();
        countriesList.forEach(country -> countryDtoList.add(structureEntityToDto(country)));
        return countryDtoList;
    }

    public StructureDto addNewCountry(StructureDto countryDto) {

        countryDto.setStructureType("COUNTRY");
        countryDto.setParentId(0L);
        Structure newCountryEntity = structureDtoToEntity(countryDto);
        structureRepository.save(newCountryEntity);
        return structureEntityToDto(newCountryEntity);
    }

    public StructureDto updateCountry(Long id, StructureDto countryDto) {
        if (structureRepository.findById(id).isEmpty()) {
            throw new Error("Country not found");
        } else {
            Structure countryFromDb = structureRepository.findById(id).get();
            countryFromDb.setName(countryDto.getName());
            countryFromDb.setDetails(countryDto.getDetails());
            structureRepository.save(countryFromDb);
            return structureEntityToDto(countryFromDb);
        }
    }
}
