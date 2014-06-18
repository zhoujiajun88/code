package com.ixiaozhi.entity;

/**
 * 所有的文章数统计
 *  周家俊　personal@zhoujiajun.com
 */
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable
public class ArticleCount {
	// 主键key
	@PrimaryKey
	private String key;

	// 所有文章计数
	@Persistent
	private int articleNumber;

	public ArticleCount() {
	}

	public ArticleCount(String key, int articleNumber) {
		this.key = key;
		this.articleNumber = articleNumber;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getArticleNumber() {
		return articleNumber;
	}

	public void setArticleNumber(int articleNumber) {
		this.articleNumber = articleNumber;
	}

}
