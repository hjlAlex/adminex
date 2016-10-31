/**
 * 
 */
package com.gzwabao.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.nutz.dao.Dao;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.gzwabao.entity.User;

/**
 * @author Alex 缓存工具类
 */
@IocBean
public class CacheUtil {
	@Inject(value = "cacheManager")
	// 都是用类名的缩写，可以写也可以不写
	private CacheManager cacheManager;

	@Inject(value = "dao")
	// 都是用类名的缩写，可以写也可以不写
	private Dao dao;

	public User getCacheUser() {
		Cache userCache = null;
		if (cacheManager.cacheExists(User.USER_CACHE)) {
			userCache = cacheManager.getCache(User.USER_CACHE);
		} else {
			cacheManager.addCache(User.USER_CACHE);
			userCache = cacheManager.getCache(User.USER_CACHE);
		}
		Element e = userCache.get("admin");
		if (null == e) {
			User user = dao.fetch(User.class, "admin");
			e = new Element("admin", user);
			userCache.put(e);
			return user;
		} else {
			return (User) e.getObjectValue();
		}
	}

}
