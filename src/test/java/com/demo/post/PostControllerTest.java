package com.demo.post;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.demo.post.controller.PostController;
import com.demo.post.entity.Post;
import com.demo.post.repository.PostRepository;

@WebMvcTest(PostController.class)
@AutoConfigureMockMvc
public class PostControllerTest {
	@Autowired
	MockMvc mockMvc;

	@MockBean
	PostRepository postRepository;

	List<Post> posts = new ArrayList<>();

	@BeforeEach
	void setUp() {
		// create posts
		posts = List.of(
			new Post(1, 1, "Hello, World!", "This is my first post.", null),
			new Post(2, 1, "Second Post", "This is my second post.", null)
		);
	}

	@Test
	public void shouldFindAllPosts() throws Exception {

		// Setup an expected output
		String jsonResponse = """
				[
				    {
				        "id":1,
				        "userId":1,
				        "title":"Hello, World!",
				        "body":"This is my first post.",
				        "version": null
				    },
				    {
				        "id":2,
				        "userId":1,
				        "title":"Second Post",
				        "body":"This is my second post.",
				        "version": null
				    }
				]
				""";

		// Setup a mock response
		when(postRepository.findAll()).thenReturn(posts);

		mockMvc.perform(get("/api/v1/posts"))
			.andExpect(status().isOk())
			.andExpect(content().json(jsonResponse));
	}
}
