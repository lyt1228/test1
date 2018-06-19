package com.gzzyy.shop.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gzzyy.shop.domain.Product;
import com.gzzyy.shop.service.ProductService;
import com.gzzyy.shop.utils.BaseServlet;
import com.gzzyy.shop.utils.BeanFactory;



/**
 * 首页的Servlet:
 */
public class IndexServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String index(HttpServletRequest req,HttpServletResponse resp){
		try{
			// 查询最新商品:
			ProductService productService = (ProductService) BeanFactory.getBean("productService");
			List<Product> newList = productService.findByNew();
			// 查询热门商品:
			List<Product> hotList = productService.findByHot();
			req.setAttribute("newList", newList);
			req.setAttribute("hotList", hotList);
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		return "/jsp/index.jsp";
	}
}
