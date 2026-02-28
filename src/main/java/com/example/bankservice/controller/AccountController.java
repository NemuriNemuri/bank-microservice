package com.example.bankservice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankservice.dto.AccountCreationRequestDto;
import com.example.bankservice.dto.AccountCreationResponseDto;
import com.example.bankservice.dto.CustomerInquiryResponseDto;
import com.example.bankservice.service.AccountService;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

	@Autowired
	private AccountService accountService;

	/**
	 * account creation request
	 * @param request
	 * @return success response payload
	 */
    @PostMapping
    public ResponseEntity<AccountCreationResponseDto> createAccount(
            @Valid @RequestBody AccountCreationRequestDto request) {
    	// success response payload
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(accountService.createAccount(request));
    }
    
    /**
     * Get request for getting the customer information
     * @param customerNumber
     * @return response payload with customer details
     */
    @GetMapping("/{customerNumber}")	
    public ResponseEntity<CustomerInquiryResponseDto> getCustomer(
            @PathVariable Long customerNumber) {

    	// get the customer with the customer number
        CustomerInquiryResponseDto response =
                accountService.getCustomer(customerNumber);

        // return when success
        return ResponseEntity.status(HttpStatus.FOUND).body(response);
    }
}
