/**
 * 文件名:ImageJson.java
 * 项目:ticket
 * 包名:com.ticket.json
 * 作者:hjl
 * 日期:2014-12-7
 */
package com.gzwabao.json;

/**
 * 媒体公告内容编辑上传JSON对象
 * 
 * @author hjl
 * @date 2015年4月2日
 */
public class ImageJson {
	private int error; // 上传结果 0：正常
	private String url; // 上传文件的url路径
	private String message; // 失败信息

	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "{\"error\":" + error + ", \"url\":\"" + url
				+ "\", \"message\":\"" + message + "\"}";
	}

}
