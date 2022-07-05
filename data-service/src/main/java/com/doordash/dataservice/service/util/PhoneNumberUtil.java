package com.doordash.dataservice.service.util;

import com.doordash.dataservice.exceptions.DataServiceException;
import com.doordash.dataservice.model.PhoneNumbers;
import com.doordash.dataservice.model.PhoneTypeEnum;
import com.doordash.dataservice.service.common.DataServiceCommon;
import com.doordash.dataservice.service.dto.PhoneNumberRequestDTO;
import io.micrometer.core.instrument.util.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;


import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

import static com.doordash.dataservice.exceptions.common.DataServiceExceptionCommon.PHONE_NUMBER_FORMAT_EXCEPTION;
import static com.doordash.dataservice.service.common.DataServiceCommon.PHONE_DELIMITER;
import static com.doordash.dataservice.service.common.DataServiceCommon.PHONE_NUMBER_PATTERN;

public class PhoneNumberUtil {

    public static void validatePhoneNumberRequest(@RequestBody @Valid PhoneNumberRequestDTO phoneNumberRequestDTO,
                                                  List<PhoneNumbers> phoneNumbersList)
            throws DataServiceException  {
        String phoneNumbers = phoneNumberRequestDTO.getRawPhoneNumbers();
        //validate
        if (phoneNumbers.contains(DataServiceCommon.HOME_PREFIX) &&
                PhoneNumberUtil.validatePhoneNumberPattern(phoneNumberRequestDTO.getRawPhoneNumbers(), DataServiceCommon.HOME_PREFIX)) {
            phoneNumbersList.add(PhoneNumberUtil.extractPhoneNumbers(phoneNumbers, DataServiceCommon.HOME_PREFIX, PhoneTypeEnum.home));
        }
        if (phoneNumbers.contains(DataServiceCommon.CELL_PREFIX) &&
                PhoneNumberUtil.validatePhoneNumberPattern(phoneNumberRequestDTO.getRawPhoneNumbers(), DataServiceCommon.CELL_PREFIX)) {
            phoneNumbersList.add(PhoneNumberUtil.extractPhoneNumbers(phoneNumbers, DataServiceCommon.CELL_PREFIX, PhoneTypeEnum.cell));
        }

        if(phoneNumbersList.size() == 2 && phoneNumbers.length() != DataServiceCommon.COMPLETE_PHONE_INPUT_PATTERN.length()){
            throw new DataServiceException(HttpStatus.BAD_REQUEST, PHONE_NUMBER_FORMAT_EXCEPTION);
        }else if(phoneNumbersList.size() == 1 && phoneNumbers.contains(DataServiceCommon.HOME_PREFIX)
                && phoneNumbers.length() != DataServiceCommon.HOME_PHONE_INPUT_PATTERN.length()){
            throw new DataServiceException(HttpStatus.BAD_REQUEST, PHONE_NUMBER_FORMAT_EXCEPTION);
        }else if(phoneNumbersList.size() == 1 && phoneNumbers.contains(DataServiceCommon.CELL_PREFIX)
                && phoneNumbers.length() != DataServiceCommon.CELL_PHONE_INPUT_PATTERN.length()){
            throw new DataServiceException(HttpStatus.BAD_REQUEST, PHONE_NUMBER_FORMAT_EXCEPTION);
        }
        //if phoneNumbersList is empty and string is not empty, then throw error
        else if (phoneNumbersList.isEmpty() && !StringUtils.isEmpty(phoneNumbers)) {
            throw new DataServiceException(HttpStatus.BAD_REQUEST, PHONE_NUMBER_FORMAT_EXCEPTION);
        }
    }

    /**
     *
     * @param phoneNumbers
     * @param prefix
     * @return
     * @throws DataServiceException
     */
    public static boolean validatePhoneNumberPattern(String phoneNumbers, String prefix) throws DataServiceException {

        boolean hasError = false;

        try {
            String number = getNumberPattern(phoneNumbers, prefix);
            if (Objects.isNull(number) || StringUtils.isEmpty(number)) {
                hasError = true;
            } else {
                for (int i = 0; i < PHONE_NUMBER_PATTERN.length(); i++) {
                    if ((PHONE_NUMBER_PATTERN.charAt(i) == DataServiceCommon.DIGIT && !Character.isDigit(number.charAt(i))) || (
                            PHONE_NUMBER_PATTERN.charAt(i) == PHONE_DELIMITER && PHONE_DELIMITER != number.charAt(i)
                    )) {
                        hasError = true;
                        break;
                    }
                }
            }
        } catch (Exception exception) {
            hasError = true;
        }

        if (hasError) {
            throw new DataServiceException(HttpStatus.BAD_REQUEST, PHONE_NUMBER_FORMAT_EXCEPTION);
        }

        return !hasError;
    }

    /**
     *
     * @param phoneNumbers
     * @param prefix
     * @param phoneTypeEnum
     * @return
     */
    public static PhoneNumbers extractPhoneNumbers(String phoneNumbers, String prefix, PhoneTypeEnum phoneTypeEnum) {
        String number = getNumberPattern(phoneNumbers, prefix);
        number = number.replace(String.valueOf(PHONE_DELIMITER), Strings.EMPTY);

        PhoneNumbers phoneNumbers1 = new PhoneNumbers();
        phoneNumbers1.setPhoneNumber(number);
        phoneNumbers1.setPhoneType(phoneTypeEnum);
        return phoneNumbers1;
    }

    /**
     *
     * @param phoneNumbers
     * @param prefix
     * @return
     */
    private static String getNumberPattern(String phoneNumbers, String prefix) {
        String number;
        int startIndex = phoneNumbers.indexOf(prefix) + prefix.length();
        int endIndex = startIndex + DataServiceCommon.PHONE_NUMBER_PATTERN.length();
        number = phoneNumbers.substring(startIndex, endIndex);
        return number;
    }
}
