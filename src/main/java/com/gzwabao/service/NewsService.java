/**
 * 
 */
package com.gzwabao.service;

import java.io.File;
import java.util.Date;
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

import com.gzwabao.dao.NewsDao;
import com.gzwabao.entity.News;
import com.gzwabao.json.ImageJson;
import com.gzwabao.util.DateUtil;

/**
 * @author Alex
 *
 */
@IocBean
public class NewsService {
	private static final Log log = Logs.getLog(NewsService.class);

	@Inject(value = "newsDao")
	private NewsDao newsDao;

	/**
	 * 新增一个页面
	 * 
	 * @param page
	 * @return
	 */
	public int addNews(News news) {
		int result = 400;
		try {
			if (StringUtils.isBlank(news.getTitle())) {
				log.error("资讯标题非法!");
				return result;
			}
			if (StringUtils.isBlank(news.getContent())) {
				log.error("资讯内容非法!");
				return result;
			}
			news.setCreateTime(new Date());
			news.setUpdateTime(new Date());
			return newsDao.save(news) != null ? 200 : 400;
		} catch (Exception e) {
			log.error("新增资讯失败!title=" + news.getTitle(), e);
		}
		return result;
	}

	/**
	 * 获取前端所有资讯
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param datemin
	 * @param datemax
	 * @param keyword
	 * @return
	 * @since 2015年12月17日 下午1:32:53
	 */
	public Map<String, Object> getNewsList(int curPage, int pageSize,
			String datemin, String datemax, String keyword, String relateIds) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Pager pager = new Pager();
			int totalRecord = 0;
			Cnd cnd = Cnd.where("1", "=", 1);
			if (StringUtils.isNotBlank(datemin)
					&& StringUtils.isNotBlank(datemax)) {
				cnd = cnd.and("updateTime", ">=", DateUtil.parse(datemin
						+ " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
				cnd = cnd.and("updateTime", "<=", DateUtil.parse(datemax
						+ " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
			}
			if (StringUtils.isNotBlank(keyword)) {
				cnd = cnd.and("title", "like", "%" + keyword + "%");
			}
			if (StringUtils.isNotBlank(relateIds)) {
				cnd = cnd.and("id", "in", relateIds);
			}
			totalRecord = newsDao.getCount(News.class, cnd);
			pager.setRecordCount(totalRecord);
			if (curPage <= 0 || pageSize <= 0) {
				pager.setPageNumber(1);
				pager.setPageSize(Pager.DEFAULT_PAGE_SIZE);
			} else {
				pager.setPageNumber(curPage);
				pager.setPageSize(pageSize);
			}
			resultMap.put("pager", pager);
			List<News> recordList = newsDao.queryAll(News.class, cnd, pager);
			resultMap.put("recordList", recordList);
			return resultMap;
		} catch (Exception e) {
			log.error("获取资讯列表失败!", e);
		}
		return null;
	}

	/**
	 * 获取所有的资讯
	 * 
	 * @return
	 */
	public List<News> getAllNews() {
		try {
			return newsDao.getAllNews();
		} catch (Exception e) {
			log.error("获取所有资讯出错!", e);
		}
		return null;
	}

	/**
	 * 获取所有的资讯
	 * 
	 * @return
	 */
	public Map<Integer, News> getAllNews2Map() {
		Map<Integer, News> map = new HashMap<Integer, News>();
		try {
			List<News> allNews = newsDao.getAllNews();
			for (News news : allNews) {
				map.put(news.getId(), news);
			}
			return map;
		} catch (Exception e) {
			log.error("获取所有资讯出错!", e);
		}
		return null;
	}

	/**
	 * 根据id获取资讯
	 * 
	 * @param newsId
	 * @return
	 */
	public News getNewsById(int newsId) {
		try {
			return newsDao.getById(News.class, newsId);
		} catch (Exception e) {
			log.error("获取资讯失败!id=" + newsId, e);
		}
		return null;
	}

	/**
	 * 更新资讯
	 * 
	 * @param oldId
	 * @param news
	 * @return
	 */
	public int updateNewsByOid(int oldId, News news) {
		int result = 400;
		try {
			News oldNews = getNewsById(oldId);
			if (null == oldNews) {
				log.error("资讯不存在!");
				return result;
			}
			if (StringUtils.isNotBlank(news.getTitle())) {
				oldNews.setTitle(news.getTitle());
			}
			if (StringUtils.isNotBlank(news.getContent())) {
				oldNews.setContent(news.getContent());
			}
			if (StringUtils.isNotBlank(news.getRemark())) {
				oldNews.setRemark(news.getRemark());
			}
			if (StringUtils.isNotBlank(news.getExt1())) {
				oldNews.setExt1(news.getExt1());
			}
			if (StringUtils.isNotBlank(news.getExt2())) {
				oldNews.setExt2(news.getExt2());
			}
			if (StringUtils.isNotBlank(news.getExt3())) {
				oldNews.setExt3(news.getExt3());
			}
			if (StringUtils.isNotBlank(news.getExt4())) {
				oldNews.setExt4(news.getExt4());
			}
			if (StringUtils.isNotBlank(news.getExt5())) {
				oldNews.setExt5(news.getExt5());
			}
			oldNews.setUpdateTime(new Date());
			return newsDao.updateAll(oldNews) > 0 ? 200 : 400;
		} catch (Exception e) {
			log.error("更新资讯失败!id=" + oldId, e);
		}
		return result;
	}

	/**
	 * 删除资讯
	 * 
	 * @param delId
	 * @return
	 */
	public int delNewsById(int delId) {
		int result = 400;
		try {
			News oldNews = getNewsById(delId);
			if (null == oldNews) {
				log.error("资讯不存在!");
				return result;
			}
			return newsDao.deleteById(News.class, delId) > 0 ? 200 : 400;
		} catch (Exception e) {
			log.error("删除资讯失败!id=" + delId, e);
		}
		return result;
	}

	/**
	 * 删除多个资讯
	 * 
	 * @param newsIds
	 * @return
	 */
	public int delMoreNews(String newsIds) {
		int result = 400;
		try {
			if (StringUtils.isBlank(newsIds)) {
				return result;
			}
			String[] delIds = newsIds.split(",");
			for (String delId : delIds) {
				newsDao.deleteById(News.class, Integer.parseInt(delId));
			}
			return 200;
		} catch (Exception e) {
			log.error("删除资讯失败!newsIds=" + newsIds, e);
		}
		return result;
	}

	/**
	 * 处理编辑框上传的图片
	 * 
	 * @param tf
	 * @param req
	 * @return
	 */
	public String uploadNewsImg(TempFile tf, HttpServletRequest req) {
		ImageJson ij = new ImageJson();
		if (null == tf) {
			// 没有图片
			ij.setError(1);
			ij.setMessage("没有图片!");
			return ij.toString();
		}
		File f = tf.getFile();
		if (null == getImagSuffex(f.getName())) {
			ij.setError(1);
			ij.setMessage("没有后缀名!");
			return ij.toString();
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
			if (f != null) {
				f.delete();
			}
			String httpPriffex = req.getRequestURL().substring(0,
					req.getRequestURL().lastIndexOf("/"));
			httpPriffex = httpPriffex
					.substring(0, httpPriffex.lastIndexOf("/"));
			ij.setUrl(httpPriffex + "/upload/image/" + imgFile.getName());
			return ij.toString();
		} catch (Exception e) {
			ij.setError(1);
			ij.setMessage("上传出错!");
			return ij.toString();
		}
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

}
