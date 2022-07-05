package com.doordash.dataservice.repository;

import com.doordash.dataservice.model.PhoneNumbers;
import com.doordash.dataservice.model.PhoneTypeEnum;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.doordash.dataservice.TestProfiles.TEST_CONTAINERS;
import static com.doordash.dataservice.TestTags.TEST_CONTAINERS_TAG;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Testcontainers
@ActiveProfiles(profiles = TEST_CONTAINERS)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayName("PhoneNumberRepository: insertPhoneNumber tests with Mysql Testcontainers instance")
@Tag(TEST_CONTAINERS_TAG)
public class PhoneNumberRepositoryTest {
    @Autowired
    PhoneNumberRepository phoneNumberRepository;

    @Test
    @DisplayName("when phone numbers are valid, should return valid phone numbers")
    void insertPhoneNumber_whenPhoneNumbersAreValid_shouldInsertIntoDatabaseAndReturnValidPhoneNumbers(){
        PhoneNumbers phoneNumbers = phoneNumberRepository.insertPhoneNumber("4154154155", PhoneTypeEnum.home.name());
        PhoneNumbers phoneNumbers2 = phoneNumberRepository.insertPhoneNumber("4154154156", PhoneTypeEnum.cell.name());

        assertNotNull(phoneNumbers.getId());
        assertEquals("4154154155", phoneNumbers.getPhoneNumber());
        assertEquals(PhoneTypeEnum.home.name(), phoneNumbers.getPhoneType().name());
        assertNotNull(phoneNumbers.getOccurence());

        assertNotNull(phoneNumbers2.getId());
        assertEquals("4154154156", phoneNumbers2.getPhoneNumber());
        assertEquals(PhoneTypeEnum.cell.name(), phoneNumbers2.getPhoneType().name());
        assertNotNull(phoneNumbers2.getOccurence());
    }

    @Test
    @DisplayName("when phone numbers are Invalid, should throw exception")
    void insertPhoneNumber_whenPhoneNumbersAreValid_shouldThrowException(){
        JpaSystemException exception =
                assertThrows(JpaSystemException.class,
                        () -> phoneNumberRepository.insertPhoneNumber("", PhoneTypeEnum.home.name()));
        JpaSystemException exception2 =
                assertThrows(JpaSystemException.class,
                        () -> phoneNumberRepository.insertPhoneNumber("", PhoneTypeEnum.cell.name()));
        assertNotNull(exception);
        assertNotNull(exception2);

    }
}
