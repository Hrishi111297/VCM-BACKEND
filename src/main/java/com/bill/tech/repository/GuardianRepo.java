package com.bill.tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bill.tech.entity.GuardianDetail;

public interface GuardianRepo extends JpaRepository<GuardianDetail, Long>{

	GuardianDetail findByUserId(Long userId);

}
