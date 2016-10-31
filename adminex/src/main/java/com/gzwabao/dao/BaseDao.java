/**
 * 
 */
package com.gzwabao.dao;

import java.io.Serializable;
import java.util.List;

import org.nutz.dao.Condition;
import org.nutz.dao.Dao;
import org.nutz.dao.Sqls;
import org.nutz.dao.entity.annotation.Table;
import org.nutz.dao.pager.Pager;
import org.nutz.dao.sql.Sql;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.log.Log;
import org.nutz.log.Logs;

/**
 * 数据库操作基类
 * 
 * @author Alex
 *
 */
public abstract class BaseDao<T extends Serializable> {
	private static final Log log = Logs.getLog(BaseDao.class);
	@Inject(value = "dao")
	protected Dao dao;

	/**
	 * 保存一个对象
	 * 
	 * @param o
	 * @return
	 */
	public T save(T o) {
		return dao.insert(o);
	}

	/**
	 * 更新一个对象的所有字段<br>
	 * (一定要有@Id或者@Name或者@PK声明,且一定主键的值必须保证是有效的)
	 * 
	 * @param o
	 * @return
	 */
	public int updateAll(T o) {
		return dao.update(o);
	}

	/**
	 * 根据id获取一个对象
	 * 
	 * @param c
	 * @param id
	 * @return
	 */
	public T getById(Class<T> c, int id) {
		return dao.fetch(c, id);
	}

	/**
	 * 根据id删除一个对象
	 * 
	 * @param c
	 * @param id
	 * @return
	 * @since 2015年12月11日 上午9:40:21
	 */
	public int deleteById(Class<T> c, int id) {
		return dao.delete(c, id);
	}

	/**
	 * 根据条件获取对象分页列表
	 * 
	 * @param c
	 * @return
	 * @since 2015年12月11日 上午9:41:42
	 */
	public List<T> queryAll(Class<T> c, Condition cnd, Pager pager) {
		return dao.query(c, cnd, pager);
	}

	public List<T> queryAll(Class<T> c) {
		return dao.query(c, null);
	}

	/**
	 * 更新单个字段
	 * 
	 * @param id
	 * @param colName
	 * @param colValue
	 * @return
	 * @since 2015年12月11日 上午9:43:49
	 */
	public boolean updateOne(Class<T> c, int id, String colName, Object colValue) {
		if (c.isAnnotationPresent(Table.class)) {
			String tName = c.getAnnotation(Table.class).value();
			Sql sql = Sqls.create("UPDATE " + tName + " SET " + colName + "="
					+ colValue + " WHERE id = " + id);
			dao.execute(sql);
			return true;
		} else {
			log.error("更新失败," + c.getClass().getName() + "没有@Table注解!");
			return false;
		}
	}

	/**
	 * 根据条件获取对象个数
	 * 
	 * @param c
	 * @param cnd
	 * @return
	 * @since 2015年12月11日 上午10:02:56
	 */
	public int getCount(Class<T> c, Condition cnd) {
		return dao.count(c, cnd);
	}
}
