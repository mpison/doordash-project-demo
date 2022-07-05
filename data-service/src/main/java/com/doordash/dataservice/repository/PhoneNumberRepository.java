package com.doordash.dataservice.repository;

import com.doordash.dataservice.model.PhoneNumbers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumbers, Long> {
    @Query(value = "CALL insert_phone_numbers(:phone_number_input, :phone_type_input)", nativeQuery = true)
    PhoneNumbers insertPhoneNumber(@Param("phone_number_input")String phoneNumber, @Param("phone_type_input") String phoneType);
}
