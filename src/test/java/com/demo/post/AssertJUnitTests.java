package com.demo.post;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import com.demo.post.entity.Post;

public class AssertJUnitTests {
	@Test
	void createsPost() {
		Post post = new Post(1, 1, "Title", "body", null);
		assertThat(post.title())
			.startsWithIgnoringCase("t")
			.endsWith("e")
			.isEqualToIgnoringCase("title");
	}
}
