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
@Table(name = "catalog_info")
public class catalog_info {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_catalog_info")
	private int id_catalog_info;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "id_catalog")
	private catalog id_catalog;
	
	@Column(name = "name_catalog_info")
	private String name_catalog_info;
	
	@Column(name = "description_catalog_info")
	private String description_catalog_info;
	
	@Column(name = "lang_catalog_info")
	private String lang_catalog_info;
	
	public catalog_info(){
		
	}

	public int getId_catalog_info() {
		return id_catalog_info;
	}

	public void setId_catalog_info(int id_catalog_info) {
		this.id_catalog_info = id_catalog_info;
	}

	public catalog getId_catalog() {
		return id_catalog;
	}

	public void setId_catalog(catalog id_catalog) {
		this.id_catalog = id_catalog;
	}

	public String getName_catalog_info() {
		return name_catalog_info;
	}

	public void setName_catalog_info(String name_catalog_info) {
		this.name_catalog_info = name_catalog_info;
	}

	public String getDescription_catalog_info() {
		return description_catalog_info;
	}

	public void setDescription_catalog_info(String description_catalog_info) {
		this.description_catalog_info = description_catalog_info;
	}

	public String getLang_catalog_info() {
		return lang_catalog_info;
	}

	public void setLang_catalog_info(String lang_catalog_info) {
		this.lang_catalog_info = lang_catalog_info;
	}
	
}