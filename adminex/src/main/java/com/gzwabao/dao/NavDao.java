/**
 * 
 */
package com.gzwabao.dao;

import java.util.List;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.gzwabao.entity.Navigation;

/**
 * @author Alex
 *
 */
@IocBean
public class NavDao extends BaseDao<Navigation> {
	private static final Log log = Logs.getLog(NavDao.class);

	/**
	 * 获取所有导航
	 * 
	 * @return
	 */
	public List<Navigation> getAllNav() {
		try {
			return dao.query(Navigation.class, null);
		} catch (Exception e) {
			log.error("获取导航列表出错!", e);
		}
		return null;
	}

}
