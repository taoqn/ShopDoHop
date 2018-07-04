package com.saledohop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.saledohop.model.catalog;

//https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

@Repository
public interface catalogRepository extends JpaRepository<catalog, Integer> {

	@Query("select u.id_catalog, k.name_catalog_info, k.description_catalog_info "
			+ "from catalog u, catalog_info k "
			+ "where u.id_catalog = k.id_catalog and u.id_catalog = :id and k.lang_catalog_info = :lang")
	Object find_Catalog_Info_ByID(@Param("id") int id, @Param("lang") String lang);
	
	@Query("select u.id_catalog, k.name_catalog_info, k.description_catalog_info " + "from catalog u, catalog_info k "
			+ "where u.id_catalog = k.id_catalog and k.lang_catalog_info = :lang")
	List<?> find_Catalog_Info(@Param("lang") String lang);

}
