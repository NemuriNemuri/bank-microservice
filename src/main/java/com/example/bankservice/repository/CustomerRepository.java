package com.example.bankservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bankservice.repository.entity.CustomerEntity;

public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {

}
