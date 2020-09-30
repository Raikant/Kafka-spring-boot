package com.example.demo.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Topic implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1631233754268840209L;
	private String id;
	private String name;
	private String description;
}
