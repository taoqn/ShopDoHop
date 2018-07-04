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
@Table(name = "order_account_lang")
public class order_account_lang {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_lang")
	private int id_lang;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_order")
	private order_account id_order;
	
	@Column(name = "product_name")
	private String product_name;
	
	@Column(name = "lang")
	private String lang;
	
	public order_account_lang(){
		
	}

	public int getId_lang() {
		return id_lang;
	}

	public void setId_lang(int id_lang) {
		this.id_lang = id_lang;
	}

	public order_account getId_order() {
		return id_order;
	}

	public void setId_order(order_account id_order) {
		this.id_order = id_order;
	}

	public String getProduct_name() {
		return product_name;
	}

	public void setProduct_name(String product_name) {
		this.product_name = product_name;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}
	
}