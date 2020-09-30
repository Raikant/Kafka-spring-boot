package com.example.demo.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PersonId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2565951887693075693L;
		
	private Integer userId;
	private Integer id;
}
