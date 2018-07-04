package com.saledohop.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saledohop.model.cart;
import com.saledohop.repository.cartRepository;

@Service
public class cartService {
	
	@Autowired
	private cartRepository caRepository;
	
	public cart getOne(int id){
		return caRepository.getOne(id);
	}
	
	public void deleteOne(int id){
		caRepository.delete(id);
	}
	
	public void deleteEntities(Set<cart> tb){
		for (cart c : tb) {
			deleteOne(c.getId_cart());
		}
	}
	
	public cart containsProduct(Set<cart> tb, int id){
		for (cart c : tb) {
			if(c.getId_product().getId_product() == id){
				return c;
			}
		}
		return null;
	}

}
