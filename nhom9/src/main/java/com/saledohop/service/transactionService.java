package com.saledohop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.saledohop.model.account;
import com.saledohop.model.transaction;
import com.saledohop.repository.transactionRepository;

@Service
public class transactionService {

	@Autowired
	private transactionRepository tranRepository;
	
	public transaction getOne(int id) {
		return tranRepository.getOne(id);
	}
	
	public void deleteOne(int id) {
		tranRepository.delete(id);
	}
	
	public void save(transaction tran) {
		tranRepository.save(tran);
	}
	
	public List<transaction> findAll(){
		return tranRepository.findAll();
	}
	
	public List<transaction> findAllbyDateFinish(){
		return tranRepository.findAllbyDateFinish();
	}
	
	public List<transaction> findAllbyIdAccount(account acc){
		return tranRepository.findAllbyIdAccount(acc);
	}
	
	public List<?> find_ListOrder_byID(transaction id, String lang){
		return tranRepository.find_ListOrder_byID(id, lang);
	}

}
