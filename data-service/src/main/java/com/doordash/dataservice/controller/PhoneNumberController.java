package com.doordash.dataservice.controller;

import com.doordash.dataservice.model.PhoneNumbers;
import com.doordash.dataservice.service.PhoneNumberService;
import com.doordash.dataservice.service.dto.PhoneNumberRequestDTO;
import com.doordash.dataservice.service.dto.PhoneNumberResponseDTO;
import com.doordash.dataservice.service.util.PhoneNumberUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class PhoneNumberController {

    @Autowired
    PhoneNumberService phoneService;

    @PostMapping(value = "/phone-numbers", produces = MediaType.APPLICATION_JSON_VALUE)
    public PhoneNumberResponseDTO createPhoneNumber(@Valid @RequestBody PhoneNumberRequestDTO phoneNumberRequestDTO) {

        List<PhoneNumbers> phoneNumbersList = new ArrayList<>();

        PhoneNumberUtil.validatePhoneNumberRequest(phoneNumberRequestDTO, phoneNumbersList);

        // call service to store phone numbers
        return phoneService.addPhoneNumbers(phoneNumbersList);
    }


}
