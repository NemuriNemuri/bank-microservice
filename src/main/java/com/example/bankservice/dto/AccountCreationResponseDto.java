package com.example.bankservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountCreationResponseDto {
	private Long customerNumber;
    private int transactionStatusCode;
    private String transactionStatusDescription;
}
