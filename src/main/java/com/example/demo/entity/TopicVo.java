package com.example.demo.entity;

import java.util.List;

public class TopicVo {
	private List<Topic> topics;

	public List<Topic> getTopics() {
		return topics;
	}

	public void setTopics(List<Topic> topics) {
		this.topics = topics;
	}

	@Override
	public String toString() {
		return "TopicVo [topics=" + topics + "]";
	}
}
