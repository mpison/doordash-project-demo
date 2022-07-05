package com.doordash.dataservice.service.mapper;

import com.doordash.dataservice.model.PhoneNumbers;
import com.doordash.dataservice.service.dto.PhoneNumberDTO;
import org.mapstruct.Mapper;

/**
 * Mapper for the entity PhoneNumber and its DTO PhoneNumberDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PhoneNumberMapper  extends EntityMapper <PhoneNumberDTO, PhoneNumbers>{
    public PhoneNumberDTO toDto(PhoneNumbers phoneNumbers);
}
