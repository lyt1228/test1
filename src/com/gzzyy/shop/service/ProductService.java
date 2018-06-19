package com.gzzyy.shop.service;

import java.sql.SQLException;
import java.util.List;

import com.gzzyy.shop.domain.PageBean;
import com.gzzyy.shop.domain.Product;



/**
 * 商品Service的接口
 * @author admin
 *
 */
public interface ProductService {

	List<Product> findByHot()throws SQLException ;

	List<Product> findByNew()throws SQLException ;

	PageBean<Product> findByPageCid(String cid, Integer currPage)throws SQLException ;

	Product findByPid(String pid)throws SQLException ;

	PageBean<Product> findByPage(Integer currPage)throws SQLException ;

	void save(Product product)throws SQLException ;

	void update(Product product)throws SQLException ;

	List<Product> findByPushDown()throws SQLException ;

}
