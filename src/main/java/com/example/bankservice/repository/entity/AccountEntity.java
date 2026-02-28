package com.example.bankservice.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "account")
public class AccountEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter
	@Getter
    private Long accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Setter
	@Getter
	private AccountType accountType = AccountType.S;

    @Setter
	@Getter
    private Double availableBalance = 500.0;

    @ManyToOne
    @JoinColumn(name = "customer_number")
    @Setter
	@Getter
    private CustomerEntity customer;
}
