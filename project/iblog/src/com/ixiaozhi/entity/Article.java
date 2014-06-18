package com.ixiaozhi.entity;

/**
 * 文章entity
 * 周家俊　personal@zhoujiajun.com
 */

import java.util.Date;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.Text;
import com.google.appengine.api.users.User;

@PersistenceCapable
public class Article {
	// 主键key
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key key;

	// 文章作者google user
	@Persistent
	private User articleAuthor;

	// 文章标题
	@Persistent
	private String articleTitle;

	// 文章内容
	@Persistent
	private Text articleContent;

	// 文章描述
	@Persistent
	private String articleDescription;

	// 文章发布时间
	@Persistent
	private Date articlePostDate;

	// 文章查看次数
	@Persistent
	private int articleReaderCount;

	// 文章静态URL
	@Persistent
	private String articleStaticURL;

	// 文章标签（多属性）
	@Persistent
	private List<String> articleTags;

	public Article() {
	}

	public Article(User articleAuthor, String articleTitle,
			Text articleContent, String articleDescription,
			Date articlePostDate, int articleReaderCount,
			String articleStaticURL, List<String> articleTags) {
		this.articleAuthor = articleAuthor;
		this.articleTitle = articleTitle;
		this.articleContent = articleContent;
		this.articleDescription = articleDescription;
		this.articlePostDate = articlePostDate;
		this.articleReaderCount = articleReaderCount;
		this.articleStaticURL = articleStaticURL;
		this.articleTags = articleTags;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
	}

	public User getArticleAuthor() {
		return articleAuthor;
	}

	public void setArticleAuthor(User articleAuthor) {
		this.articleAuthor = articleAuthor;
	}

	public String getArticleTitle() {
		return articleTitle;
	}

	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}

	public Text getArticleContent() {
		return articleContent;
	}

	public void setArticleContent(Text articleContent) {
		this.articleContent = articleContent;
	}

	public String getArticleDescription() {
		return articleDescription;
	}

	public void setArticleDescription(String articleDescription) {
		this.articleDescription = articleDescription;
	}

	public Date getArticlePostDate() {
		return articlePostDate;
	}

	public void setArticlePostDate(Date articlePostDate) {
		this.articlePostDate = articlePostDate;
	}

	public int getArticleReaderCount() {
		return articleReaderCount;
	}

	public void setArticleReaderCount(int articleReaderCount) {
		this.articleReaderCount = articleReaderCount;
	}

	public String getArticleStaticURL() {
		return articleStaticURL;
	}

	public void setArticleStaticURL(String articleStaticURL) {
		this.articleStaticURL = articleStaticURL;
	}

	public List<String> getArticleTags() {
		return articleTags;
	}

	public void setArticleTags(List<String> articleTags) {
		this.articleTags = articleTags;
	}

}
