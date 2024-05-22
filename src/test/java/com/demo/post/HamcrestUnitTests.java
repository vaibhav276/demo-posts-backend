package com.demo.post;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import com.demo.post.entity.Post;

public class HamcrestUnitTests {
	@Test
	void createsPost() {
		Post post1 = new Post(1, 1, "title", "body", null);
		Post post2 = new Post(1, 1, "title", "body", null);

		// assertThat(post1.equalTo(post2));
	}
}
