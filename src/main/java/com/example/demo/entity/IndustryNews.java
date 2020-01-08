package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IndustryNews {
	@JsonProperty("CIN")
	private String cin;
	@JsonProperty("News_Title")
	private String newsTitle;
	@JsonProperty("Link")
	private String link;
	@JsonProperty("Date")
	private String date;
	@JsonProperty("Source_Name")
	private String sourceName;
	@JsonProperty("Description")
	private String description;
	@JsonProperty("News_Type")
	private String newsType;

	public String getCin() {
		return cin;
	}

	public void setCin(String cin) {
		this.cin = cin;
	}

	public String getNewsTitle() {
		return newsTitle;
	}

	public void setNewsTitle(String newsTitle) {
		this.newsTitle = newsTitle;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
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
}