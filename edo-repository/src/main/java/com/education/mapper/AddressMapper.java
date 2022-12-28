package com.education.mapper;

import com.education.entity.Address;
import model.dto.AddressDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Интерфейс для реализации конвертации департамента в ДТО и обратно
 */

@Mapper(componentModel = "spring")
public interface AddressMapper extends AbstractMapper<Address, AddressDto> {
    AddressMapper ADDRESS_MAPPER_MAPPER = Mappers.getMapper(AddressMapper.class);

}
