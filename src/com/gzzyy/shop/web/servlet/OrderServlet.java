package com.gzzyy.shop.web.servlet;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gzzyy.shop.domain.Cart;
import com.gzzyy.shop.domain.CartItem;
import com.gzzyy.shop.domain.Order;
import com.gzzyy.shop.domain.OrderItem;
import com.gzzyy.shop.domain.PageBean;
import com.gzzyy.shop.domain.User;
import com.gzzyy.shop.service.OrderService;
import com.gzzyy.shop.utils.BaseServlet;
import com.gzzyy.shop.utils.BeanFactory;
import com.gzzyy.shop.utils.PaymentUtil;
import com.gzzyy.shop.utils.UUIDUtils;


/**
 * 订单模块的Servlet
 */
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 生成订单的执行的方法:saveOrder
	 */
	public String saveOrder(HttpServletRequest req,HttpServletResponse resp){
		// 封装Order对象:
		Order order = new Order();
		order.setOid(UUIDUtils.getUUID());
		order.setOrdertime(new Date());
		order.setState(1); // 1:未付款.
		// 设置总金额: // 从购物车中获得订单的总金额.
		Cart cart = (Cart) req.getSession().getAttribute("cart");
		if(cart == null){
			req.setAttribute("msg", "购物车是空的呦!");
			return "/jsp/msg.jsp";
		}
		order.setTotal(cart.getTotal());
		// 设置订单所属的用户: // 从登陆的信息中获得用户的信息.
		User existUser = (User) req.getSession().getAttribute("existUser");
		if(existUser == null){
			req.setAttribute("msg", "您还没有登录呦!");
			return "/jsp/login.jsp";
		}
		order.setUser(existUser);
		// 设置订单项:
		for(CartItem cartItem : cart.getMap().values()){
			OrderItem orderItem = new OrderItem();
			orderItem.setItemid(UUIDUtils.getUUID());
			orderItem.setCount(cartItem.getCount());
			orderItem.setSubtotal(cartItem.getSubtotal());
			orderItem.setProduct(cartItem.getProduct());
			orderItem.setOrder(order);
			
			order.getOrderItems().add(orderItem);
		}
		
		// 调用业务层完成保存:
		OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
		orderService.save(order);
		
		// 清空购物车:
		cart.clearCart();
		
		// 页面跳转:
		req.setAttribute("order", order);
		return "/jsp/order_info.jsp";
	}
	
	/**
	 * 根据用户的ID查询订单:findByUid
	 */
	public String findByUid(HttpServletRequest req,HttpServletResponse resp){
		// 接收参数:
		try{
			Integer currPage = Integer.parseInt(req.getParameter("currPage"));
			// 获得用户的信息:
			User user = (User) req.getSession().getAttribute("existUser");
			// 调用业务层:
			OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
			PageBean<Order> pageBean = orderService.findByUid(user.getUid(),currPage);
			
			req.setAttribute("pageBean", pageBean);
		}catch(Exception e){
			e.printStackTrace();
		}
		// 页面跳转:
		return "/jsp/order_list.jsp";
	}
	
	/**
	 * 根据订单id查询订单的方法:findByOid
	 */
	public String findByOid(HttpServletRequest req,HttpServletResponse resp){
		try{
			// 接收参数:
			String oid = req.getParameter("oid");
			// 调用业务层查询:
			OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
			Order order = orderService.findByOid(oid);
			req.setAttribute("order", order);
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return "/jsp/order_info.jsp";
	}
	
	/**
	 * 为订单付款的方法:
	 */
	public String payOrder(HttpServletRequest req,HttpServletResponse resp){
		try{
			// 接收参数:
			String oid = req.getParameter("oid");
			String name = req.getParameter("name");
			String address = req.getParameter("address");
			String telephone = req.getParameter("telephone");
			String pd_FrpId = req.getParameter("pd_FrpId");
			
			// 修改数据库 姓名，地址，电话.
			OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
			Order order = orderService.findByOid(oid);
			order.setAddress(address);
			order.setName(name);
			order.setTelephone(telephone);
			
			orderService.update(order);
			// 付款:跳转到网银的界面:
			// 准备参数:
			String p0_Cmd = "Buy";
			String p1_MerId = "10001126856";
			String p2_Order = oid;
			String p3_Amt = "0.01";
			String p4_Cur = "CNY";
			String p5_Pid = "";
			String p6_Pcat = "";
			String p7_Pdesc = "";
			String p8_Url = "http://localhost:8080/shop/OrderServlet?method=callBack";
			String p9_SAF = "";
			String pa_MP = "";
			String pr_NeedResponse = "1";
			String keyValue = "69cl522AV6q613Ii4W6u8K6XuW8vM1N6bFgyv769220IuYe9u37N4y7rI4Pl";
			String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);
			
			StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
			sb.append("p0_Cmd=").append(p0_Cmd).append("&");
			sb.append("p1_MerId=").append(p1_MerId).append("&");
			sb.append("p2_Order=").append(p2_Order).append("&");
			sb.append("p3_Amt=").append(p3_Amt).append("&");
			sb.append("p4_Cur=").append(p4_Cur).append("&");
			sb.append("p5_Pid=").append(p5_Pid).append("&");
			sb.append("p6_Pcat=").append(p6_Pcat).append("&");
			sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
			sb.append("p8_Url=").append(p8_Url).append("&");
			sb.append("p9_SAF=").append(p9_SAF).append("&");
			sb.append("pa_MP=").append(pa_MP).append("&");
			sb.append("pd_FrpId=").append(pd_FrpId).append("&");
			sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
			sb.append("hmac=").append(hmac);
			
			resp.sendRedirect(sb.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 付款成功后执行的方法:callBack
	 */
	public String callBack(HttpServletRequest req,HttpServletResponse resp){
		try{
			// 接收参数:
			String oid = req.getParameter("r6_Order");
			String money = req.getParameter("r3_Amt");
			
			// 修改订单状态:
			OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
			Order order = orderService.findByOid(oid);
			order.setState(2); 
			orderService.update(order);
			
			/*req.setAttribute("oid", oid);
			req.setAttribute("money", money);*/
			req.setAttribute("msg", "您的订单:"+oid+"付款成功,付款的金额为:"+money);
		}catch(Exception e){
			e.printStackTrace();
		}
		return "/jsp/msg.jsp";
	}
	
	public String finish(HttpServletRequest req,HttpServletResponse resp){
		try{
			String oid = req.getParameter("oid");
			OrderService orderService = (OrderService) BeanFactory.getBean("orderService");
			Order order = orderService.findByOid(oid);
			order.setState(4);
			orderService.update(order);
			
			resp.sendRedirect(req.getContextPath()+"/OrderServlet?method=findByUid&currPage=1");
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
}
