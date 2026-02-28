package com.example.bankservice.repository.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "customer")
public class CustomerEntity {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Setter
	@Getter
    private Long customerNumber;

    @Column(length = 50, nullable = false)
    @Setter
	@Getter
    private String customerName;

    @Column(length = 20, nullable = false)
    @Setter
	@Getter
    private String customerMobile;

    @Column(length = 50, nullable = false, unique = true)
    @Setter
	@Getter
    private String customerEmail;

    @Column(length = 100, nullable = false)
    @Setter
	@Getter
    private String address1;

    @Column(length = 100)
    @Setter
	@Getter
    private String address2;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    @Setter
	@Getter
    private List<AccountEntity> accounts;
	
}
