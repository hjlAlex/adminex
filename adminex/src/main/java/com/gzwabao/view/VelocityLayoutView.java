package com.gzwabao.view;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;
import org.apache.velocity.io.VelocityWriter;
import org.apache.velocity.util.SimplePool;
import org.nutz.lang.Files;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.Mvcs;
import org.nutz.mvc.config.AtMap;
import org.nutz.mvc.view.AbstractPathView;

import com.gzwabao.util.AdminUtil;
import com.gzwabao.util.CacheUtil;
import com.gzwabao.util.DateUtil;

public class VelocityLayoutView extends AbstractPathView {

	private static final Log log = Logs.get();
	protected static final int WRITER_BUFFER_SIZE = 8 * 1024;
	protected SimplePool writerPool = new SimplePool(40);
	private final static String SUFFIX = ".html";

	private static final StringUtils stringUtils = new StringUtils();
	private static final DateUtil dateUtils = new DateUtil();

	public VelocityLayoutView(String dest) {
		super(dest);
	}

	public void render(HttpServletRequest req, HttpServletResponse resp,
			Object obj) throws Exception {
		String path = evalPath(req, obj);
		resp.setContentType("text/html;charset=\"UTF-8\"");
		resp.setCharacterEncoding("UTF-8");
		try {
			StringWriter sw = new StringWriter();
			Context context = new VelocityContext();
			// 存放公用常量或者方法，全局vm模板公用
			context.put("obj", obj);
			context.put("base", req.getContextPath());
			context.put("request", req);
			context.put("session", req.getSession());
			context.put("stringUtils", stringUtils);
			context.put("dateUtils", dateUtils);
			context.put("adminUtils",
					Mvcs.ctx().getDefaultIoc().get(AdminUtil.class));
			context.put("cacheUtil",
					Mvcs.ctx().getDefaultIoc().get(CacheUtil.class));
			// 请求的参数表,需要兼容之前的p.参数, Fix issue 418
			// 这样就可以直接可以在另一个vm B模板中获取由vm A模板请求带上的参数
			Map<String, String> p = new HashMap<String, String>();
			for (Object o : req.getParameterMap().keySet()) {
				String key = (String) o;
				String value = req.getParameter(key);
				p.put(key, value);
				context.put(key, value);// 以支持直接获取请求参数
			}
			context.put("p", p);

			Map<String, String> u = new HashMap<String, String>();
			AtMap at = Mvcs.getAtMap();
			if (at != null) {
				for (Object o : at.keys()) {
					String key = (String) o;
					u.put(key, at.get(key));
				}
				context.put("u", u);
			}
			Enumeration<?> reqs = req.getAttributeNames();
			while (reqs.hasMoreElements()) {
				String strKey = (String) reqs.nextElement();
				context.put(strKey, req.getAttribute(strKey));
			}
			Template template = Velocity.getTemplate(getPath(path));
			template.merge(context, sw);
			internalRenderTemplate(template, context, resp.getWriter());
		} catch (Exception e) {
			log.error("velocity error", e);
			throw new Exception(e);
		}
	}

	private String getPath(String path) {
		StringBuilder sb = new StringBuilder();
		sb.append(("/WEB-INF/"));
		// 空路径，采用默认规则
		if (Strings.isBlank(path)) {
			sb.append(Mvcs.getServletContext().getRealPath("WEB-INF"));
			sb.append((path.startsWith("/") ? "" : "/"));
			sb.append(Files.renameSuffix(path, SUFFIX));
		}
		// 绝对路径 : 以 '/' 开头的路径不增加 '/WEB-INF'
		else if (path.charAt(0) == '/') {
			String ext = SUFFIX;
			sb.append(path);
			if (!path.toLowerCase().endsWith(ext))
				sb.append(ext);
		}
		// 包名形式的路径
		else {
			sb.append(path.replace('.', '/'));
			sb.append(SUFFIX);
		}
		return sb.toString();
	}

	private void internalRenderTemplate(Template template, Context context,
			Writer writer) throws Exception {
		VelocityWriter velocityWriter = null;
		try {
			velocityWriter = (VelocityWriter) writerPool.get();
			if (velocityWriter == null) {
				velocityWriter = new VelocityWriter(writer, WRITER_BUFFER_SIZE,
						true);
			} else {
				velocityWriter.recycle(writer);
			}
			template.merge(context, velocityWriter);
		} catch (Exception pee) {
			throw new Exception(pee);
		} finally {
			if (velocityWriter != null) {
				velocityWriter.flush();
				velocityWriter.recycle(null);
				writerPool.put(velocityWriter);
			}
			writer.flush();
			writer.close();
		}
	}
}
