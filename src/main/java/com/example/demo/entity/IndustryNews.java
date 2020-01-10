package com.example.demo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "INDUSTRY_NEWS")
public class IndustryNews {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "NEWS_SEQ")
	@Column
	private Long id;
	@JsonProperty("CIN")
	@Column(name = "SAVERISK_CIN", nullable = false)
	private String saveriskCin;

	@JsonProperty("News_Title")
	@Column(name = "NEWS_TITLE")
	private String newTitle;

	@JsonProperty("Link")
	@Column(name = "LINK")
	private String link;

	@JsonProperty("Date")
	@Column(name = "NEWS_DATE")
	private String newsDate;

	@JsonProperty("Source_Name")
	@Column(name = "SOURCE_NAME")
	private String sourceName;

	@JsonProperty("Description")
	@Column(name = "DESCRIPTION")
	private String description;

	@JsonProperty("News_Type")
	@Column(name = "NEWS_TYPE")
	private String newsType;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date")
	private Date createDate;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "modify_date")
	private Date modifyDate;

	public IndustryNews(IndustryNews industryNews) {
		this.saveriskCin = industryNews.saveriskCin;
		this.link = industryNews.link;
		this.newTitle = industryNews.newTitle;
		this.description = industryNews.description;
		this.newsDate = industryNews.newsDate;
		this.sourceName = industryNews.sourceName;
		this.newsType = industryNews.newsType;

	}

	public IndustryNews() {
	}

	public String getSaveriskCin() {
		return saveriskCin;
	}

	public void setSaveriskCin(String saveriskCin) {
		this.saveriskCin = saveriskCin;
	}

	public String getNewTitle() {
		return newTitle;
	}

	public void setNewTitle(String newTitle) {
		this.newTitle = newTitle;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getNewsDate() {
		return newsDate;
	}

	public void setNewsDate(String newsDate) {
		this.newsDate = newsDate;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getNewsType() {
		return newsType;
	}

	public void setNewsType(String newsType) {
		this.newsType = newsType;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Override
	public String toString() {
		return "IndustryNews [id=" + id + ", cin=" + saveriskCin + ", newsTitle=" + newTitle + ", link=" + link
				+ ", date=" + newsDate + ", sourceName=" + sourceName + ", description=" + description + ", newsType="
				+ newsType + "]";
	}
}