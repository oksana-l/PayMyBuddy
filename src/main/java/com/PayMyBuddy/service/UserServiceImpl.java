package com.PayMyBuddy.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.PayMyBuddy.model.Transaction;
import com.PayMyBuddy.model.User;
import com.PayMyBuddy.model.dto.UserDTO;
import com.PayMyBuddy.model.exception.UserExistsException;
import com.PayMyBuddy.model.exception.UserNotFoundException;
import com.PayMyBuddy.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
		
	@Autowired
	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
	public User addUser(User user) {
		return userRepository.save(user);
	}
	
	public List<User> getUsers() {
		return userRepository.findAll();
	}
	
	public User getUser(Long id) {
		return userRepository.findById(id).orElseThrow(() -> 
			new UserNotFoundException(id));
	}
	
	public User findUserByUserName(String userName) {
        User user = userRepository.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with userName: " + userName);
        }        
        return user;
    }
	
	public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with email: " + email);
        }        
        return user;
    }
	
	public User deleteUser(Long id) {
		User user = getUser(id);
		userRepository.delete(user);
		return user ;
	}
	
	@Transactional
	public User updateUser(Long id, User user) {
		User userToUpdate = getUser(id);
		userToUpdate.setUserName(user.getUserName());
		userToUpdate.setEmail(user.getEmail());
		userToUpdate.setPassword(user.getPassword());
		userToUpdate.setBalance(user.getBalance());
		userToUpdate.setConnections(user.getConnections());
		userToUpdate.setDebits(user.getDebits());
		userToUpdate.setCredits(user.getCredits());
		return userToUpdate;
	}
	
	@Override
	public User save(UserDTO userDto) throws UserExistsException {
	    if (userRepository.findByUserName(userDto.getUserName()) != null ||
	    		userRepository.findByEmail(userDto.getEmail()) != null) {
	        throw new UserExistsException(HttpStatus.BAD_REQUEST, "There is an user with that name or that email adress: ", 
		        	userDto.getUserName(), userDto.getEmail());       
	    }
		User user = new User(userDto.getUserName(), userDto.getEmail(),
				(userDto.getPassword()), new BigDecimal(0.00), new LinkedHashSet<>(), 
				Arrays.asList(), Arrays.asList());
		
		return userRepository.save(user);
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("No user found with email: " + email);
        }
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        
        return new org.springframework.security.core.userdetails.User(
          user.getEmail(), user.getPassword(), enabled, accountNonExpired,
          credentialsNonExpired, accountNonLocked, getAuthorities(new ArrayList<String>()));
	}

	@Override
	public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("No user found");
        }
        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        
        return new org.springframework.security.core.userdetails.User(
          user.getEmail(), user.getPassword(), enabled, accountNonExpired,
          credentialsNonExpired, accountNonLocked, getAuthorities(new ArrayList<String>()));
	}
	
    private static List<GrantedAuthority> getAuthorities (List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }

	public void updateSender(Transaction transaction) {
		User sender = transaction.getSender();
		List<Transaction> debits = sender.getDebits();
		debits.add(transaction);
		BigDecimal balance = sender.getBalance();
		BigDecimal amount = transaction.getAmount();
		if (balance.compareTo(amount) >= 0) {
			sender.setBalance(balance.subtract(amount));
			userRepository.save(sender);
		} else { 
			// Declencher une exception
		}
	}

	public void updateRecepient(Transaction transaction) {
		User recepient = transaction.getRecepient();
		List<Transaction> credits = recepient.getCredits();
		credits.add(transaction);
		recepient.setBalance(recepient.getBalance().add(transaction.getAmount()));
		userRepository.save(recepient);
	}
}
