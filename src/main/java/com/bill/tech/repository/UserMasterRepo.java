package com.bill.tech.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bill.tech.entity.UserMaster;

public interface UserMasterRepo extends JpaRepository<UserMaster, Long> {
	Optional<UserMaster> findByEmailId(String emailId);
	List<UserMaster> findByRolesId(Long roleId);

}
