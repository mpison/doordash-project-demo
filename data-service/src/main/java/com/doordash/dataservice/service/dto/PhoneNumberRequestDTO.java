package com.doordash.dataservice.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class PhoneNumberRequestDTO {

    @NotNull
    @JsonProperty("raw_phone_numbers")
    private String rawPhoneNumbers;

    public String getRawPhoneNumbers() {
        return rawPhoneNumbers;
    }

    public void setRawPhoneNumbers(String rawPhoneNumbers) {
        this.rawPhoneNumbers = rawPhoneNumbers;
    }
}
