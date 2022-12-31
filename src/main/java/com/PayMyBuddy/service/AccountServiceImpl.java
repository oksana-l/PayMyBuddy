package com.PayMyBuddy.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.PayMyBuddy.model.Account;
import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.dto.AccountDTO;
import com.PayMyBuddy.model.exception.AccountExistsException;
import com.PayMyBuddy.model.exception.AccountNotFoundException;
import com.PayMyBuddy.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

	private AccountRepository accountRepository;
		
	@Autowired
	public AccountServiceImpl(AccountRepository accountRepository) {
		super();
		this.accountRepository = accountRepository;
	}

	@Override
	public Account getAccount(Long id) {
		return accountRepository.findById(id).orElseThrow(() -> 
			new AccountNotFoundException(id));
	}
	
	@Override
	public Account findAccountByUserName(String userName) {
        Account account = accountRepository.findByUserName(userName);
        if (account == null) {
            throw new UsernameNotFoundException("No user found with userName : " + userName);
        }        
        return account;
    }
	
	@Override
	public Account findAccountByEmail(String email) {
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            throw new UsernameNotFoundException("No user found with email : " + email);
        }        
        return account;
    }
	
	@Override
	@Transactional
	public Account updateAccount(Long id, Account account) {
		Account accountToUpdate = getAccount(id);
		accountToUpdate.setUserName(account.getUserName());
		accountToUpdate.setEmail(account.getEmail());
		accountToUpdate.setPassword(account.getPassword());
		accountToUpdate.setBalance(account.getBalance());
		accountToUpdate.setConnections(account.getConnections());
		accountToUpdate.setDebits(account.getDebits());
		accountToUpdate.setCredits(account.getCredits());
		return accountToUpdate;
	}
	
	@Override
	public Account save(AccountDTO accountDto) throws AccountExistsException {
		Account account = new Account(accountDto.getUserName(), accountDto.getEmail(),
				(accountDto.getPassword()), new BigDecimal(0.00), new LinkedHashSet<>(), 
				Arrays.asList(), Arrays.asList());
		
		return accountRepository.save(account);
	}
	
	@Override
	public boolean ifUserExist(AccountDTO accountDto) {
		return accountRepository.findByUserName(accountDto.getUserName()) != null ||
	    		accountRepository.findByEmail(accountDto.getEmail()) != null;
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            throw new UsernameNotFoundException("No user found with email: " + email);
        }
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        
        return new org.springframework.security.core.userdetails.User(
          account.getEmail(), account.getPassword(), enabled, accountNonExpired,
          credentialsNonExpired, accountNonLocked, getAuthorities(new ArrayList<String>()));
	}

	@Override
	public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email);
        if (account == null) {
            throw new UsernameNotFoundException("No user found");
        }
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        
        return new org.springframework.security.core.userdetails.User(
          account.getEmail(), account.getPassword(), enabled, accountNonExpired,
          credentialsNonExpired, accountNonLocked, getAuthorities(new ArrayList<String>()));
	}
	
    private static List<GrantedAuthority> getAuthorities (List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

    @Override
	public void updateSender(Transaction transaction) {
		Account sender = transaction.getSender();
		List<Transaction> debits = sender.getDebits();
		debits.add(transaction);
		BigDecimal balance = sender.getBalance();
		BigDecimal amount = transaction.getAmount();
		sender.setBalance(balance.subtract(amount));
		accountRepository.save(sender);
	}

    @Override
	public void updateRecepient(Transaction transaction) {
		Account recepient = transaction.getRecepient();
		List<Transaction> credits = recepient.getCredits();
		credits.add(transaction);
		recepient.setBalance(recepient.getBalance().add(transaction.getAmount()));
		accountRepository.save(recepient);
	}
	
    @Override
	public boolean userHasAmount(String senderEmail, BigDecimal amount) {
		Account account = accountRepository.findByEmail(senderEmail);
		return account.getBalance().compareTo(amount)>=0;	
	}
}
