package com.ixiaozhi.entity;

/**
 * 文章云标签entity
 * 周家俊　personal@zhoujiajun.com
 */

import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class Tags {
	// 主键key
	@PrimaryKey
	private String tagName;

	public Tags() {
	}

	public Tags(String tagName) {
		this.tagName = tagName;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

}
