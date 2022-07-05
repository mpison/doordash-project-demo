package com.doordash.dataservice.service.util;


import com.doordash.dataservice.exceptions.DataServiceException;
import com.doordash.dataservice.model.PhoneNumbers;
import com.doordash.dataservice.model.PhoneTypeEnum;
import com.doordash.dataservice.service.dto.PhoneNumberRequestDTO;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.doordash.dataservice.exceptions.common.DataServiceExceptionCommon.PHONE_NUMBER_FORMAT_EXCEPTION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class PhoneServiceUtilTest {

    @Test
    public void validatePhoneNumberRequest_whenInputHasHomeAndCell_shouldReturnPhoneNumbers() {
        String input = "(Home) 415-415-4155 (Cell) 415-514-5146";
        PhoneNumberRequestDTO phoneNumberRequestDTO = new PhoneNumberRequestDTO();

        phoneNumberRequestDTO.setRawPhoneNumbers(input);

        List<PhoneNumbers> phoneNumbersList = new ArrayList<>();

        PhoneNumberUtil.validatePhoneNumberRequest(phoneNumberRequestDTO, phoneNumbersList);

        phoneNumbersList.stream().forEach((phoneNumbers)->{
            if(phoneNumbers.getPhoneType() == PhoneTypeEnum.home)
                assertEquals("4154154155", phoneNumbers.getPhoneNumber());
            else
                assertEquals("4155145146", phoneNumbers.getPhoneNumber());
        });
        ;
    }

    @Test
    public void validateAndExtractHomeNumber_whenInputHasIncorrectNumberFormat_shouldThrowException() {
        String input = "(Home) 415-415a4155 (Cell) 415-514-5146";
        PhoneNumberRequestDTO phoneNumberRequestDTO = new PhoneNumberRequestDTO();

        phoneNumberRequestDTO.setRawPhoneNumbers(input);

        List<PhoneNumbers> phoneNumbersList = new ArrayList<>();

        DataServiceException exception = assertThrows(DataServiceException.class, () -> PhoneNumberUtil.validatePhoneNumberRequest(phoneNumberRequestDTO, phoneNumbersList));

        assertEquals(PHONE_NUMBER_FORMAT_EXCEPTION, exception.getMessage());

        String input2 = "(Home)415-415";

        phoneNumberRequestDTO.setRawPhoneNumbers(input2);

        exception = assertThrows(DataServiceException.class, () -> PhoneNumberUtil.validatePhoneNumberRequest(phoneNumberRequestDTO, phoneNumbersList));

        assertEquals(PHONE_NUMBER_FORMAT_EXCEPTION, exception.getMessage());
    }


    @Test
    public void validateAndExtractPhoneNumber_whenInputHasIncorrectNumberFormat_shouldThrowException() {
        PhoneNumberRequestDTO phoneNumberRequestDTO = new PhoneNumberRequestDTO();
        String input2 = "(Home)415-514-5146";

        phoneNumberRequestDTO.setRawPhoneNumbers(input2);

        List<PhoneNumbers> phoneNumbersList = new ArrayList<>();

        DataServiceException exception = assertThrows(DataServiceException.class,
                () -> PhoneNumberUtil.validatePhoneNumberRequest(phoneNumberRequestDTO, phoneNumbersList));

        assertEquals(PHONE_NUMBER_FORMAT_EXCEPTION, exception.getMessage());
    }
}
