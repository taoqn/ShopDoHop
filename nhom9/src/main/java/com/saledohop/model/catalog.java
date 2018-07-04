package com.saledohop.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "catalog")
public class catalog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_catalog")
	private int id_catalog;
	
	@Lob
	@Column(name = "img_catalog")
	private byte[] img_catalog;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id_catalog")
	private Set<catalog_info> catalog_info_table;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "id_catalog")
	private Set<product> product_table;
	
	public catalog(){
		
	}

	public int getId_catalog() {
		return id_catalog;
	}

	public void setId_catalog(int id_catalog) {
		this.id_catalog = id_catalog;
	}

	public byte[] getImg_catalog() {
		return img_catalog;
	}

	public void setImg_catalog(byte[] img_catalog) {
		this.img_catalog = img_catalog;
	}

	public Set<catalog_info> getCatalog_info_table() {
		return catalog_info_table;
	}

	public void setCatalog_info_table(Set<catalog_info> catalog_info_table) {
		this.catalog_info_table = catalog_info_table;
	}

	public Set<product> getProduct_table() {
		return product_table;
	}

	public void setProduct_table(Set<product> product_table) {
		this.product_table = product_table;
	}
	
}