package com.doordash.dataservice.service;

import com.doordash.dataservice.model.PhoneNumbers;
import com.doordash.dataservice.repository.PhoneNumberRepository;
import com.doordash.dataservice.service.dto.PhoneNumberDTO;
import com.doordash.dataservice.service.dto.PhoneNumberResponseDTO;
import com.doordash.dataservice.service.mapper.PhoneNumberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class PhoneNumberService {

    @Autowired
    PhoneNumberRepository phoneNumberRepository;

    @Autowired
    PhoneNumberMapper phoneNumberMapper;

    @Transactional
    public PhoneNumberResponseDTO addPhoneNumbers(List<PhoneNumbers> phoneNumbersList) {

        PhoneNumberResponseDTO phoneNumberResponseDTO = new PhoneNumberResponseDTO();
        List<PhoneNumberDTO> phoneNumberDTOList = new ArrayList<>();

        for(PhoneNumbers phoneNumbers: phoneNumbersList){
            phoneNumbers =  phoneNumberRepository.insertPhoneNumber(phoneNumbers.getPhoneNumber(), phoneNumbers.getPhoneType().toString());
            PhoneNumberDTO phoneNumberDTO = phoneNumberMapper.toDto(phoneNumbers);
            phoneNumberDTOList.add(phoneNumberDTO);
        }

        phoneNumberResponseDTO.setPhoneNumberDTOList(phoneNumberDTOList);
        return phoneNumberResponseDTO;
    }
}
