package com.doordash.dataservice.service;

import com.doordash.dataservice.model.PhoneNumbers;
import com.doordash.dataservice.model.PhoneTypeEnum;
import com.doordash.dataservice.repository.PhoneNumberRepository;
import com.doordash.dataservice.service.dto.PhoneNumberResponseDTO;
import com.doordash.dataservice.service.mapper.PhoneNumberMapper;
import com.doordash.dataservice.service.mapper.PhoneNumberMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PhoneNumberServiceTest {

    @Mock
    PhoneNumberRepository phoneNumberRepository;

    PhoneNumberMapper phoneNumberMapper;

    @InjectMocks
    PhoneNumberService phoneNumberService;

    @BeforeEach
    void setup(){
        phoneNumberMapper = new PhoneNumberMapperImpl();
        ReflectionTestUtils.setField(phoneNumberService, "phoneNumberMapper", phoneNumberMapper);
    }

    @Test
    public void addPhoneNumbers_whenValidPhoneNumbers_thenShouldReturnPhoneNumber(){

        PhoneNumbers phoneNumbers1 = new PhoneNumbers();
        phoneNumbers1.setId(1L);
        phoneNumbers1.setPhoneNumber("4154154155");
        phoneNumbers1.setPhoneType(PhoneTypeEnum.home);
        phoneNumbers1.setOccurence(1L);

        PhoneNumbers phoneNumbers2 = new PhoneNumbers();
        phoneNumbers2.setId(2L);
        phoneNumbers2.setPhoneNumber("4154154156");
        phoneNumbers2.setPhoneType(PhoneTypeEnum.cell);
        phoneNumbers2.setOccurence(1L);

        when(phoneNumberRepository.insertPhoneNumber("4154154155", PhoneTypeEnum.home.name())).thenReturn(phoneNumbers1);
        when(phoneNumberRepository.insertPhoneNumber("4154154156", PhoneTypeEnum.cell.name())).thenReturn(phoneNumbers2);

        List<PhoneNumbers> phoneNumbersList = new ArrayList<>();
        phoneNumbersList.add(phoneNumbers1);
        phoneNumbersList.add(phoneNumbers2);

        PhoneNumberResponseDTO phoneNumberResponseDTO = phoneNumberService.addPhoneNumbers(phoneNumbersList);

        assertEquals("4154154155",phoneNumberResponseDTO.getPhoneNumberDTOList().get(0).getPhoneNumber());
        assertEquals(PhoneTypeEnum.home.name(),phoneNumberResponseDTO.getPhoneNumberDTOList().get(0).getPhoneType());
        assertEquals(1,phoneNumberResponseDTO.getPhoneNumberDTOList().get(0).getOccurence());

        assertEquals("4154154156",phoneNumberResponseDTO.getPhoneNumberDTOList().get(1).getPhoneNumber());
        assertEquals(PhoneTypeEnum.cell.name(),phoneNumberResponseDTO.getPhoneNumberDTOList().get(1).getPhoneType());
        assertEquals(1,phoneNumberResponseDTO.getPhoneNumberDTOList().get(1).getOccurence());

    }

    @Test
    public void addPhoneNumbers_whenInValidPhoneNumbers_thenShouldReturnException(){

        PhoneNumbers phoneNumbers1 = new PhoneNumbers();
        phoneNumbers1.setId(1L);
        phoneNumbers1.setPhoneNumber("");
        phoneNumbers1.setPhoneType(PhoneTypeEnum.home);
        phoneNumbers1.setOccurence(1L);

        PhoneNumbers phoneNumbers2 = new PhoneNumbers();
        phoneNumbers2.setId(2L);
        phoneNumbers2.setPhoneNumber("");
        phoneNumbers2.setPhoneType(PhoneTypeEnum.cell);
        phoneNumbers2.setOccurence(1L);

        when(phoneNumberRepository.insertPhoneNumber(anyString(), anyString()))
                .thenThrow(new JpaSystemException(new RuntimeException("Dao Exception")));

        List<PhoneNumbers> phoneNumbersList = new ArrayList<>();
        phoneNumbersList.add(phoneNumbers1);
        phoneNumbersList.add(phoneNumbers2);

        JpaSystemException exception =
                assertThrows(JpaSystemException.class, () -> phoneNumberService.addPhoneNumbers(phoneNumbersList));

        assertNotNull(exception);

    }

}
