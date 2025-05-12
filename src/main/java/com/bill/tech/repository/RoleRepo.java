package com.bill.tech.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bill.tech.entity.Role;
import com.bill.tech.entity.UserMaster;

public interface RoleRepo extends JpaRepository<Role, Long> {

	Optional<Role>findByName(String name);
	
	}
