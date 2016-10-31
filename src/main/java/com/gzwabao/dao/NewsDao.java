/**
 * 
 */
package com.gzwabao.dao;

import java.util.List;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.gzwabao.entity.News;

/**
 * @author Alex
 *
 */
@IocBean
public class NewsDao extends BaseDao<News> {
	private static final Log log = Logs.getLog(NewsDao.class);

	/**
	 * 获取所有的资讯
	 * 
	 * @return
	 */
	public List<News> getAllNews() {
		try {
			return dao.query(News.class, null);
		} catch (Exception e) {
			log.error("获取所有资讯出错!", e);
		}
		return null;
	}
}
