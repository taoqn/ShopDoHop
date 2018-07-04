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
@Table(name = "product_info")
public class product_info {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_product_info")
	private int id_product_info;
    
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "id_product")
	private product id_product;
	
	@Column(name = "name_product_info")
	private String name_product_info;
	
	@Column(name = "packed_product_info")
	private String packed_product_info;
	
	@Column(name = "ingredients_product_info")
	private String ingredients_product_info;
	
	@Column(name = "for_use_product_info")
	private String for_use_product_info;
	
	@Column(name = "for_preserve_product_info")
	private String for_preserve_product_info;
	
	@Column(name = "lang_product_info")
	private String lang_product_info;
	
	public product_info(){
		
	}

	public int getId_product_info() {
		return id_product_info;
	}

	public void setId_product_info(int id_product_info) {
		this.id_product_info = id_product_info;
	}

	public product getId_product() {
		return id_product;
	}

	public void setId_product(product id_product) {
		this.id_product = id_product;
	}

	public String getName_product_info() {
		return name_product_info;
	}

	public void setName_product_info(String name_product_info) {
		this.name_product_info = name_product_info;
	}

	public String getPacked_product_info() {
		return packed_product_info;
	}

	public void setPacked_product_info(String packed_product_info) {
		this.packed_product_info = packed_product_info;
	}

	public String getIngredients_product_info() {
		return ingredients_product_info;
	}

	public void setIngredients_product_info(String ingredients_product_info) {
		this.ingredients_product_info = ingredients_product_info;
	}

	public String getFor_use_product_info() {
		return for_use_product_info;
	}

	public void setFor_use_product_info(String for_use_product_info) {
		this.for_use_product_info = for_use_product_info;
	}

	public String getFor_preserve_product_info() {
		return for_preserve_product_info;
	}

	public void setFor_preserve_product_info(String for_preserve_product_info) {
		this.for_preserve_product_info = for_preserve_product_info;
	}

	public String getLang_product_info() {
		return lang_product_info;
	}

	public void setLang_product_info(String lang_product_info) {
		this.lang_product_info = lang_product_info;
	}
	
}