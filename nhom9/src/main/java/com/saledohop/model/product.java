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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "product")
public class product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_product")
	private int id_product;
	
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_catalog")
	private catalog id_catalog;
	
	@Column(name = "price_product")
	private double price_product;
	
	@Lob
	@Column(name = "img_product")
	private byte[] img_product;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "created_product")
	private Date created_product;
	
	@Column(name = "view_product")
	private int view_product;
	
	@Column(name = "like_product")
	private int like_product;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id_product")
	private Set<product_info> product_info_table;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id_product")
	private Set<cart> cart_table;
	
	public product(){
		
	}

	public int getId_product() {
		return id_product;
	}

	public void setId_product(int id_product) {
		this.id_product = id_product;
	}

	public catalog getId_catalog() {
		return id_catalog;
	}

	public void setId_catalog(catalog id_catalog) {
		this.id_catalog = id_catalog;
	}

	public double getPrice_product() {
		return price_product;
	}

	public void setPrice_product(double price_product) {
		this.price_product = price_product;
	}

	public byte[] getImg_product() {
		return img_product;
	}

	public void setImg_product(byte[] img_product) {
		this.img_product = img_product;
	}

	public Date getCreated_product() {
		return created_product;
	}

	public void setCreated_product(Date created_product) {
		this.created_product = created_product;
	}

	public int getView_product() {
		return view_product;
	}

	public void setView_product(int view_product) {
		this.view_product = view_product;
	}

	public int getLike_product() {
		return like_product;
	}

	public void setLike_product(int like_product) {
		this.like_product = like_product;
	}

	public Set<product_info> getProduct_info_table() {
		return product_info_table;
	}

	public void setProduct_info_table(Set<product_info> product_info_table) {
		this.product_info_table = product_info_table;
	}
	
}