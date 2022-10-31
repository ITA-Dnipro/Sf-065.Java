package com.officemap.cotroller;

import com.officemap.dto.StructureDto;
import com.officemap.repository.StructureRepository;
import com.officemap.service.CountryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.officemap.common.ApplicationUrl.URL_COUNTRY;

@RestController
@RequestMapping(URL_COUNTRY)
public class CountryController extends CountryService {

    public CountryController(StructureRepository structureRepository) {
        super(structureRepository);
    }

    @GetMapping
    public List<StructureDto> getAllCountries() {
        return getCountriesList();
    }

    @PostMapping
    public StructureDto addCountry(@RequestBody StructureDto newCountry) {
        return addNewCountry(newCountry);
    }

    @PutMapping("/{id}")
    public StructureDto updateCountry(@PathVariable("id") Long id,
                                      @RequestBody StructureDto countryDto) {
        return updateCountry(id, countryDto);
    }
}
