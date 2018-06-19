package com.gzzyy.shop.web.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.gzzyy.shop.domain.Cart;
import com.gzzyy.shop.domain.CartItem;
import com.gzzyy.shop.domain.Product;
import com.gzzyy.shop.service.ProductService;
import com.gzzyy.shop.utils.BaseServlet;
import com.gzzyy.shop.utils.BeanFactory;



/**
 * 购物模块的Servlet
 */
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 添加到购物车执行的方法:addCart
	 */
	public String addCart(HttpServletRequest req,HttpServletResponse resp){
		// 接收参数:
		try{
			String pid = req.getParameter("pid");
			Integer count = Integer.parseInt(req.getParameter("count"));
			// 封装CartItem:
			CartItem cartItem = new CartItem();
			cartItem.setCount(count);
			ProductService productService = (ProductService) BeanFactory.getBean("productService");
			Product product = productService.findByPid(pid);
			cartItem.setProduct(product);
			// 调用Cart中的方法处理
			Cart cart = getCart(req);
			cart.addCart(cartItem);
			// 页面跳转
			resp.sendRedirect(req.getContextPath()+"/jsp/cart.jsp");
			return null;
		}catch(Exception e){
			e.printStackTrace();
			throw new RuntimeException();
		}
		
	}
	
	/**
	 * 清空购物车的执行的方法:
	 * @param req
	 * @return
	 */
	public String clearCart(HttpServletRequest req,HttpServletResponse resp){
		try{
			// 调用Cart中的clearCart的方法:
			Cart cart = getCart(req);
			cart.clearCart();
			// 页面跳转：
			resp.sendRedirect(req.getContextPath()+"/jsp/cart.jsp");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 从购物车中移除购物项的方法:removeCart
	 * @param req
	 * @return
	 */
	public String removeCart(HttpServletRequest req,HttpServletResponse resp){
		try{
			// 接收参数:
			String pid = req.getParameter("pid");
			// 调用Cart中的clearCart的方法:
			Cart cart = getCart(req);
			cart.removeCart(pid);
			// 页面跳转：
			resp.sendRedirect(req.getContextPath()+"/jsp/cart.jsp");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	

	private Cart getCart(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Cart cart = (Cart) session.getAttribute("cart");
		if(cart == null){
			cart = new Cart();
			session.setAttribute("cart", cart);
		}
		return cart;
	}
}
