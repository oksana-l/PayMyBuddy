package com.PayMyBuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PayMyBuddy.model.Account;

@Repository
public interface UserRepository extends JpaRepository<Account, Long>{
	Account findByEmail(String email);

	Account findByUserName(String userName);
	
}
