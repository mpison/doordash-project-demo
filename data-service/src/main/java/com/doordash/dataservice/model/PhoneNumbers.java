package com.doordash.dataservice.model;

import javax.persistence.*;

@Entity(name = "PhoneNumbers")
public class PhoneNumbers {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column
    private Long id;
    @Column(name = "phone_number")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "phone_type")
    private PhoneTypeEnum phoneType;

    @Column
    private Long occurence;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public PhoneTypeEnum getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(PhoneTypeEnum phoneType) {
        this.phoneType = phoneType;
    }

    public Long getOccurence() {
        return occurence;
    }

    public void setOccurence(Long occurence) {
        this.occurence = occurence;
    }
}
