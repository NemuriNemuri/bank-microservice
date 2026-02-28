package com.example.bankservice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.bankservice.controller.AccountController;
import com.example.bankservice.dto.AccountCreationRequestDto;
import com.example.bankservice.dto.AccountCreationResponseDto;
import com.example.bankservice.dto.AccountDetailsDto;
import com.example.bankservice.dto.CustomerInquiryResponseDto;
import com.example.bankservice.repository.entity.AccountType;
import com.example.bankservice.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;

    @MockBean
    private AccountService accountService;

    @Test
    void testCreateAccountNoErrors() throws Exception {
    	// new creation request dto
    	AccountCreationRequestDto request = new AccountCreationRequestDto();
    	request.setCustomerName("Test");
    	request.setCustomerMobile("09081234567");
    	request.setCustomerEmail("test@gmail.com");
    	request.setAddress1("test");
    	request.setAccountType(AccountType.S);
    	// convert request to json
    	String requestJson = objectMapper.writeValueAsString(request);

    	// mock successful response
        AccountCreationResponseDto mockResponse =
            new AccountCreationResponseDto(
                    1L,
                    201,
                    "Customer account created"
            );

        // stub the account service to return the success response
        Mockito.when(accountService.createAccount(Mockito.any()))
                .thenReturn(mockResponse);

        // perform successful mockmvc
        mockMvc.perform(post("/api/v1/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerNumber").value(1))
                .andExpect(jsonPath("$.transactionStatusCode").value(201))
                .andExpect(jsonPath("$.transactionStatusDescription")
                        .value("Customer account created"));
    }
    
    @Test
    void testCreateAccountBlankEmail() throws Exception {

    	// request body when email is blank
    	AccountCreationRequestDto request = new AccountCreationRequestDto();
    	request.setCustomerName("Test");
    	request.setCustomerMobile("09081234567");
    	request.setCustomerEmail("");
    	request.setAddress1("test");
    	request.setAccountType(AccountType.S);

    	// convert request to json
    	String requestJson = objectMapper.writeValueAsString(request);

    	// perform mockMvc expecting error
        mockMvc.perform(post("/api/v1/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.transactionStatusCode").value(400))
                .andExpect(jsonPath("$.transactionStatusDescription")
                        .value("Email is required field"));
    }
    
    @Test
    void testGetCustomer() throws Exception {
    	// list of accounts
        List<AccountDetailsDto> accounts = List.of(
                new AccountDetailsDto(10001L, "Savings", 500.0)
        );
        // customer inquiries list
        CustomerInquiryResponseDto mockResponse =
                new CustomerInquiryResponseDto(
                        1L,
                        "Test",
                        "09081234567",
                        "test@gmail.com",
                        "test",
                        null,
                        accounts,
                        302,
                        "Customer Account found"
                );
        
        // stub account service when customer id is 1
        Mockito.when(accountService.getCustomer(1L))
                .thenReturn(mockResponse);

        // perform mock mvc
        mockMvc.perform(get("/api/v1/account/1"))
                .andExpect(status().isFound())
                .andExpect(jsonPath("$.customerNumber").value(1))
                .andExpect(jsonPath("$.customerName").value("Test"))
                .andExpect(jsonPath("$.savings[0].accountNumber").value(10001))
                .andExpect(jsonPath("$.transactionStatusCode").value(302));
    }
}
