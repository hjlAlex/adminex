package com.gzwabao.entity;

import java.io.Serializable;
import java.util.Date;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

/**
 * 
 * @since 2015年11月5日 上午11:10:12
 * @author Alex
 */
@Table(value = "user")
public class User implements Serializable {
	public static final String SESSION_USER = "session_user";
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String USER_CACHE = "USER_CACHE";
	@Id(auto = true)
	// id主键,自增
	private int id;

	@Name(casesensitive = true)
	// 字符型主键,大小写敏感
	@Column(value = "name")
	@ColDefine(type = ColType.VARCHAR, width = 50, notNull = true)
	private String name;

	@Column(value = "password")
	@ColDefine(type = ColType.VARCHAR, width = 50, notNull = true)
	private String password;

	@Column(value = "nick_name")
	@ColDefine(type = ColType.VARCHAR, width = 50, notNull = false)
	private String nickName;

	@Column(value = "create_time")
	@ColDefine(type = ColType.DATETIME, notNull = true)
	private Date createTime;

	@Column(value = "update_time")
	@ColDefine(type = ColType.DATETIME, notNull = true)
	private Date updateTime;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
