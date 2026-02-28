package com.example.bankservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bankservice.dto.AccountCreationRequestDto;
import com.example.bankservice.dto.AccountCreationResponseDto;
import com.example.bankservice.dto.AccountDetailsDto;
import com.example.bankservice.dto.CustomerInquiryResponseDto;
import com.example.bankservice.repository.AccountRepository;
import com.example.bankservice.repository.CustomerRepository;
import com.example.bankservice.repository.entity.AccountEntity;
import com.example.bankservice.repository.entity.AccountType;
import com.example.bankservice.repository.entity.CustomerEntity;

@Service
public class AccountService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
    private AccountRepository accountRepository;

	/**
	 * account creation service
	 * @param request
	 * @return
	 */
    public AccountCreationResponseDto createAccount(AccountCreationRequestDto request) {

    	// create new customer entity
        CustomerEntity customer = new CustomerEntity();
        // set the values
        customer.setCustomerName(request.getCustomerName());
        customer.setCustomerMobile(request.getCustomerMobile());
        customer.setCustomerEmail(request.getCustomerEmail());
        customer.setAddress1(request.getAddress1());
        customer.setAddress2(request.getAddress2());

        // save the entity
        customer = customerRepository.save(customer);

        // create new account entity
        AccountEntity account = new AccountEntity();
        // set the values
        AccountType type = request.getAccountType() != null
                ? request.getAccountType()
                : AccountType.S;
        account.setAccountType(type);
        account.setCustomer(customer);

        // save the account entity
        accountRepository.save(account);

        // return when account created successfully
        return new AccountCreationResponseDto(
                customer.getCustomerNumber(),
                201,
                "Customer account created"
        );
    }
    
    /**
     * Customer inquiry for get request
     * @param customerNumber
     * @return response for customer inquiry
     */
    public CustomerInquiryResponseDto getCustomer(Long customerNumber) {

    	// new customer entity
        CustomerEntity customer = customerRepository.findById(customerNumber)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        // list of customer accounts
        List<AccountDetailsDto> accounts = customer.getAccounts()
                .stream()
                .map(account -> new AccountDetailsDto(
                        account.getAccountNumber(),
                        account.getAccountType().name().equals("S") ? "Savings" : "Checking",
                        account.getAvailableBalance()
                ))
                .toList();

        // return customer details and accounts
        return new CustomerInquiryResponseDto(
                customer.getCustomerNumber(),
                customer.getCustomerName(),
                customer.getCustomerMobile(),
                customer.getCustomerEmail(),
                customer.getAddress1(),
                customer.getAddress2(),
                accounts,
                302,
                "Customer Account found"
        );
    }
}
