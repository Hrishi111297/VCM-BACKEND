package com.bill.tech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bill.tech.entity.Education;

public interface EducationRepo  extends JpaRepository<Education, Long>{

List<	Education >findByUserId(Long userId);
boolean existsByUserIdAndDegree(Long userId,String degree);

}
