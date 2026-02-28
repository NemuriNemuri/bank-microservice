package com.example.bankservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.bankservice.dto.AccountCreationRequestDto;
import com.example.bankservice.dto.AccountCreationResponseDto;
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
}
