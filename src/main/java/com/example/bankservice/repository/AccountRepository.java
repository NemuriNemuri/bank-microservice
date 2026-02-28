package com.example.bankservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.bankservice.repository.entity.AccountEntity;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {

}
