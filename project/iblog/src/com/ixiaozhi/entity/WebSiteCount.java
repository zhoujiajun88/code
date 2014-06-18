package com.ixiaozhi.entity;

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/**
 * 网站访问计数--隐藏功能
 * 
 * @author 周家俊　personal@zhoujiajun.com
 * 
 */
@PersistenceCapable
public class WebSiteCount {
	// 主键key
	@PrimaryKey
	private String key;

	// 所有文章计数
	@Persistent
	private long viewNumber;

	public WebSiteCount() {
	}

	public WebSiteCount(String key, long viewNumber) {
		super();
		this.key = key;
		this.viewNumber = viewNumber;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public long getViewNumber() {
		return viewNumber;
	}

	public void setViewNumber(long viewNumber) {
		this.viewNumber = viewNumber;
	}

}
