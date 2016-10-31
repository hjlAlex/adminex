/**
 * 
 */
package com.gzwabao.service;

import java.util.Date;
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

import com.gzwabao.dao.ApplyDao;
import com.gzwabao.entity.Apply;
import com.gzwabao.util.DateUtil;

/**
 * @author Alex
 *
 */
@IocBean
public class ApplyService {
	private static final Log log = Logs.getLog(ApplyService.class);

	@Inject(value = "applyDao")
	private ApplyDao applyDao;

	/**
	 * 新增一个申请
	 * 
	 * @param apply
	 * @return
	 */
	public int addApply(Apply apply) {
		int result = 400;
		try {
			if (StringUtils.isBlank(apply.getName())) {
				log.error("申请姓名非法!");
				return result;
			}
			if (StringUtils.isBlank(apply.getPhone())) {
				log.error("申请电话非法!");
				return result;
			}
			if (StringUtils.isBlank(apply.getAddress())) {
				log.error("申请地址非法!");
				return result;
			}
			apply.setCreateTime(new Date());
			return applyDao.save(apply) != null ? 200 : 400;
		} catch (Exception e) {
			log.error("新增申请失败!name=" + apply.getName(), e);
		}
		return result;
	}

	/**
	 * 获取前端所有申请
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param datemin
	 * @param datemax
	 * @param keyword
	 * @return
	 * @since 2015年12月17日 下午1:32:53
	 */
	public Map<String, Object> getApplyList(int curPage, int pageSize,
			String datemin, String datemax, String keyword) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		try {
			Pager pager = new Pager();
			int totalRecord = 0;
			Cnd cnd = Cnd.where("1", "=", 1);
			if (StringUtils.isNotBlank(datemin)
					&& StringUtils.isNotBlank(datemax)) {
				cnd = cnd.and("createTime", ">=", DateUtil.parse(datemin
						+ " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
				cnd = cnd.and("createTime", "<=", DateUtil.parse(datemax
						+ " 23:59:59", "yyyy-MM-dd HH:mm:ss"));
			}
			if (StringUtils.isNotBlank(keyword)) {
				cnd = cnd.and("name", "like", "%" + keyword + "%");

			}
			totalRecord = applyDao.getCount(Apply.class, cnd);
			pager.setRecordCount(totalRecord);
			if (curPage <= 0 || pageSize <= 0) {
				pager.setPageNumber(1);
				pager.setPageSize(Pager.DEFAULT_PAGE_SIZE);
			} else {
				pager.setPageNumber(curPage);
				pager.setPageSize(pageSize);
			}
			resultMap.put("pager", pager);
			List<Apply> recordList = applyDao.queryAll(Apply.class, cnd, pager);
			resultMap.put("recordList", recordList);
			return resultMap;
		} catch (Exception e) {
			log.error("获取申请列表失败!", e);
		}
		return null;
	}

	/**
	 * 根据id获取申请
	 * 
	 * @param id
	 * @return
	 */
	public Apply getApplyById(int id) {
		try {
			return applyDao.getById(Apply.class, id);
		} catch (Exception e) {
			log.error("获取申请失败!id=" + id, e);
		}
		return null;
	}

	/**
	 * 更新申请
	 * 
	 * @param oldId
	 * @param apply
	 * @return
	 */
	public int updateApplyByOid(int oldId, Apply apply) {
		int result = 400;
		try {
			Apply oldApply = getApplyById(oldId);
			if (null == oldApply) {
				log.error("申请不存在!");
				return result;
			}
			if (StringUtils.isNotBlank(apply.getName())) {
				oldApply.setName(apply.getName());
			}
			if (StringUtils.isNotBlank(apply.getPhone())) {
				oldApply.setPhone(apply.getPhone());
			}
			if (StringUtils.isNotBlank(apply.getAddress())) {
				oldApply.setAddress(apply.getAddress());
			}
			if (StringUtils.isNotBlank(apply.getExt1())) {
				oldApply.setExt1(apply.getExt1());
			}
			if (StringUtils.isNotBlank(apply.getExt2())) {
				oldApply.setExt2(apply.getExt2());
			}
			if (StringUtils.isNotBlank(apply.getExt3())) {
				oldApply.setExt3(apply.getExt3());
			}
			if (StringUtils.isNotBlank(apply.getExt4())) {
				oldApply.setExt4(apply.getExt4());
			}
			if (StringUtils.isNotBlank(apply.getExt5())) {
				oldApply.setExt5(apply.getExt5());
			}
			return applyDao.updateAll(oldApply) > 0 ? 200 : 400;
		} catch (Exception e) {
			log.error("更新申请失败!id=" + oldId, e);
		}
		return result;
	}

	/**
	 * 删除申请
	 * 
	 * @param delId
	 * @return
	 */
	public int delApplyById(int delId) {
		int result = 400;
		try {
			Apply oldApply = getApplyById(delId);
			if (null == oldApply) {
				log.error("申请不存在!");
				return result;
			}
			return applyDao.deleteById(Apply.class, delId) > 0 ? 200 : 400;
		} catch (Exception e) {
			log.error("删除申请失败!id=" + delId, e);
		}
		return result;
	}

	/**
	 * 删除多个资讯
	 * 
	 * @param newsIds
	 * @return
	 */
	public int delMoreApply(String applyIds) {
		int result = 400;
		try {
			if (StringUtils.isBlank(applyIds)) {
				return result;
			}
			String[] delIds = applyIds.split(",");
			for (String delId : delIds) {
				applyDao.deleteById(Apply.class, Integer.parseInt(delId));
			}
			return 200;
		} catch (Exception e) {
			log.error("删除申请失败!applyIds=" + applyIds, e);
		}
		return result;
	}

}
