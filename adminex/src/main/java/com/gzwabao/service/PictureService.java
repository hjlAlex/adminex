/**
 * 
 */
package com.gzwabao.service;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.upload.TempFile;

import com.gzwabao.dao.PictureDao;
import com.gzwabao.entity.Picture;

/**
 * @author Alex
 *
 */
@IocBean
public class PictureService {
	private static final Log log = Logs.getLog(PictureService.class);

	@Inject(value = "pictureDao")
	private PictureDao pictureDao;

	/**
	 * 获取图片列表
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param keyword
	 * @param relateIds
	 * @return
	 */
	public Map<String, Object> getPictureList(int curPage, int pageSize,
			String keyword, String relateIds) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Pager pager = new Pager();
			int totalRecord = 0;
			Cnd cnd = Cnd.where("1", "=", 1);
			if (StringUtils.isNotBlank(keyword)) {
				cnd = cnd.and("name", "like", "%" + keyword + "%");

			}
			if (StringUtils.isNotBlank(relateIds)) {
				cnd = cnd.and("id", "in", relateIds);
			}
			totalRecord = pictureDao.getCount(Picture.class, cnd);
			pager.setRecordCount(totalRecord);
			if (curPage <= 0 || pageSize <= 0) {
				pager.setPageNumber(1);
				pager.setPageSize(Pager.DEFAULT_PAGE_SIZE);
			} else {
				pager.setPageNumber(curPage);
				pager.setPageSize(pageSize);
			}
			resultMap.put("pager", pager);
			List<Picture> recordList = pictureDao.queryAll(Picture.class, cnd,
					pager);
			resultMap.put("recordList", recordList);
			return resultMap;
		} catch (Exception e) {
			log.error("获取图片列表失败!", e);
		}
		return null;
	}

	/**
	 * 获取所有的图片
	 * 
	 * @return
	 */
	public List<Picture> getAllPicture() {
		try {
			return pictureDao.getAllPicture();
		} catch (Exception e) {
			log.error("获取所有图片出错!", e);
		}
		return null;
	}

	/**
	 * 获取所有的图片
	 * 
	 * @return
	 */
	public Map<Integer, Picture> getAllPicture2Map() {
		Map<Integer, Picture> map = new HashMap<Integer, Picture>();
		try {
			List<Picture> allPictures = pictureDao.getAllPicture();
			for (Picture picture : allPictures) {
				map.put(picture.getId(), picture);
			}
			return map;
		} catch (Exception e) {
			log.error("获取所有图片出错!", e);
		}
		return null;
	}

	/**
	 * 添加图片
	 * 
	 * @param tf
	 * @param picture
	 * @param req
	 * @return
	 */
	public String addPicture(TempFile tf, Picture picture,
			HttpServletRequest req) {
		if (null == tf) {
			// 未选择图片
			return "not_select_img";
		}
		File f = tf.getFile();
		if (null == getImagSuffex(f.getName())) {
			return "add_error";
		}
		String parent = req.getSession().getServletContext()
				.getRealPath("/upload/image");
		File imgDir = new File(parent);
		if (!imgDir.exists()) {
			imgDir.mkdir();
		}
		File imgFile = new File(imgDir, System.currentTimeMillis()
				+ getImagSuffex(f.getName()));
		try {
			FileUtils.copyFile(f, imgFile);
			picture.setPath("/upload/image/" + imgFile.getName());
			pictureDao.save(picture);
			if (f != null) {
				f.delete();
			}
		} catch (Exception e) {
			log.error("添加失败!", e);
			// 添加失败
			return "add_error";
		}
		return "add_success";
	}

	/**
	 * 更新图片
	 * 
	 * @param tf
	 * @param picture
	 * @param req
	 * @return
	 */
	public String updatePicture(TempFile tf, Picture picture,
			HttpServletRequest req) {
		if (null != tf) {
			File f = tf.getFile();
			if (null == getImagSuffex(f.getName())) {
				return "update_error";
			}
			String oldImagePath = req.getSession().getServletContext()
					.getRealPath(picture.getPath());
			File oldImage = new File(oldImagePath);
			if (oldImage.exists()) {
				oldImage.delete();
			}
			String parent = req.getSession().getServletContext()
					.getRealPath("/upload/image");
			File imgDir = new File(parent);
			if (!imgDir.exists()) {
				imgDir.mkdir();
			}
			File imgFile = new File(imgDir, System.currentTimeMillis()
					+ getImagSuffex(f.getName()));
			try {
				FileUtils.copyFile(f, imgFile);
				picture.setPath("/upload/image/" + imgFile.getName());
				pictureDao.updateAll(picture);
				if (f != null) {
					f.delete();
				}
				return "update_success";
			} catch (Exception e) {
				log.error("更新失败!", e);
				// 添加失败
				return "update_error";
			}
		}
		pictureDao.updateAll(picture);
		return "update_success";
	}

	/**
	 * 根据id获取图片
	 * 
	 * @param pid
	 * @return
	 */
	public Picture getPictureById(int pid) {
		try {
			return pictureDao.getById(Picture.class, pid);
		} catch (Exception e) {
			log.error("获取图片失败!id=" + pid, e);
		}
		return null;
	}

	/**
	 * 根据id删除图片
	 * 
	 * @param delId
	 * @return
	 * @since 2015年12月15日 下午12:36:13
	 */
	public int delPictureById(int delId) {
		int result = 400;
		try {
			Picture oldPicture = getPictureById(delId);
			if (null == oldPicture) {
				log.error("图片不存在!");
				return result;
			}
			return pictureDao.deleteById(Picture.class, delId) > 0 ? 200 : 400;
		} catch (Exception e) {
			log.error("删除图片失败!id=" + delId, e);
		}
		return result;
	}

	/**
	 * 根据图片名称获取后缀
	 * 
	 * @param imageName
	 * @return
	 * @since 2015年12月15日 下午1:01:00
	 */
	private String getImagSuffex(String imageName) {
		int index = imageName.lastIndexOf(".");
		if (-1 != index) {
			return imageName.substring(index);
		}
		return null;
	}

	/**
	 * 删除多个图片
	 * 
	 * @param pIds
	 * @return
	 * @since 2015年12月15日 下午1:22:25
	 */
	public int delMorePicture(String pIds) {
		int result = 400;
		try {
			if (StringUtils.isBlank(pIds)) {
				return result;
			}
			String[] delIds = pIds.split(",");
			for (String delId : delIds) {
				pictureDao.deleteById(Picture.class, Integer.parseInt(delId));
			}
			return 200;
		} catch (Exception e) {
			log.error("删除图片失败!pIds=" + pIds, e);
		}
		return result;
	}
}
