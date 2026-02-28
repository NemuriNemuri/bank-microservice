package com.example.bankservice.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.bankservice.repository.entity.AccountType;

import lombok.Data;

@Data
public class AccountCreationRequestDto {

    @NotBlank
    @Size(max = 50)
    private String customerName;

    @NotBlank
    @Size(max = 20)
    private String customerMobile;

    @NotBlank(message = "Email is required field")
    @Email
    @Size(max = 50)
    private String customerEmail;

    @NotBlank
    @Size(max = 100)
    private String address1;
    
    @Size(max = 100)
    private String address2;

    private AccountType accountType;

}
