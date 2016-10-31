/**
 * 
 */
package com.gzwabao.dao;

import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.gzwabao.entity.Page;

/**
 * @author Alex
 *
 */
@IocBean
public class PageDao extends BaseDao<Page> {
	private static final Log log = Logs.getLog(PageDao.class);

	/**
	 * 根据strId获取页面
	 * 
	 * @param sid
	 * @return
	 */
	public Page getByStrId(String sid) {
		try {
			Condition cnd = Cnd.where("strId", "=", sid);
			return dao.fetch(Page.class, cnd);
		} catch (Exception e) {
			log.error("获取页面失败!strId=" + sid, e);
		}
		return null;
	}
}
