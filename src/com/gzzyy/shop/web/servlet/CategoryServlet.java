package com.gzzyy.shop.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gzzyy.shop.domain.Category;
import com.gzzyy.shop.service.CategoryService;
import com.gzzyy.shop.utils.BaseServlet;
import com.gzzyy.shop.utils.BeanFactory;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class CategoryServlet
 */
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 查询所有分类的Servlet的执行的方法:findAll
	 */
	public String findAll(HttpServletRequest req,HttpServletResponse resp){
		// 调用业务层:
		try{
			CategoryService categoryService = (CategoryService) BeanFactory.getBean("categoryService");
			List<Category> list = categoryService.findAll();
			// 将list转成JSON: 
			JSONArray jsonArray = JSONArray.fromObject(list);
			System.out.println(jsonArray.toString());
			
			resp.getWriter().println(jsonArray.toString());
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		return null;
	}
}
