package com.bill.tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bill.tech.entity.Batch;

public interface BatchRepo extends JpaRepository<Batch, Long>{

}
