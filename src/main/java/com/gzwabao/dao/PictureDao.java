/**
 * 
 */
package com.gzwabao.dao;

import java.util.List;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.gzwabao.entity.Picture;

/**
 * @author Alex
 *
 */
@IocBean
public class PictureDao extends BaseDao<Picture> {
	private static final Log log = Logs.getLog(PictureDao.class);

	/**
	 * 获取所有的图片
	 * 
	 * @return
	 */
	public List<Picture> getAllPicture() {
		try {
			return dao.query(Picture.class, null);
		} catch (Exception e) {
			log.error("获取所有图片出错!", e);
		}
		return null;
	}
}
