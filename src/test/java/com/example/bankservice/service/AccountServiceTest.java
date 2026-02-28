package com.example.bankservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.bankservice.dto.AccountCreationRequestDto;
import com.example.bankservice.dto.AccountCreationResponseDto;
import com.example.bankservice.dto.CustomerInquiryResponseDto;
import com.example.bankservice.repository.AccountRepository;
import com.example.bankservice.repository.CustomerRepository;
import com.example.bankservice.repository.entity.AccountEntity;
import com.example.bankservice.repository.entity.AccountType;
import com.example.bankservice.repository.entity.CustomerEntity;

public class AccountServiceTest {
	@Mock
    private CustomerRepository customerRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private AccountService accountService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAccount() {

    	// initialize request dto
        AccountCreationRequestDto request = new AccountCreationRequestDto();
        // set the values for dto
        request.setCustomerName("Test");
        request.setCustomerMobile("09081234567");
        request.setCustomerEmail("test@gmail.com");
        request.setAddress1("test");
        request.setAccountType(AccountType.S);

        // createnew customer entity for saved customer
        CustomerEntity savedCustomer = new CustomerEntity();
        // set the values of the saved customer
        savedCustomer.setCustomerNumber(1L);

        // stub customer repository to always return the saved customer
        when(customerRepository.save(any())).thenReturn(savedCustomer);
        
        // call the method and store in response dto
        AccountCreationResponseDto response = accountService.createAccount(request);

        // check if response is successful with status code 201
        assertEquals(201, response.getTransactionStatusCode());
        assertEquals("Customer account created", response.getTransactionStatusDescription());

        // verify that repositories are called
        verify(customerRepository, times(1)).save(any());
        verify(accountRepository, times(1)).save(any());
    }
    
    @Test
    void testGetCustomerExists() {

    	// initialize customer entity
        CustomerEntity customer = new CustomerEntity();
        // set the values
        customer.setCustomerNumber(1L);
        customer.setCustomerName("Test");
        
        // initialize account entity without values
        AccountEntity account = new AccountEntity();
        
        // set the list of accounts of customer to avoid NullPointerException
        customer.setAccounts(List.of(account));

        // stub the customer repository to always return optional of customer
        when(customerRepository.findById(1L))
                .thenReturn(Optional.of(customer));

        // call the tested method and store the value inside dto
        CustomerInquiryResponseDto response = accountService.getCustomer(1L);
        
        // check if customer retrieval is successful with status code of 302
        assertEquals(302, response.getTransactionStatusCode());
        assertEquals("Customer Account found", response.getTransactionStatusDescription());
    }
    
    @Test
    void testGetCustomerNotExists() {
    	// return empty optional when find customerRepository.findById is called with 99L as argument
        when(customerRepository.findById(99L))
                .thenReturn(Optional.empty());

        // check if the tested method will throw RunTimeException
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> accountService.getCustomer(99L));

        // Check if message is as expected
        assertEquals("Customer not found", exception.getMessage());
    }
}
