package com.saledohop.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.saledohop.model.account;
import com.saledohop.repository.accountRepository;

@Service
public class accountService {
	
	@Autowired
    private accountRepository accRepository;
	
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
	public account Email(String email) {
		return accRepository.checkEmail(email);
	}
    
    public void save(account acc, String role) {
        acc.setPassword(bCryptPasswordEncoder.encode(acc.getPassword()));
        acc.setRole(role);
        accRepository.save(acc);
    }
    
    public boolean checkAccount(String pass, account acc){
    	return bCryptPasswordEncoder.matches(pass, acc.getPassword());
    }

	public void save(account acc) {
		accRepository.save(acc);
	}
	
	public account getOneByID(int id) {
		return accRepository.getOne(id);
	}
	
	public List<account> findAll(){
		return accRepository.findAll();
	}
	
	public List<?> getListCardByLanguage(account acc, String lang){
		return accRepository.find_ListCartByLanguage(acc, lang);
	}
	
	public Object getTotalPriceListCart(account acc){
		return accRepository.getTotalPriceListCart(acc);
	}

	public void deleteOne(int id) {
		accRepository.delete(id);
	}
	
}
