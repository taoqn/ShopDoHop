package com.saledohop.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.saledohop.model.catalog;
import com.saledohop.model.product;

@Repository
public interface productRepository extends JpaRepository<product, Integer> {
    
//    @Modifying(clearAutomatically = true)
//    @Query("UPDATE product c SET c.view_product = :view WHERE c.id_product = :id")
//    int update_view_product(@Param("view") int view, @Param("id") int id);
	
	@Query("select u.id_product, u.price_product, u.created_product, u.view_product, u.like_product, "
			+ "k.name_product_info, k.packed_product_info, "
			+ "k.ingredients_product_info, k.for_use_product_info, k.for_preserve_product_info "
			+ "from product u, product_info k "
			+ "where u.id_product = k.id_product and u.id_product = :id and k.lang_product_info = :lang")
	Object find_ListProduct_Info_By_ID(@Param("id") int id, @Param("lang") String lang);

	@Query("select u.id_product, u.price_product, u.created_product, u.view_product, u.like_product, "
			+ "k.name_product_info, k.packed_product_info, "
			+ "k.ingredients_product_info, k.for_use_product_info, k.for_preserve_product_info "
			+ "from product u, product_info k "
			+ "where u.id_product = k.id_product and u.id_catalog = :id and k.lang_product_info = :lang")
	List<?> find_ListProduct_Info_By_ID_Catalog(@Param("id") catalog id_cata, @Param("lang") String lang);
	
	@Query("select u.id_product, u.price_product, u.created_product, u.view_product, u.like_product, "
			+ "k.name_product_info, k.packed_product_info, "
			+ "k.ingredients_product_info, k.for_use_product_info, k.for_preserve_product_info "
			+ "from product u, product_info k "
			+ "where u.id_product = k.id_product and k.lang_product_info = :lang "
			+ "order by u.view_product desc ")
	List<?> find_ListProduct_Info_sort_page(@Param("lang") String lang, Pageable pageable);

	@Query("select u.id_product, u.price_product, u.created_product, u.view_product, u.like_product, "
			+ "k.name_product_info, k.packed_product_info, "
			+ "k.ingredients_product_info, k.for_use_product_info, k.for_preserve_product_info "
			+ "from product u, product_info k "
			+ "where u.id_product = k.id_product and k.lang_product_info = :lang "
			+ "and ( k.name_product_info LIKE CONCAT('%',:value,'%') or k.ingredients_product_info LIKE CONCAT('%',:value,'%') ) ")
	List<?> find_ListProduct_Info_By_Value(@Param("value") String value, @Param("lang") String lang);
	
}
