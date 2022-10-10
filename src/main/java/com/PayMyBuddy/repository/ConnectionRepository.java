package com.PayMyBuddy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.PayMyBuddy.model.Connection;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long>{

}
