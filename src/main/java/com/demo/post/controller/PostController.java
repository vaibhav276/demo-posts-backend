package com.demo.post.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.post.entity.Post;
import com.demo.post.repository.PostRepository;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping(path = "api/v1/posts")
public class PostController {

	private final PostRepository postRepository;

	PostController(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	@GetMapping("")
	public List<Post> getPosts() {
		return postRepository.findAll();
	}
	
}
