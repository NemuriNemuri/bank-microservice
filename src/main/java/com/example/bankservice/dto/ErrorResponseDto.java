package com.example.bankservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDto {
	// status code
    private int transactionStatusCode;
    // status description
    private String transactionStatusDescription;
    
}
