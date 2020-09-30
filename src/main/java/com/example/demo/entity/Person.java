package com.example.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@IdClass(PersonId.class)
public class Person implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3692696965467321275L;
	@Id
	@Column
	@JsonProperty(value = "Id")
	private Integer id;
	@Id
	@Column
	@JsonProperty(value = "UserId")
	private Integer userId;
	@Column
	@JsonProperty(value = "Title")
	private String title;
	@Column
	@JsonProperty(value = "Completed")
	private Boolean completed;
}