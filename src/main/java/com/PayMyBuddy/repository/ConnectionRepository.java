package com.PayMyBuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.PayMyBuddy.model.Connection;

@Repository
public interface ConnectionRepository extends CrudRepository<Connection, Integer>{

}
