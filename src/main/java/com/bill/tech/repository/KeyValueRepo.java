package com.bill.tech.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bill.tech.entity.KeyValue;

@Repository
public interface KeyValueRepo extends JpaRepository<KeyValue, Long> {
   Optional< KeyValue> findByKey(String key); 
}
