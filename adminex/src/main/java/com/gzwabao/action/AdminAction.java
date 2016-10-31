package com.gzwabao.action;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.AdaptBy;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.By;
import org.nutz.mvc.annotation.Filters;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.annotation.Param;
import org.nutz.mvc.upload.TempFile;
import org.nutz.mvc.upload.UploadAdaptor;

import com.gzwabao.entity.Apply;
import com.gzwabao.entity.Module;
import com.gzwabao.entity.Navigation;
import com.gzwabao.entity.News;
import com.gzwabao.entity.Page;
import com.gzwabao.entity.Picture;
import com.gzwabao.entity.User;
import com.gzwabao.filter.LoginFilter;
import com.gzwabao.service.ApplyService;
import com.gzwabao.service.ModuleService;
import com.gzwabao.service.NavService;
import com.gzwabao.service.NewsService;
import com.gzwabao.service.PageService;
import com.gzwabao.service.PictureService;
import com.gzwabao.service.UserService;
import com.gzwabao.util.CodeUtil;

/**
 * 后台管理Action
 * 
 * @since 2015年11月5日 下午5:45:16
 * @author Alex
 */
@IocBean
@At({ "/admin" })
@Filters(@By(type = LoginFilter.class))
public class AdminAction {
	@Inject(value = "userService")
	private UserService userService;

	@Inject(value = "pageService")
	private PageService pageService;

	@Inject(value = "pictureService")
	private PictureService pictureService;

	@Inject(value = "newsService")
	private NewsService newsService;

	@Inject(value = "navService")
	private NavService navService;

	@Inject(value = "moduleService")
	private ModuleService moduleService;

	@Inject(value = "applyService")
	private ApplyService applyService;

	/**
	 * 管理首页
	 */
	@At("/index")
	@Ok("vm:template.admin.index")
	public void adminMain() {

	}

	@Filters
	@At("/appindex")
	@Ok("vm:template.admin.appindex")
	public void appIndex() {

	}

	// =============用户业务相关开始=============
	/**
	 * 获取用户列表
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param keyword
	 * @return
	 * @since 2015年12月11日 下午5:52:12
	 */
	@At("/user_list")
	@Ok("vm:template.admin.user_list")
	public Map<String, Object> getUserList(@Param("curPage") int curPage,
			@Param("pageSize") int pageSize, @Param("keyword") String keyword) {
		return userService.getUserList(curPage, pageSize, keyword);
	}

	/**
	 * 跳往添加用户页面
	 * 
	 * @since 2015年12月11日 下午5:52:22
	 */
	@At("/add_user")
	@Ok("vm:template.admin.add_user")
	public void toCreateAdmin() {
		// 仅仅为了返回一个页面，这个时候也可以给该页面初始化点东西
	}

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 * @since 2015年12月11日 下午5:52:47
	 */
	@At("/addAdmin")
	@Ok("raw:html")
	public int addAdmin(@Param("..") User user) {
		int status = userService.addUser(user);
		return status;
	}

	/**
	 * 跳往更新页面
	 * 
	 * @param uid
	 * @return
	 */
	@At("/update_user")
	@Ok("vm:template.admin.update_user")
	public User toUpdateAdmin(@Param("uid") int uid) {
		// 仅仅为了返回一个页面，这个时候也可以给该页面初始化点东西
		return userService.getUserById(uid);
	}

	/**
	 * 更新用户
	 * 
	 * @param oldId
	 * @param user
	 * @return
	 */
	@At("/updateAdmin")
	@Ok("raw:html")
	public int updateAdmin(@Param("oldId") int oldId, @Param("..") User user) {
		int status = userService.updateUserByOid(oldId, user);
		return status;
	}

	/**
	 * 删除单个用户
	 * 
	 * @param oldId
	 * @param user
	 * @return
	 */
	@At("/deleteAdmin")
	@Ok("raw:html")
	public int deleteAdmin(@Param("delId") int delId) {
		int status = userService.delAdminById(delId);
		return status;
	}

	/**
	 * 批量删除用户
	 * 
	 * @param adminIds
	 * @return
	 * @since 2015年12月12日 下午12:49:45
	 */
	@At("/deleteMoreAdmin")
	@Ok("raw:html")
	public int deleteMoreAdmin(@Param("adminIds") String adminIds) {
		int status = userService.delMoreAdmin(adminIds);
		return status;
	}

	/**
	 * 获取验证码
	 * 
	 * @param req
	 * @return
	 * @since 2015年12月11日 下午5:52:59
	 */
	@At("/code")
	@Ok("raw:jpg")
	public BufferedImage getValidateCode(HttpServletRequest req) {
		String randomString = CodeUtil.getRandomString();
		// 将getSession（）设置为true，当会话不存在是返回null
		HttpSession session = req.getSession(true);
		session.setMaxInactiveInterval(180 * 1000);
		String sid = session.getId();
		session.setAttribute(sid, randomString);
		// 创建一个彩色图片
		BufferedImage bimage = CodeUtil.getCodeImg(randomString);
		return bimage;
	}

	/**
	 * 登录
	 * 
	 * @param name
	 * @param password
	 * @param code
	 * @param req
	 * @return
	 */
	@At("/login")
	@Ok("redirect:/admin/index.html?tip=${obj}")
	public String login(@Param("name") String name,
			@Param("password") String password, @Param("code") String code,
			HttpServletRequest req) {
		return userService.login(name, password, code, req);
	}

	/**
	 * 登出
	 * 
	 * @param req
	 */
	@At("/logout")
	@Ok("vm:template.admin.login")
	public void logout(HttpServletRequest req) {
		userService.logout(req);
	}

	// =============用户业务相关结束=============

	// =============页面业务相关开始=============
	@At("/add_page")
	@Ok("vm:template.admin.add_page")
	public void toCreatePage() {
		// 仅仅为了返回一个页面，这个时候也可以给该页面初始化点东西
	}

	/**
	 * 添加用户
	 * 
	 * @param user
	 * @return
	 * @since 2015年12月11日 下午5:52:47
	 */
	@At("/addPage")
	@Ok("raw:html")
	public int addPage(@Param("..") Page page) {
		int status = pageService.addPage(page);
		return status;
	}

	/**
	 * 获取前端所有页面
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param keyword
	 * @return
	 */
	@At("/page_list")
	@Ok("vm:template.admin.page_list")
	public Map<String, Object> getPageList(@Param("curPage") int curPage,
			@Param("pageSize") int pageSize, @Param("keyword") String keyword) {
		return pageService.getPageList(curPage, pageSize, keyword);
	}

	/**
	 * 跳往更新页面
	 * 
	 * @param uid
	 * @return
	 */
	@At("/update_page")
	@Ok("vm:template.admin.update_page")
	public Page toUpdatePage(@Param("pid") int pid) {
		// 仅仅为了返回一个页面，这个时候也可以给该页面初始化点东西
		return pageService.getPageById(pid);
	}

	/**
	 * 更新页面
	 * 
	 * @param oldId
	 * @param page
	 * @return
	 */
	@At("/updatePage")
	@Ok("raw:html")
	public int updatePage(@Param("oldId") int oldId, @Param("..") Page page) {
		int status = pageService.updatePageByOid(oldId, page);
		return status;
	}

	/**
	 * 删除页面
	 * 
	 * @param delId
	 * @return
	 */
	@At("/deletePage")
	@Ok("raw:html")
	public int deletePage(@Param("delId") int delId) {
		int status = pageService.delPageById(delId);
		return status;
	}

	/**
	 * 删除多个页面
	 * 
	 * @param pageIds
	 * @return
	 */
	@At("/deleteMorePage")
	@Ok("raw:html")
	public int deleteMorePage(@Param("pageIds") String pageIds) {
		int status = pageService.delMorePage(pageIds);
		return status;
	}

	// =============页面业务相关结束=============

	// =============图片业务相关开始=============
	@At("/picture_list")
	@Ok("vm:template.admin.picture_list")
	public Map<String, Object> getPictureList(@Param("curPage") int curPage,
			@Param("pageSize") int pageSize, @Param("keyword") String keyword,
			@Param("relateIds") String relateIds) {
		return pictureService.getPictureList(curPage, pageSize, keyword,
				relateIds);
	}

	@At("/add_picture")
	@Ok("vm:template.admin.add_picture")
	public void toAddPicture() {
		// 仅仅为了返回一个页面，这个时候也可以给该页面初始化点东西
	}

	/**
	 * 添加图片
	 * 
	 * @param tf
	 * @param picture
	 * @param req
	 * @return
	 */
	@At("/addPicture")
	@Ok("vm:template.admin.add_picture")
	@AdaptBy(type = UploadAdaptor.class, args = { "${app.root}/WEB-INF/tmp" })
	public String addPicture(@Param("image") TempFile tf,
			@Param("..") Picture picture, HttpServletRequest req) {
		return pictureService.addPicture(tf, picture, req);
	}

	/**
	 * 根据id删除图片
	 * 
	 * @param delId
	 * @return
	 * @since 2015年12月15日 下午12:36:37
	 */
	@At("/deletePicture")
	@Ok("raw:html")
	public int deletePicture(@Param("delId") int delId) {
		int status = pictureService.delPictureById(delId);
		return status;
	}

	/**
	 * 删除多个图片
	 * 
	 * @param pIds
	 * @return
	 */
	@At("/deleteMorePicture")
	@Ok("raw:html")
	public int deleteMorePicture(@Param("pIds") String pIds) {
		int status = pictureService.delMorePicture(pIds);
		return status;
	}

	/**
	 * 跳往图片编辑页面
	 * 
	 * @param oldId
	 * @return
	 */
	@At("/update_picture")
	@Ok("vm:template.admin.update_picture")
	public Picture toUpdatePicture(@Param("oldId") int oldId) {
		return pictureService.getPictureById(oldId);
	}

	/**
	 * 更新图片
	 * 
	 * @param tf
	 * @param picture
	 * @param req
	 * @return
	 */
	@At("/updatePicture")
	@Ok("vm:template.admin.update_picture")
	@AdaptBy(type = UploadAdaptor.class, args = { "${app.root}/WEB-INF/tmp" })
	public String updatePicture(@Param("image") TempFile tf,
			@Param("..") Picture picture, HttpServletRequest req) {
		return pictureService.updatePicture(tf, picture, req);
	}

	// ===============图片业务结束
	// ===============资讯业务开始
	@At("/add_news")
	@Ok("vm:template.admin.add_news")
	public void toAddNews() {
	}

	@At("/addNews")
	@Ok("vm:template.admin.add_news")
	public String addNews(@Param("..") News news) {
		return newsService.addNews(news) + "";
	}

	@At("/upLoadNewsImgJson")
	@Ok("raw:html")
	@AdaptBy(type = UploadAdaptor.class, args = { "${app.root}/WEB-INF/tmp" })
	public String upLoadNewsImgJson(@Param("imgFile") TempFile tf,
			HttpServletRequest req) {
		return newsService.uploadNewsImg(tf, req);
	}

	@At("/news_list")
	@Ok("vm:template.admin.news_list")
	public Map<String, Object> getNewsList(@Param("curPage") int curPage,
			@Param("pageSize") int pageSize, @Param("datemin") String datemin,
			@Param("datemax") String datemax, @Param("keyword") String keyword,
			@Param("relateIds") String relateIds) {
		return newsService.getNewsList(curPage, pageSize, datemin, datemax,
				keyword, relateIds);
	}

	@At("/update_news")
	@Ok("vm:template.admin.update_news")
	public News toUpdateNews(@Param("newsId") int newsId) {
		return newsService.getNewsById(newsId);
	}

	@At("/updateNews")
	@Ok("vm:template.admin.update_news")
	public int updateNews(@Param("..") News news, @Param("oldId") int oldId) {
		return newsService.updateNewsByOid(oldId, news);
	}

	@At("/deleteNews")
	@Ok("raw:html")
	public int deleteNews(@Param("delId") int delId) {
		return newsService.delNewsById(delId);
	}

	@At("/deleteMoreNews")
	@Ok("raw:html")
	public int deleteMoreNews(@Param("newsIds") String newsIds) {
		return newsService.delMoreNews(newsIds);
	}

	// ===============资讯业务结束
	// ===============导航业务开始
	/**
	 * 获取导航列表
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param keyword
	 * @return
	 */
	@At("/nav_list")
	@Ok("vm:template.admin.nav_list")
	public Map<String, Object> getNavList(@Param("curPage") int curPage,
			@Param("pageSize") int pageSize, @Param("keyword") String keyword,
			@Param("relateIds") String relateIds) {
		return navService.getNavList(curPage, pageSize, keyword, relateIds);
	}

	/**
	 * 跳往添加导航页面
	 */
	@At("/add_nav")
	@Ok("vm:template.admin.add_nav")
	public void toAddNav() {

	}

	/**
	 * 添加导航
	 * 
	 * @param nav
	 * @return
	 */
	@At("/addNav")
	@Ok("vm:template.admin.add_nav")
	public int addNav(@Param("..") Navigation nav) {
		return navService.addNav(nav);
	}

	/**
	 * 跳往更新导航页面
	 */
	@At("/update_nav")
	@Ok("vm:template.admin.update_nav")
	public Navigation toUpdateNav(int nid) {
		return navService.getNavById(nid);
	}

	/**
	 * 更新导航
	 * 
	 * @param nav
	 * @param oldId
	 * @return
	 */
	@At("/updateNav")
	@Ok("vm:template.admin.update_nav")
	public int updateNav(@Param("..") Navigation nav, @Param("oldId") int oldId) {
		return navService.updateNavByOid(oldId, nav);
	}

	/**
	 * 删除导航
	 * 
	 * @param delId
	 * @return
	 */
	@At("/deleteNav")
	@Ok("raw:html")
	public int deleteNav(@Param("delId") int delId) {
		return navService.delNavById(delId);
	}

	/**
	 * 删除导航
	 * 
	 * @param navIds
	 * @return
	 */
	@At("/deleteMoreNav")
	@Ok("raw:html")
	public int deleteMoreNav(@Param("navIds") String navIds) {
		return navService.delMoreNav(navIds);
	}

	// ===============导航业务结束
	// ===============模块业务开始
	/**
	 * 页面模块管理
	 * 
	 * @param pid
	 * @return
	 */
	@At("/module_manage")
	@Ok("vm:template.admin.module_manage")
	public Map<String, Object> moduleManage(@Param("pid") int pid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("moduleList", moduleService.getModuleList(pid));
		map.put("page", pageService.getPageById(pid));
		return map;
	}

	/**
	 * 添加模块页面
	 * 
	 * @param pid
	 * @return
	 */
	@At("/add_module")
	@Ok("vm:template.admin.add_module")
	public Map<String, Object> toAddModule(@Param("pid") int pid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("pictureList", pictureService.getAllPicture());
		map.put("newsList", newsService.getAllNews());
		map.put("navList", navService.getAllNav());
		return map;
	}

	/**
	 * 添加模块
	 * 
	 * @param module
	 * @param req
	 * @return
	 */
	@At("/addModule")
	@Ok("vm:template.admin.add_module")
	public int addModule(@Param("..") Module module, HttpServletRequest req) {
		return moduleService.addModule(module, req);
	}

	/**
	 * 更新模块页面
	 * 
	 * @param mid
	 * @return
	 */
	@At("/update_module")
	@Ok("vm:template.admin.update_module")
	public Map<String, Object> toUpdateModule(@Param("mid") int mid) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("module", moduleService.getModuleById(mid));
		map.put("pictureList", pictureService.getAllPicture());
		map.put("newsList", newsService.getAllNews());
		map.put("navList", navService.getAllNav());
		return map;
	}

	/**
	 * 更新模块
	 * 
	 * @param module
	 * @param req
	 * @return
	 */
	@At("/updateModule")
	@Ok("vm:template.admin.update_module")
	public int updateModule(@Param("oldId") int oldId,
			@Param("..") Module module, HttpServletRequest req) {
		return moduleService.updateModuleByOid(oldId, module, req);
	}

	/**
	 * 删除模块
	 * 
	 * @param delId
	 * @return
	 */
	@At("/deleteModule")
	@Ok("raw:html")
	public int deleteModule(@Param("delId") int delId) {
		return moduleService.delModuleById(delId);
	}

	/**
	 * 删除模块
	 * 
	 * @param mIds
	 * @return
	 */
	@At("/deleteMoreModule")
	@Ok("raw:html")
	public int deleteMoreModule(@Param("mIds") String mIds) {
		return moduleService.delMoreModule(mIds);
	}

	// ===============模块业务结束
	// ===============申请业务开始
	@At("/apply_list")
	@Ok("vm:template.admin.apply_list")
	public Map<String, Object> getApplyList(@Param("curPage") int curPage,
			@Param("pageSize") int pageSize, @Param("datemin") String datemin,
			@Param("datemax") String datemax, @Param("keyword") String keyword) {
		return applyService.getApplyList(curPage, pageSize, datemin, datemax,
				keyword);
	}

	@At("/update_apply")
	@Ok("vm:template.admin.update_apply")
	public Apply toUpdateApply(@Param("applyId") int applyId) {
		return applyService.getApplyById(applyId);
	}

	@At("/updateApply")
	@Ok("vm:template.admin.update_apply")
	public int updateApply(@Param("..") Apply apply, @Param("oldId") int oldId) {
		return applyService.updateApplyByOid(oldId, apply);
	}

	/**
	 * 删除加盟申请
	 * 
	 * @param delId
	 * @return
	 */
	@At("/deleteApply")
	@Ok("raw:html")
	public int deleteApply(@Param("delId") int delId) {
		return applyService.delApplyById(delId);
	}

	/**
	 * 删除加盟申请
	 * 
	 * @param aIds
	 * @return
	 */
	@At("/deleteMoreApply")
	@Ok("raw:html")
	public int deleteMoreApply(@Param("applyIds") String applyIds) {
		return applyService.delMoreApply(applyIds);
	}
	// ===============申请业务结束
}
