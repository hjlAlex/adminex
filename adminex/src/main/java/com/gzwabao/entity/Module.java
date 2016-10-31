package com.gzwabao.entity;

import java.io.Serializable;

import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.ColType;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Name;
import org.nutz.dao.entity.annotation.Table;

/**
 * 
 * @since 2015年12月11日 上午11:10:12
 * @author Alex
 */
@Table(value = "module")
public class Module implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int TYPE_PICTURE = 1;// 图片类型
	public static final int TYPE_NEWS = 2;// 资讯类型
	public static final int TYPE_NAV = 3;// 导航类型
	@Id(auto = true)
	// id主键,自增
	private int id;

	@Name(casesensitive = true)
	// 字符型主键,大小写敏感
	@Column(value = "str_flag")
	@ColDefine(type = ColType.VARCHAR, width = 50, notNull = true)
	private String strFlag;

	// 所属页面Id
	@Column(value = "page_id")
	@ColDefine(type = ColType.INT, width = 32, notNull = true)
	private int pageId;

	@Column(value = "type")
	@ColDefine(type = ColType.INT, width = 32, notNull = true)
	private int type;

	@Column(value = "relate_ids")
	@ColDefine(type = ColType.VARCHAR, width = 250, notNull = true)
	private String relateIds;

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

	public String getStrFlag() {
		return strFlag;
	}

	public void setStrFlag(String strFlag) {
		this.strFlag = strFlag;
	}

	public int getPageId() {
		return pageId;
	}

	public void setPageId(int pageId) {
		this.pageId = pageId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRelateIds() {
		return relateIds;
	}

	public void setRelateIds(String relateIds) {
		this.relateIds = relateIds;
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
