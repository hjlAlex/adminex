/**
 * 
 */
package com.gzwabao.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.gzwabao.dao.UserDao;
import com.gzwabao.entity.User;
import com.gzwabao.util.MD5Utils;

/**
 * @author Alex
 *
 */
@IocBean
public class UserService {
	private static final Log log = Logs.getLog(UserService.class);

	@Inject(value = "userDao")
	private UserDao userDao;

	/**
	 * 新增一个用户
	 * 
	 * @param user
	 * @return
	 */
	public int addUser(User user) {
		int result = 400;
		try {
			if (StringUtils.isBlank(user.getName())) {
				log.error("用户名非法!");
				return result;
			}
			if (StringUtils.isBlank(user.getNickName())) {
				log.error("昵称非法!");
				return result;
			}
			if (StringUtils.isBlank(user.getPassword())) {
				log.error("密码非法!");
				return result;
			}
			user.setPassword(MD5Utils.MD5Encode(user.getPassword()));
			user.setCreateTime(new Date());
			user.setUpdateTime(new Date());
			return userDao.save(user) != null ? 200 : 400;
		} catch (Exception e) {
			log.error("新增用户失败!name=" + user.getName(), e);
		}
		return result;
	}

	/**
	 * 更新一个用户(所有字段)
	 * 
	 * @param user
	 * @return
	 */
	public int updateUser(User user) {
		try {
			return userDao.updateAll(user);
		} catch (Exception e) {
			log.error("更新用户失败!name=" + user.getName(), e);
		}
		return 0;
	}

	/**
	 * 通过id获取用户
	 * 
	 * @param id
	 * @return
	 */
	public User getUserById(int id) {
		try {
			return userDao.getById(User.class, id);
		} catch (Exception e) {
			log.error("获取用户失败!id=" + id, e);
		}
		return null;
	}

	/**
	 * 根据条件分页获取用户
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param keyword
	 * @return
	 * @since 2015年12月11日 上午10:12:34
	 */
	public Map<String, Object> getUserList(int curPage, int pageSize,
			String keyword) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Pager pager = new Pager();
			int totalRecord = 0;
			Condition cnd = null;
			if (StringUtils.isBlank(keyword)) {
				totalRecord = userDao.getCount(User.class, null);

			} else {
				cnd = Cnd.where("name", "like", "%" + keyword + "%").or(
						"nickName", "like", "%" + keyword + "%");
				totalRecord = userDao.getCount(User.class, cnd);
			}
			pager.setRecordCount(totalRecord);
			if (curPage <= 0 || pageSize <= 0) {
				pager.setPageNumber(1);
				pager.setPageSize(Pager.DEFAULT_PAGE_SIZE);
			} else {
				pager.setPageNumber(curPage);
				pager.setPageSize(pageSize);
			}
			resultMap.put("pager", pager);
			List<User> recordList = userDao.queryAll(User.class, cnd, pager);
			resultMap.put("recordList", recordList);
			return resultMap;
		} catch (Exception e) {
			log.error("获取用户列表失败!", e);
		}
		return null;
	}

	/**
	 * 用户登录
	 * 
	 * @param name
	 * @param password
	 * @param req
	 * @return
	 */
	public String login(String name, String password, String code,
			HttpServletRequest req) {
		HttpSession session = req.getSession(true);
		String sessionCode = (String) session.getAttribute(session.getId());
		if (!sessionCode.equalsIgnoreCase(code)) {
			return "code_error";
		}
		User user = userDao.getUserByNameOrNick(name);
		if (null == user) {
			return "user_not_exist";
		}
		if (!MD5Utils.MD5Encode(password).equals(user.getPassword())) {
			return "error_password";
		}
		req.getSession().setAttribute(User.SESSION_USER, user);
		return null;
	}

	/**
	 * 更新用户
	 * 
	 * @param oldId
	 * @param user
	 * @return
	 */
	public int updateUserByOid(int oldId, User user) {
		int result = 400;
		try {
			User oldUser = getUserById(oldId);
			if (null == oldUser) {
				log.error("用户不存在!");
				return result;
			}
			if (StringUtils.isNotBlank(user.getNickName())) {
				oldUser.setNickName(user.getNickName());
			}
			if (StringUtils.isNotBlank(user.getPassword())) {
				oldUser.setPassword(MD5Utils.MD5Encode(user.getPassword()));
			}
			oldUser.setUpdateTime(new Date());
			return updateUser(oldUser) > 0 ? 200 : 400;
		} catch (Exception e) {
			log.error("更新用户失败!name=" + user.getName(), e);
		}
		return result;
	}

	/**
	 * 根据id删除用户
	 * 
	 * @param delId
	 * @return
	 */
	public int delAdminById(int delId) {
		int result = 400;
		try {
			User oldUser = getUserById(delId);
			if (null == oldUser) {
				log.error("用户不存在!");
				return result;
			}

			if (1 == delId) {// 超管不许删
				return result;
			}

			return userDao.deleteById(User.class, delId) > 0 ? 200 : 400;
		} catch (Exception e) {
			log.error("删除用户失败!id=" + delId, e);
		}
		return result;
	}

	/**
	 * 批量删除用户
	 * 
	 * @param adminIds
	 * @return
	 * @since 2015年12月12日 下午12:49:23
	 */
	public int delMoreAdmin(String adminIds) {
		int result = 400;
		try {
			if (StringUtils.isBlank(adminIds)) {
				return result;
			}
			String[] delIds = adminIds.split(",");
			for (String delId : delIds) {
				userDao.deleteById(User.class, Integer.parseInt(delId));
			}
			return 200;
		} catch (Exception e) {
			log.error("删除用户失败!adminIds=" + adminIds, e);
		}
		return result;
	}

	/**
	 * 登出
	 * 
	 * @param req
	 */
	public void logout(HttpServletRequest req) {
		if (req.getSession().getAttribute(User.SESSION_USER) != null) {
			req.getSession().removeAttribute(User.SESSION_USER);
		}
		String sessionId = req.getSession().getId();
		if (req.getSession().getAttribute(sessionId) != null) {
			req.getSession().removeAttribute(sessionId);
		}
	}
}
