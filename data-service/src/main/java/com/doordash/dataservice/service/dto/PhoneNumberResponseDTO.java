package com.doordash.dataservice.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class PhoneNumberResponseDTO {
    public List<PhoneNumberDTO> phoneNumberDTOList;

    @JsonProperty(value = "results")
    public List<PhoneNumberDTO> getPhoneNumberDTOList() {
        return phoneNumberDTOList;
    }

    public void setPhoneNumberDTOList(List<PhoneNumberDTO> phoneNumberDTOList) {
        this.phoneNumberDTOList = phoneNumberDTOList;
    }
}
