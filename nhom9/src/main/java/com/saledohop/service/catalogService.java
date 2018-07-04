package com.saledohop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saledohop.model.catalog;
import com.saledohop.repository.catalogRepository;

@Service
public class catalogService {
	
	@Autowired
    private catalogRepository cataRepository;

	public void save(catalog cata) {
		cataRepository.save(cata);
	}
	
	public catalog findCatalogByID(int id){
		return cataRepository.getOne(id);
	}
	
	public Object findCatalog_InfoByID(int id, String lang){
		return cataRepository.find_Catalog_Info_ByID(id, lang);
	}
	
	public List<?> findCatalog_Info(String lang){
		return cataRepository.find_Catalog_Info(lang);
	}
	
	public List<catalog> getListCatalog(){
		return cataRepository.findAll();
	}

}
