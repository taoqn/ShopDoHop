package com.saledohop.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.saledohop.model.cart;
import com.saledohop.model.catalog;
import com.saledohop.model.product;
import com.saledohop.repository.productRepository;

@Service
public class productService {

	@Autowired
	private productRepository proRepository;

	public void save(product pro) {
		proRepository.save(pro);
	}

	public void update_view_product(int id) {
		product pro = findProductByID(id);
		pro.setView_product(pro.getView_product()+1);
		proRepository.save(pro);
	}

	public product findProductByID(int id) {
		return proRepository.getOne(id);
	}

	public List<product> getListProduct() {
		return proRepository.findAll();
	}

	public List<?> find_ListProduct_Info_sort_page(String lang, Pageable pageable){
		return proRepository.find_ListProduct_Info_sort_page(lang, pageable);
	}
	
	public List<?> findListProduct_InfoByID_Catalog(catalog id, String lang) {
		return proRepository.find_ListProduct_Info_By_ID_Catalog(id, lang);
	}
	
	public List<?> findListProduct_InfoByValue(String value, String lang){
		return proRepository.find_ListProduct_Info_By_Value(value, lang);
	}

	public Object findListProduct_InfoByID(int id, String lang) {
		return proRepository.find_ListProduct_Info_By_ID(id, lang);
	}
	
	public boolean containsProduct(Set<cart> tb, int id){
		for (cart c : tb) {
			if(c.getId_product().getId_product() == id){
				return true;
			}
		}
		return false;
	}

}
