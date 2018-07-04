package com.saledohop.model;

import java.util.Date;
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

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "transaction")
public class transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_transaction")
	private int id_transaction;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_account")
	private account id_account;
	
	@NotEmpty
	@Column(name = "account_name")
	private String account_name;
	
	@Email
	@NotEmpty
	@Column(name = "account_email")
	private String account_email;
	
	@NotEmpty
	@Column(name = "account_phone")
	private String account_phone;
	
	@NotEmpty
	@Column(name = "account_address")
	private String account_address;
	
	@Column(name = "amount_transaction")
	private double amount_transaction;
	
	@Column(name = "comment_transaction")
	private String comment_transaction;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "date_start_transaction")
	private Date date_start_transaction;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "date_finish_transaction")
	private Date date_finish_transaction;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id_transaction")
	private Set<order_account> order_account_table;
	
	public transaction(){
		
	}

	public int getId_transaction() {
		return id_transaction;
	}

	public void setId_transaction(int id_transaction) {
		this.id_transaction = id_transaction;
	}

	public account getId_account() {
		return id_account;
	}

	public void setId_account(account id_account) {
		this.id_account = id_account;
	}

	public String getAccount_name() {
		return account_name;
	}

	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}

	public String getAccount_email() {
		return account_email;
	}

	public void setAccount_email(String account_email) {
		this.account_email = account_email;
	}

	public String getAccount_phone() {
		return account_phone;
	}

	public void setAccount_phone(String account_phone) {
		this.account_phone = account_phone;
	}

	public String getAccount_address() {
		return account_address;
	}

	public void setAccount_address(String account_address) {
		this.account_address = account_address;
	}

	public double getAmount_transaction() {
		return amount_transaction;
	}

	public void setAmount_transaction(double amount_transaction) {
		this.amount_transaction = amount_transaction;
	}

	public String getComment_transaction() {
		return comment_transaction;
	}

	public void setComment_transaction(String comment_transaction) {
		this.comment_transaction = comment_transaction;
	}

	public Date getDate_start_transaction() {
		return date_start_transaction;
	}

	public void setDate_start_transaction(Date date_start_transaction) {
		this.date_start_transaction = date_start_transaction;
	}

	public Date getDate_finish_transaction() {
		return date_finish_transaction;
	}

	public void setDate_finish_transaction(Date date_finish_transaction) {
		this.date_finish_transaction = date_finish_transaction;
	}

	public Set<order_account> getOrder_account_table() {
		return order_account_table;
	}

	public void setOrder_account_table(Set<order_account> order_account_table) {
		this.order_account_table = order_account_table;
	}

}