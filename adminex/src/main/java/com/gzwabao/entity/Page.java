package com.gzwabao.entity;

import java.io.Serializable;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

/**
 * 前端页面
 * 
 * @since 2015年11月5日 上午11:10:12
 * @author Alex
 */
@Table(value = "page")
public class Page implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	// id主键,自增
	private int id;

	@Name(casesensitive = true)
	// 字符型主键,大小写敏感
	@Column(value = "str_id")
	@ColDefine(type = ColType.VARCHAR, width = 50, notNull = true)
	private String strId;

	@Column(value = "loca_url")
	@ColDefine(type = ColType.VARCHAR, width = 50, notNull = true)
	private String locaUrl;

	@Column(value = "remark")
	@ColDefine(type = ColType.VARCHAR, width = 50, notNull = false)
	private String remark;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStrId() {
		return strId;
	}

	public void setStrId(String strId) {
		this.strId = strId;
	}

	public String getLocaUrl() {
		return locaUrl;
	}

	public void setLocaUrl(String locaUrl) {
		this.locaUrl = locaUrl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
