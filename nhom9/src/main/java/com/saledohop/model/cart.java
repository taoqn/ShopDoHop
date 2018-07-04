package com.saledohop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "cart")
public class cart {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_cart")
	private int id_cart;
    
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_account")
	private account id_account;
    
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_product")
	private product id_product;
	
	@Column(name = "number_cart")
	private int number_cart;
	
	public cart(){
		
	}

	public int getId_cart() {
		return id_cart;
	}

	public void setId_cart(int id_cart) {
		this.id_cart = id_cart;
	}

	public account getId_account() {
		return id_account;
	}

	public void setId_account(account id_account) {
		this.id_account = id_account;
	}

	public product getId_product() {
		return id_product;
	}

	public void setId_product(product id_product) {
		this.id_product = id_product;
	}

	public int getNumber_cart() {
		return number_cart;
	}

	public void setNumber_cart(int number_cart) {
		this.number_cart = number_cart;
	}
	
}