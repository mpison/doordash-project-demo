package com.doordash.dataservice.controller;

import com.doordash.dataservice.model.PhoneTypeEnum;
import com.doordash.dataservice.service.PhoneNumberService;
import com.doordash.dataservice.service.dto.PhoneNumberDTO;
import com.doordash.dataservice.service.dto.PhoneNumberRequestDTO;
import com.doordash.dataservice.service.dto.PhoneNumberResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;

import static com.doordash.dataservice.exceptions.common.DataServiceExceptionCommon.PHONE_NUMBER_FORMAT_EXCEPTION;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PhoneNumberController.class)
public class PhoneNumberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PhoneNumberService phoneNumberService;

    @Autowired private ObjectMapper mapper;

    @Test
    public void createPhoneNumber_whenHomeAndCellNumbersArePresent_shouldReturnValidResponse() throws Exception {

        PhoneNumberRequestDTO phoneNumberRequestDTO = new PhoneNumberRequestDTO();
        phoneNumberRequestDTO.setRawPhoneNumbers("(Home) 415-415-4155 (Cell) 415-514-5145");

        String objStr = mapper.writeValueAsString(phoneNumberRequestDTO);

        PhoneNumberResponseDTO phoneNumberResponseDTO = new PhoneNumberResponseDTO();
        List<PhoneNumberDTO> phoneNumberDTOList = new ArrayList<>();
        PhoneNumberDTO homeNumberDTO = new PhoneNumberDTO();
        homeNumberDTO.setId("1");
        homeNumberDTO.setPhoneNumber("4154154155");
        homeNumberDTO.setPhoneType(PhoneTypeEnum.home.name());
        homeNumberDTO.setOccurence(1);

        PhoneNumberDTO cellNumberDTO = new PhoneNumberDTO();
        cellNumberDTO.setId("2");
        cellNumberDTO.setPhoneNumber("4154154155");
        cellNumberDTO.setPhoneType(PhoneTypeEnum.cell.name());
        cellNumberDTO.setOccurence(1);

        phoneNumberDTOList.add(homeNumberDTO);
        phoneNumberDTOList.add(cellNumberDTO);

        phoneNumberResponseDTO.setPhoneNumberDTOList(phoneNumberDTOList);

        when(phoneNumberService.addPhoneNumbers(anyList())).thenReturn(phoneNumberResponseDTO);

        MvcResult mvcResult = this.mockMvc.perform(post("/api/phone-numbers")
                        .content(objStr)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.results[0].phone_number").value("4154154155"))
                    .andExpect(jsonPath("$.results[0].phone_type").value("home"))
                    .andExpect(jsonPath("$.results[1].phone_number").value("4154154155"))
                    .andExpect(jsonPath("$.results[1].phone_type").value("cell"))
                    .andReturn();
    }

    @Test
    public void createPhoneNumber_whenHomeNumbersIsPresent_shouldReturnValidResponse() throws Exception {

        PhoneNumberRequestDTO phoneNumberRequestDTO = new PhoneNumberRequestDTO();
        phoneNumberRequestDTO.setRawPhoneNumbers("(Home) 415-415-4155");

        String objStr = mapper.writeValueAsString(phoneNumberRequestDTO);

        PhoneNumberResponseDTO phoneNumberResponseDTO = new PhoneNumberResponseDTO();
        List<PhoneNumberDTO> phoneNumberDTOList = new ArrayList<>();
        PhoneNumberDTO homeNumberDTO = new PhoneNumberDTO();
        homeNumberDTO.setId("1");
        homeNumberDTO.setPhoneNumber("4154154155");
        homeNumberDTO.setPhoneType(PhoneTypeEnum.home.name());
        homeNumberDTO.setOccurence(1);

        phoneNumberDTOList.add(homeNumberDTO);

        phoneNumberResponseDTO.setPhoneNumberDTOList(phoneNumberDTOList);

        when(phoneNumberService.addPhoneNumbers(anyList())).thenReturn(phoneNumberResponseDTO);

        MvcResult mvcResult = this.mockMvc.perform(post("/api/phone-numbers")
                .content(objStr)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results[0].phone_number").value("4154154155"))
                .andExpect(jsonPath("$.results[0].phone_type").value("home"))
                .andReturn();
    }

    @Test
    public void createPhoneNumber_whenCellNumbersIsPresent_shouldReturnValidResponse() throws Exception {

        PhoneNumberRequestDTO phoneNumberRequestDTO = new PhoneNumberRequestDTO();
        phoneNumberRequestDTO.setRawPhoneNumbers("(Home) 415-415-4155");

        String objStr = mapper.writeValueAsString(phoneNumberRequestDTO);

        PhoneNumberResponseDTO phoneNumberResponseDTO = new PhoneNumberResponseDTO();
        List<PhoneNumberDTO> phoneNumberDTOList = new ArrayList<>();

        PhoneNumberDTO cellNumberDTO = new PhoneNumberDTO();
        cellNumberDTO.setId("2");
        cellNumberDTO.setPhoneNumber("4154154155");
        cellNumberDTO.setPhoneType(PhoneTypeEnum.cell.name());
        cellNumberDTO.setOccurence(1);

        phoneNumberDTOList.add(cellNumberDTO);

        phoneNumberResponseDTO.setPhoneNumberDTOList(phoneNumberDTOList);

        when(phoneNumberService.addPhoneNumbers(anyList())).thenReturn(phoneNumberResponseDTO);

        MvcResult mvcResult = this.mockMvc.perform(post("/api/phone-numbers")
                .content(objStr)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.results[0].phone_number").value("4154154155"))
                .andExpect(jsonPath("$.results[0].phone_type").value("cell"))
                .andReturn();
    }

    @Test
    public void createPhoneNumber_whenInputIsWrongFormat_shouldReturnInValidResponse() throws Exception {

        PhoneNumberRequestDTO phoneNumberRequestDTO = new PhoneNumberRequestDTO();
        phoneNumberRequestDTO.setRawPhoneNumbers("(Home)415-415-4155");

        String objStr = mapper.writeValueAsString(phoneNumberRequestDTO);

        this.mockMvc.perform(post("/api/phone-numbers")
                .content(objStr)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(PHONE_NUMBER_FORMAT_EXCEPTION));

    }

    @Test
    public void createPhoneNumber_whenInputIsWrongFormat_shouldReturnInValidResponse2() throws Exception {

        PhoneNumberRequestDTO phoneNumberRequestDTO = new PhoneNumberRequestDTO();
        phoneNumberRequestDTO.setRawPhoneNumbers("(Home) 415d415d4155 (Cell) 415-514-5145");

        String objStr = mapper.writeValueAsString(phoneNumberRequestDTO);

        this.mockMvc.perform(post("/api/phone-numbers")
                .content(objStr)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(PHONE_NUMBER_FORMAT_EXCEPTION));

    }

}
