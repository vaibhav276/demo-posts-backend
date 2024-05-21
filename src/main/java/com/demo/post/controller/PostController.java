package com.demo.post.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.demo.post.entity.Post;
import com.demo.post.exception.PostNotFoundException;
import com.demo.post.repository.PostRepository;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "api/v1/posts")
@Validated
public class PostController {

	private final PostRepository postRepository;

	PostController(PostRepository postRepository) {
		this.postRepository = postRepository;
	}
	
	@GetMapping("")
	List<Post> getAll() {
		return postRepository.findAll();
	}

	@GetMapping("/{id}")
	Optional<Post> getById(@PathVariable("id") Integer id) {
		return Optional.ofNullable(
			postRepository.findById(id)
			.orElseThrow(PostNotFoundException::new));
	}

	@PostMapping("")
	@ResponseStatus(HttpStatus.CREATED)
	public Post create(@RequestBody @Valid Post post) {
		return postRepository.save(post);
	}
	
	@PatchMapping("/{id}")
	Optional<Post> updateById(@PathVariable("id") Integer id,
														@RequestBody @Valid Post updatedPost) {
		postRepository.findById(id).orElseThrow(PostNotFoundException::new);
		return Optional.of(postRepository.save(updatedPost));
	}
	
	@DeleteMapping("/{id}")
	void deleteById(@PathVariable("id") Integer id) {
		postRepository.findById(id).orElseThrow(PostNotFoundException::new);
		postRepository.deleteById(id);
	}
}
