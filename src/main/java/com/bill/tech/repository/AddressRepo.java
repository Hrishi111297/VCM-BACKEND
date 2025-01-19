package com.bill.tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bill.tech.entity.Address;

public interface AddressRepo extends JpaRepository<Address, Long> {
	Address findByUserId(long userId); 
}
