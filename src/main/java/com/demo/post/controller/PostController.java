package com.demo.post.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.post.entity.Post;
import com.demo.post.exception.PostNotFoundException;
import com.demo.post.repository.PostRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping(path = "api/v1/posts")
public class PostController {

	private final PostRepository postRepository;

	PostController(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	@GetMapping("")
	List<Post> findAll() {
		return postRepository.findAll();
	}

	@GetMapping("/{id}")
	Optional<Post> findById(@PathVariable("id") Integer id) {
		return Optional.ofNullable(
			postRepository.findById(id)
			.orElseThrow(PostNotFoundException::new));
	}
	
}
