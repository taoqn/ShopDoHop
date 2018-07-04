package com.saledohop.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "order_account")
public class order_account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_order")
	private int id_order;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_transaction")
	private transaction id_transaction;
	
	@Column(name = "product_price")
	private double product_price;
	
	@Column(name = "product_number")
	private int product_number;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id_order")
	private Set<order_account_lang> order_account_lang_table;
	
	public order_account(){
		
	}

	public int getId_order() {
		return id_order;
	}

	public void setId_order(int id_order) {
		this.id_order = id_order;
	}

	public transaction getId_transaction() {
		return id_transaction;
	}

	public void setId_transaction(transaction id_transaction) {
		this.id_transaction = id_transaction;
	}

	public double getProduct_price() {
		return product_price;
	}

	public void setProduct_price(double product_price) {
		this.product_price = product_price;
	}

	public int getProduct_number() {
		return product_number;
	}

	public void setProduct_number(int product_number) {
		this.product_number = product_number;
	}

	public Set<order_account_lang> getOrder_account_lang_table() {
		return order_account_lang_table;
	}

	public void setOrder_account_lang_table(Set<order_account_lang> order_account_lang_table) {
		this.order_account_lang_table = order_account_lang_table;
	}
	
}