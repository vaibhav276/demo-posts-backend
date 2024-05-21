package com.demo.post.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import jakarta.validation.constraints.NotNull;

public record Post(
	@Id
	Integer id, 
	
	Integer userId, 

	@NotNull(message = "Title cannot be null")
	String title, 

	String body,

	@Version
	Integer version) {

}
