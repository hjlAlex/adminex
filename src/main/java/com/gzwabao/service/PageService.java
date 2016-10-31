/**
 * 
 */
package com.gzwabao.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.gzwabao.dao.PageDao;
import com.gzwabao.entity.Page;

/**
 * @author Alex
 *
 */
@IocBean
public class PageService {
	private static final Log log = Logs.getLog(PageService.class);

	@Inject(value = "pageDao")
	private PageDao pageDao;

	/**
	 * 新增一个页面
	 * 
	 * @param page
	 * @return
	 */
	public int addPage(Page page) {
		int result = 400;
		try {
			if (StringUtils.isBlank(page.getStrId())) {
				log.error("页面strId非法!");
				return result;
			}
			if (StringUtils.isBlank(page.getLocaUrl())) {
				log.error("页面链接locaUrl非法!");
				return result;
			}

			return pageDao.save(page) != null ? 200 : 400;
		} catch (Exception e) {
			log.error("新增页面失败!name=" + page.getId(), e);
		}
		return result;
	}

	/**
	 * 获取前端所有页面
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param keyword
	 * @return
	 */
	public Map<String, Object> getPageList(int curPage, int pageSize,
			String keyword) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Pager pager = new Pager();
			int totalRecord = 0;
			Condition cnd = null;
			if (StringUtils.isBlank(keyword)) {
				totalRecord = pageDao.getCount(Page.class, null);

			} else {
				cnd = Cnd.where("strId", "like", "%" + keyword + "%").or(
						"locaUrl", "like", "%" + keyword + "%");
				totalRecord = pageDao.getCount(Page.class, cnd);
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
			List<Page> recordList = pageDao.queryAll(Page.class, cnd, pager);
			resultMap.put("recordList", recordList);
			return resultMap;
		} catch (Exception e) {
			log.error("获取页面列表失败!", e);
		}
		return null;
	}

	/**
	 * 根据id获取页面
	 * 
	 * @param pid
	 * @return
	 */
	public Page getPageById(int pid) {
		try {
			return pageDao.getById(Page.class, pid);
		} catch (Exception e) {
			log.error("获取页面失败!id=" + pid, e);
		}
		return null;
	}

	/**
	 * 根据strId获取页面
	 * 
	 * @param sid
	 * @return
	 */
	public Page getPageByStrId(String sid) {
		try {
			return pageDao.getByStrId(sid);
		} catch (Exception e) {
			log.error("获取页面失败!sid=" + sid, e);
		}
		return null;
	}

	/**
	 * 更新页面
	 * 
	 * @param oldId
	 * @param page
	 * @return
	 */
	public int updatePageByOid(int oldId, Page page) {
		int result = 400;
		try {
			Page oldPage = getPageById(oldId);
			if (null == oldPage) {
				log.error("页面不存在!");
				return result;
			}
			if (StringUtils.isNotBlank(page.getStrId())) {
				oldPage.setStrId(page.getStrId());
			}
			if (StringUtils.isNotBlank(page.getLocaUrl())) {
				oldPage.setLocaUrl(page.getLocaUrl());
			}
			if (StringUtils.isNotBlank(page.getRemark())) {
				oldPage.setRemark(page.getRemark());
			}
			return pageDao.updateAll(oldPage) > 0 ? 200 : 400;
		} catch (Exception e) {
			log.error("更新页面失败!strId=" + page.getStrId(), e);
		}
		return result;
	}

	/**
	 * 删除页面
	 * 
	 * @param delId
	 * @return
	 */
	public int delPageById(int delId) {
		int result = 400;
		try {
			Page oldPage = getPageById(delId);
			if (null == oldPage) {
				log.error("页面不存在!");
				return result;
			}
			return pageDao.deleteById(Page.class, delId) > 0 ? 200 : 400;
		} catch (Exception e) {
			log.error("删除页面失败!id=" + delId, e);
		}
		return result;
	}

	/**
	 * 删除多个页面
	 * 
	 * @param pageIds
	 * @return
	 */
	public int delMorePage(String pageIds) {
		int result = 400;
		try {
			if (StringUtils.isBlank(pageIds)) {
				return result;
			}
			String[] delIds = pageIds.split(",");
			for (String delId : delIds) {
				pageDao.deleteById(Page.class, Integer.parseInt(delId));
			}
			return 200;
		} catch (Exception e) {
			log.error("删除页面失败!pageIds=" + pageIds, e);
		}
		return result;
	}

	/**
	 * 获取所有页面
	 * 
	 * @return
	 */
	public List<Page> getAllPages() {
		return pageDao.queryAll(Page.class);
	}
}
