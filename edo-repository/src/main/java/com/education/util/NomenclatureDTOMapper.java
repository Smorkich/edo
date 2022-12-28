package com.education.util;

import com.education.entity.Nomenclature;
import model.dto.NomenclatureDto;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
* Util class for transform entity to DTO object and DTO to entity object.
*/
public class NomenclatureDTOMapper {

    /**
     * Method tranforms object from entity to DTO
     */
    public static NomenclatureDto toDTO(Nomenclature nomenclature) {
        return NomenclatureDto.builder()
                .id(nomenclature.getId())
                .creationDate(nomenclature.getCreationDate())
                .archivedDate(nomenclature.getArchivedDate())
                .template(nomenclature.getTemplate())
                .currentValue(nomenclature.getCurrentValue())
                .index(nomenclature.getIndex())
                .build();
    }

    /**
     * Method tranforms list of objects from entity to DTO list.
     */
    public static List<NomenclatureDto> listNomenclatureDto(Collection<Nomenclature> nomenclatureList) {
        return nomenclatureList.stream()
                .map(NomenclatureDTOMapper::toDTO)
                .collect(Collectors.toList());
    }

    /**
     * Method tranforms object from DTO to entity
     */
    public static Nomenclature toEntity(NomenclatureDto nomenclatureDto) {
        return Nomenclature.builderNomenclature()
                .creationDate(nomenclatureDto.getCreationDate())
                .archivedDate(nomenclatureDto.getArchivedDate())
                .template(nomenclatureDto.getTemplate())
                .currentValue(nomenclatureDto.getCurrentValue())
                .index(nomenclatureDto.getIndex())
                .build();
    }

}
