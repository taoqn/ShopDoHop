package com.saledohop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.saledohop.model.account;
import com.saledohop.model.transaction;

@Repository
public interface transactionRepository extends JpaRepository<transaction, Integer> {
	
	@Query("select u from transaction u where u.date_finish_transaction = null ")
	List<transaction> findAllbyDateFinish();
	
	@Query("select u from transaction u where u.id_account = :id")
	List<transaction> findAllbyIdAccount(@Param("id") account acc);
	
	@Query("select l.product_name, o.product_number, o.product_price "
			+ "from order_account o, order_account_lang l "
			+ "where o.id_transaction = :id and o.id_order = l.id_order.id_order and l.lang = :lan")
	List<?> find_ListOrder_byID(@Param("id") transaction id, @Param("lan") String lang);

}
