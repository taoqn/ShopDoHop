package com.saledohop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.saledohop.model.cart;

@Repository
public interface cartRepository extends JpaRepository<cart, Integer> {
	
}
