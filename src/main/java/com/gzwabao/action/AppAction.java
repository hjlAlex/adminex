package com.gzwabao.action;

import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;

import com.gzwabao.entity.Apply;
import com.gzwabao.entity.Module;
import com.gzwabao.entity.Page;
import com.gzwabao.service.ApplyService;
import com.gzwabao.service.ModuleService;
import com.gzwabao.service.PageService;

/**
 * 手机客户端页面控制层
 * 
 * @since 2015年11月5日 下午5:45:16
 * @author Alex
 */
@IocBean
@At({ "/" })
public class AppAction {

	@Inject(value = "pageService")
	private PageService pageService;

	@Inject(value = "moduleService")
	private ModuleService moduleService;

	@Inject(value = "applyService")
	private ApplyService applyService;

	/**
	 * 首页
	 * 
	 * @return
	 */
	@At("")
	@Ok("vm:template.app.appindex")
	public Map<String, Module> index() {
		/*
		 * Page index = pageService.getPageByStrId("index"); if (null != index)
		 * { return moduleService.getModuleMap(index.getId()); }
		 */
		return null;
	}

	@At("/new_product")
	@Ok("vm:template.app.new_product")
	public Map<String, Module> newProduct() {
		/*
		 * Page index = pageService.getPageByStrId("index"); if (null != index)
		 * { return moduleService.getModuleMap(index.getId()); }
		 */
		return null;
	}

	@At("/detail")
	@Ok("vm:template.app.detail")
	public Map<String, Module> detail() {
		/*
		 * Page index = pageService.getPageByStrId("index"); if (null != index)
		 * { return moduleService.getModuleMap(index.getId()); }
		 */
		return null;
	}

	/**
	 * 关于
	 * 
	 * @return
	 */
	@At("/about")
	@Ok("vm:template.about")
	public Map<String, Module> about() {
		Page about = pageService.getPageByStrId("about");
		if (null != about) {
			return moduleService.getModuleMap(about.getId());
		}
		return null;
	}

	/**
	 * 加盟介绍
	 * 
	 * @return
	 */
	@At("/join")
	@Ok("vm:template.join")
	public Map<String, Module> join() {
		Page join = pageService.getPageByStrId("join");
		if (null != join) {
			return moduleService.getModuleMap(join.getId());
		}
		return null;
	}

	/**
	 * 奶茶饮品
	 * 
	 * @return
	 */
	@At("/tea")
	@Ok("vm:template.tea")
	public Map<String, Module> tea() {
		Page tea = pageService.getPageByStrId("tea");
		if (null != tea) {
			return moduleService.getModuleMap(tea.getId());
		}
		return null;
	}

	/**
	 * 新闻中心
	 * 
	 * @return
	 */
	@At("/news")
	@Ok("vm:template.news")
	public Map<String, Module> news() {
		Page news = pageService.getPageByStrId("news");
		if (null != news) {
			return moduleService.getModuleMap(news.getId());
		}
		return null;
	}

	/**
	 * 申请加盟页面
	 * 
	 * @return
	 */
	@At("/join_apply")
	@Ok("vm:template.join_apply")
	public Map<String, Module> joinApply() {

		return null;
	}

	@At("/applyJoin")
	@Ok("vm:template.join_apply")
	public int applyJoin(@Param("..") Apply apply) {
		return applyService.addApply(apply);
	}
}
