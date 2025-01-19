package com.bill.tech.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bill.tech.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Long> {

}
