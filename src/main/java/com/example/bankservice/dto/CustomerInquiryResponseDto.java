package com.example.bankservice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerInquiryResponseDto {

	private Long customerNumber;
    private String customerName;
    private String customerMobile;
    private String customerEmail;
    private String address1;
    private String address2;

    private List<AccountDetailsDto> savings;

    private int transactionStatusCode;
    private String transactionStatusDescription;
    
}
