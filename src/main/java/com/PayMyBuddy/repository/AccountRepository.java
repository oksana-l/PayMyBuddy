package com.PayMyBuddy.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.PayMyBuddy.model.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	
	Account findByEmail(String email);

	Account findByUserName(String userName);

	@Override
	Optional<Account> findById(Long id);
	
	@Query( value = "SELECT a.* FROM ACCOUNT a "
			+ "JOIN CONNECTION c ON a.id = c.connected_account_id "
			+ "WHERE c.account_id = ?1",
		    countQuery = "SELECT count(*) FROM CONNECTION WHERE account_id = ?1",
		    nativeQuery = true )
	Page<Account> findAccountsByConnectedAccountId(Long id, Pageable pageable);
}
