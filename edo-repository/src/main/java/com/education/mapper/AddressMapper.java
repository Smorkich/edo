package com.education.mapper;

import com.education.entity.Address;
import model.dto.AddressDto;
import org.mapstruct.Mapper;


@Mapper
public interface AddressMapper extends AbstractMapper<Address, AddressDto> {

}
