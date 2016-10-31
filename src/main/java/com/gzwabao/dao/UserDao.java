/**
 * 
 */
package com.gzwabao.dao;

import org.nutz.dao.Cnd;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.gzwabao.entity.User;

/**
 * @author Alex
 *
 */
@IocBean
public class UserDao extends BaseDao<User> {
	private static final Log log = Logs.getLog(UserDao.class);

	/**
	 * 根据账号或昵称获取用户
	 * 
	 * @param keyword
	 * @return
	 */
	public User getUserByNameOrNick(String keyword) {
		try {
			User user = dao.fetch(User.class, Cnd.where("name", "=", keyword)
					.or("nickName", "=", keyword));
			return user;
		} catch (Exception e) {
			log.error("通过账号,昵称获取用户失败!", e);
		}
		return null;
	}
}
