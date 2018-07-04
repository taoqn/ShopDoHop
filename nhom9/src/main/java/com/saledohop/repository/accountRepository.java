package com.saledohop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.saledohop.model.account;

//https://docs.spring.io/spring-data/jpa/docs/current/reference/html/

@Repository
public interface accountRepository extends JpaRepository<account, Integer> {
	
	@Query("select u from account u where u.email = :email")
	account checkEmail(@Param("email") String email);
	
	@Query("select SUM(u.price_product*c.number_cart) as sum "
			+ "from cart c, product u "
			+ "where c.id_account = :id and c.id_product.id_product = u.id_product")
	Object getTotalPriceListCart(@Param("id") account acc);
	
	@Query("select u.id_product, u.price_product, u.created_product, u.view_product, u.like_product, "
			+ "k.name_product_info, k.packed_product_info, "
			+ "k.ingredients_product_info, k.for_use_product_info, k.for_preserve_product_info, "
			+ "c.id_cart, c.number_cart "
			+ "from cart c, product u, product_info k "
			+ "where c.id_account = :id and c.id_product.id_product = u.id_product"
			+ " and u.id_product = k.id_product and k.lang_product_info = :lang")
	List<?> find_ListCartByLanguage(@Param("id") account acc, @Param("lang") String lang);

}
