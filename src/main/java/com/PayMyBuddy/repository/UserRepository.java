package com.PayMyBuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.PayMyBuddy.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer>{

}
