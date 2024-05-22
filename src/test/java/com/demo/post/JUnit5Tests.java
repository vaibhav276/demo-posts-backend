package com.demo.post;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.demo.post.entity.Post;

public class JUnit5Tests {
	@Test
	public void createsPost() {
		Post post = new Post(1, 1, "Title", "Body", null);
		Assertions.assertEquals(post.title(), "Title");
	}
}
