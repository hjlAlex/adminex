/** 广州哇宝信息技术有限公司 */
package com.gzwabao;

import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.SetupBy;
import org.nutz.mvc.annotation.Views;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

import com.gzwabao.view.VelocityViewMaker;

@Views(value = { VelocityViewMaker.class })
@SetupBy(value = MainSetup.class)
@IocBy(type = ComboIocProvider.class, args = { "*js", "dao.js", "*anno",
		"com.gzwabao", "*tx" })
@Modules(scanPackage = true)
public class MainModule {
}
