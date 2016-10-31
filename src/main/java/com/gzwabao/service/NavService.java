/**
 * 
 */
package com.gzwabao.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.nutz.dao.Cnd;
import org.nutz.dao.pager.Pager;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.gzwabao.dao.NavDao;
import com.gzwabao.entity.Navigation;

/**
 * @author Alex
 *
 */
@IocBean
public class NavService {
	private static final Log log = Logs.getLog(NavService.class);

	@Inject(value = "navDao")
	private NavDao navDao;

	/**
	 * 新增一个导航
	 * 
	 * @param nav
	 * @return
	 */
	public int addNav(Navigation nav) {
		int result = 400;
		try {
			if (StringUtils.isBlank(nav.getName())) {
				log.error("导航名字非法!");
				return result;
			}
			if (StringUtils.isBlank(nav.getLinkUrl())) {
				log.error("导航链接非法!");
				return result;
			}

			return navDao.save(nav) != null ? 200 : 400;
		} catch (Exception e) {
			log.error("新增导航失败!name=" + nav.getName(), e);
		}
		return result;
	}

	/**
	 * 获取前端所有导航
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param keyword
	 * @return
	 */
	public Map<String, Object> getNavList(int curPage, int pageSize,
			String keyword, String relateIds) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Pager pager = new Pager();
			int totalRecord = 0;
			Cnd cnd = Cnd.where("1", "=", 1);
			if (StringUtils.isNotBlank(keyword)) {
				cnd = cnd.and("name", "like", "%" + keyword + "%").or(
						"linkUrl", "like", "%" + keyword + "%");
			}
			if (StringUtils.isNotBlank(relateIds)) {
				cnd = cnd.and("id", "in", relateIds);
			}
			totalRecord = navDao.getCount(Navigation.class, cnd);
			pager.setRecordCount(totalRecord);
			if (curPage <= 0 || pageSize <= 0) {
				pager.setPageNumber(1);
				pager.setPageSize(Pager.DEFAULT_PAGE_SIZE);
			} else {
				pager.setPageNumber(curPage);
				pager.setPageSize(pageSize);
			}
			resultMap.put("pager", pager);
			List<Navigation> recordList = navDao.queryAll(Navigation.class,
					cnd, pager);
			resultMap.put("recordList", recordList);
			return resultMap;
		} catch (Exception e) {
			log.error("获取导航列表失败!", e);
		}
		return null;
	}

	/**
	 * 获取所有导航
	 * 
	 * @return
	 */
	public List<Navigation> getAllNav() {
		try {
			return navDao.getAllNav();
		} catch (Exception e) {
			log.error("获取导航列表出错!", e);
		}
		return null;
	}

	/**
	 * 获取所有导航
	 * 
	 * @return
	 */
	public Map<Integer, Navigation> getAllNav2Map() {
		Map<Integer, Navigation> map = new HashMap<Integer, Navigation>();
		try {
			List<Navigation> allNavs = navDao.getAllNav();
			for (Navigation navigation : allNavs) {
				map.put(navigation.getId(), navigation);
			}
			return map;
		} catch (Exception e) {
			log.error("获取导航列表出错!", e);
		}
		return null;
	}

	/**
	 * 根据id获取导航
	 * 
	 * @param nid
	 * @return
	 */
	public Navigation getNavById(int nid) {
		try {
			return navDao.getById(Navigation.class, nid);
		} catch (Exception e) {
			log.error("获取导航失败!id=" + nid, e);
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
	public int updateNavByOid(int oldId, Navigation nav) {
		int result = 400;
		try {
			Navigation oldNav = getNavById(oldId);
			if (null == oldNav) {
				log.error("导航不存在!");
				return result;
			}
			if (StringUtils.isNotBlank(nav.getName())) {
				oldNav.setName(nav.getName());
			}
			if (StringUtils.isNotBlank(nav.getLinkUrl())) {
				oldNav.setLinkUrl(nav.getLinkUrl());
			}
			if (StringUtils.isNotBlank(nav.getRemark())) {
				oldNav.setRemark(nav.getRemark());
			}
			if (StringUtils.isNotBlank(nav.getExt1())) {
				oldNav.setExt1(nav.getExt1());
			}
			if (StringUtils.isNotBlank(nav.getExt2())) {
				oldNav.setExt2(nav.getExt2());
			}
			if (StringUtils.isNotBlank(nav.getExt3())) {
				oldNav.setExt3(nav.getExt3());
			}
			if (StringUtils.isNotBlank(nav.getExt4())) {
				oldNav.setExt4(nav.getExt4());
			}
			if (StringUtils.isNotBlank(nav.getExt5())) {
				oldNav.setExt5(nav.getExt5());
			}
			return navDao.updateAll(oldNav) > 0 ? 200 : 400;
		} catch (Exception e) {
			log.error("更新导航失败!id=" + oldId, e);
		}
		return result;
	}

	/**
	 * 删除导航
	 * 
	 * @param delId
	 * @return
	 */
	public int delNavById(int delId) {
		int result = 400;
		try {
			Navigation oldNav = getNavById(delId);
			if (null == oldNav) {
				log.error("导航不存在!");
				return result;
			}
			return navDao.deleteById(Navigation.class, delId) > 0 ? 200 : 400;
		} catch (Exception e) {
			log.error("删除导航失败!id=" + delId, e);
		}
		return result;
	}

	/**
	 * 删除多个导航
	 * 
	 * @param pageIds
	 * @return
	 */
	public int delMoreNav(String navIds) {
		int result = 400;
		try {
			if (StringUtils.isBlank(navIds)) {
				return result;
			}
			String[] delIds = navIds.split(",");
			for (String delId : delIds) {
				navDao.deleteById(Navigation.class, Integer.parseInt(delId));
			}
			return 200;
		} catch (Exception e) {
			log.error("删除导航失败!navIds=" + navIds, e);
		}
		return result;
	}

}
