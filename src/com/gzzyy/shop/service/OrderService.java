package com.gzzyy.shop.service;

import java.sql.SQLException;
import java.util.List;

import com.gzzyy.shop.domain.Order;
import com.gzzyy.shop.domain.OrderItem;
import com.gzzyy.shop.domain.PageBean;



/**
 * 订单模块的Service的接口
 * @author admin
 *
 */
public interface OrderService {

	void save(Order order);

	PageBean<Order> findByUid(String uid, Integer currPage) throws Exception;

	Order findByOid(String oid)throws Exception;

	void update(Order order)throws Exception;

	List<Order> findAll()throws Exception;

	List<Order> findByState(int pstate)throws Exception;

	List<OrderItem> showDetail(String oid)throws Exception;

}
