package com.demo.post.repository;

import org.springframework.data.repository.ListCrudRepository;

import com.demo.post.entity.Post;

public interface PostRepository extends ListCrudRepository<Post, Integer> {
	
}
