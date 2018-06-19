package com.gzzyy.shop.service.impl;

import java.sql.SQLException;

import com.gzzyy.shop.dao.UserDao;
import com.gzzyy.shop.domain.User;
import com.gzzyy.shop.service.UserService;
import com.gzzyy.shop.utils.BeanFactory;
import com.gzzyy.shop.utils.MailUtils;
import com.gzzyy.shop.utils.UUIDUtils;


/**
 * 用户模块的Service的实现类
 * @author admin
 *
 */
public class UserServiceImpl implements UserService {

	@Override
	public User findByUsername(String username)  throws SQLException{
		UserDao userDao = (UserDao) BeanFactory.getBean("userDao");
		return userDao.findByUsername(username);
	}

	@Override
	public void save(User user) throws SQLException {
		UserDao userDao = (UserDao) BeanFactory.getBean("userDao");
		user.setUid(UUIDUtils.getUUID());
		user.setState(1);// 1代表未激活, 2:代表已经激活.
		String code = UUIDUtils.getUUID()+UUIDUtils.getUUID();
		user.setCode(code);
		userDao.save(user);
		
		// 发送激活邮件:
		MailUtils.sendMail(user.getEmail(), code);
	}

	@Override
	public User findByCode(String code) throws SQLException {
		UserDao userDao = (UserDao) BeanFactory.getBean("userDao");
		return userDao.findByCode(code);
	}

	@Override
	public void update(User existUser) throws SQLException {
		UserDao userDao = (UserDao) BeanFactory.getBean("userDao");
		userDao.update(existUser);
	}

	@Override
	public User login(User user) throws SQLException {
		UserDao userDao = (UserDao) BeanFactory.getBean("userDao");
		return userDao.login(user);
	}

}
