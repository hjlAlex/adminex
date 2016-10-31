package com.gzwabao.entity;

import java.io.Serializable;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * 前端图片
 * 
 * @since 2015年11月5日 上午11:10:12
 * @author Alex
 */
@Table(value = "picture")
public class Picture implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id(auto = true)
	// id主键,自增
	private int id;

	@Column(value = "path")
	@ColDefine(type = ColType.VARCHAR, width = 250, notNull = false)
	private String path;

	@Column(value = "name")
	@ColDefine(type = ColType.VARCHAR, width = 50, notNull = false)
	private String name;

	@Column(value = "remark")
	@ColDefine(type = ColType.VARCHAR, width = 250, notNull = false)
	private String remark;

	@Column(value = "ext1")
	@ColDefine(type = ColType.VARCHAR, width = 250, notNull = false)
	private String ext1;

	@Column(value = "ext2")
	@ColDefine(type = ColType.VARCHAR, width = 250, notNull = false)
	private String ext2;

	@Column(value = "ext3")
	@ColDefine(type = ColType.VARCHAR, width = 250, notNull = false)
	private String ext3;

	@Column(value = "ext4")
	@ColDefine(type = ColType.VARCHAR, width = 250, notNull = false)
	private String ext4;

	@Column(value = "ext5")
	@ColDefine(type = ColType.VARCHAR, width = 250, notNull = false)
	private String ext5;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public String getExt3() {
		return ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	public String getExt4() {
		return ext4;
	}

	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}

	public String getExt5() {
		return ext5;
	}

	public void setExt5(String ext5) {
		this.ext5 = ext5;
	}

}
