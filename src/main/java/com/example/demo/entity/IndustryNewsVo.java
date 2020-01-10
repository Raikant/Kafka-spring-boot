package com.example.demo.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IndustryNewsVo {

	@JsonProperty("Industry News")
	List<IndustryNews> industryNewsList;

	public List<IndustryNews> getIndustryNewsList() {
		return industryNewsList;
	}

	public void setIndustryNewsList(List<IndustryNews> industryNewsList) {
		this.industryNewsList = industryNewsList;
	}

	@Override
	public String toString() {
		return "IndustryNewsVo [industryNewsList=" + industryNewsList + "]";
	}

}
