package com.gzwabao;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.velocity.app.Velocity;
import org.nutz.dao.Dao;
import org.nutz.dao.util.Daos;
import org.nutz.ioc.Ioc;
import org.nutz.lang.Tasks;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.NutConfig;
import org.nutz.mvc.Setup;

import com.gzwabao.entity.User;
import com.gzwabao.util.MD5Utils;

public class MainSetup implements Setup {
	private static final Log log = Logs.getLog(MainSetup.class);

	public void init(NutConfig conf) {
		log.info("系统启动中...");
		try {
			Ioc ioc = conf.getIoc();
			for (int i = 0; i < ioc.getNames().length; i++) {
				System.out.println(ioc.getNames()[i]);
			}
			Dao dao = ioc.get(Dao.class, "dao");
			Daos.createTablesInPackage(dao, "com.gzwabao.entity", false);

			// 初始化默认根用户
			if (dao.count(User.class) == 0) {
				User user = new User();
				user.setName("admin");
				user.setPassword(MD5Utils.MD5Encode("123456"));
				user.setCreateTime(new Date());
				user.setUpdateTime(new Date());
				dao.insert(user);
			}
			// Velocity环境初始化
			velocityInit(conf);
			// 日常事务执行
			Tasks.scheduleAtFixedRate(new Runnable() {
				public void run() {
					// 可以写定点要干的事情
				}
			}, "2015-12-05 12:41:00", 35, TimeUnit.SECONDS);
		} catch (Exception e) {
			log.error("系统启动出错!", e);
		}
		log.info("系统启动完毕!");
	}

	public void destroy(NutConfig conf) {

	}

	/**
	 * 初始化Velocity
	 *
	 * @param config
	 * @throws IOException
	 */
	private void velocityInit(NutConfig config) throws IOException {
		log.info("Veloctiy Init Start...");
		Properties p = new Properties();
		p.setProperty("resource.loader", "file,classloader");
		p.setProperty("file.resource.loader.path", config.getAppRoot());
		p.setProperty("file",
				"org.apache.velocity.tools.view.WebappResourceLoader");
		p.setProperty("classloader.resource.loader.class",
				"com.gzwabao.view.VelocityResourceLoader");
		p.setProperty("classloader.resource.loader.path", config.getAppRoot());
		p.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
		p.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
		p.setProperty("velocimacro.library.autoreload", "false");
		p.setProperty("classloader.resource.loader.root", config.getAppRoot());
		p.setProperty("velocimarco.library.autoreload", "true");
		p.setProperty("runtime.log.error.stacktrace", "false");
		p.setProperty("runtime.log.warn.stacktrace", "false");
		p.setProperty("runtime.log.info.stacktrace", "false");
		p.setProperty("runtime.log.logsystem.class",
				"org.apache.velocity.runtime.log.SimpleLog4JLogSystem");
		p.setProperty("runtime.log.logsystem.log4j.category", "velocity_log");
		Velocity.init(p);
		log.info("Veloctiy Init End.");
	}

}
