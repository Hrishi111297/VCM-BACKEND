package com.bill.tech.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.bill.tech.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long>  {

}
