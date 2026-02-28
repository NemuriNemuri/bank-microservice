package com.example.bankservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AccountDetailsDto {

	private Long accountNumber;
    private String accountType;
    private Double availableBalance;
    
}
