package com.example.bankservice.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bankservice.dto.AccountCreationRequestDto;
import com.example.bankservice.dto.AccountCreationResponseDto;
import com.example.bankservice.service.AccountService;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

	@Autowired
	private AccountService accountService;

	/**
	 * account creation request
	 * @param request
	 * @return
	 */
    @PostMapping
    public ResponseEntity<AccountCreationResponseDto> createAccount(
            @Valid @RequestBody AccountCreationRequestDto request) {
    	// success response payload
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(accountService.createAccount(request));
    }
}
