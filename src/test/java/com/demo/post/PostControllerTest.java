package com.demo.post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.demo.post.controller.PostController;
import com.demo.post.entity.Post;
import com.demo.post.exception.PostNotFoundException;
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

	// GET /api/v1/posts/1
	@Test
	public void shouldFindPostByValidId() throws Exception {

		// Setup an expected output
		String jsonResponse = """
				    {
				        "id":1,
				        "userId":1,
				        "title":"Hello, World!",
				        "body":"This is my first post.",
				        "version": null
				    }
				""";
		
		// Setup a mock response
		Post post = posts.get(0);
		when(postRepository.findById(1)).thenReturn(Optional.of(post));

		mockMvc.perform(get("/api/v1/posts/1"))
			.andExpect(status().isOk())
			.andExpect(content().json(jsonResponse));
	}

	// GET /api/v1/posts/999
	@Test
	public void shouldNotFindPostByInvalidId() throws Exception {

		// Setup a mock response
		when(postRepository.findById(999)).thenThrow(PostNotFoundException.class);

		mockMvc.perform(get("/api/v1/posts/999"))
			.andExpect(status().isNotFound());
	}

	// POST /api/v1/posts
	@Test
	public void shouldCreatePostForValidInput() throws Exception {
		String jsonBody = """
				{
					"id":222,
					"userId":1,
					"title":"My test post",
					"body":"This is created in testing",
					"version": null
				}
				""";

		Post post = new Post(222, 1, "My test post", "This is created in testing", null);
		when(postRepository.save(post)).thenReturn(post);

		mockMvc.perform(post("/api/v1/posts")
				.contentType("application/json")
				.content(jsonBody))
				.andExpect(status().isCreated())
				.andExpect(content().json(jsonBody));
	}

	// POST /api/v1/posts
	@Test
	public void shouldNotCreatePostForInvalidInput() throws Exception {
		String jsonBody = """
				{
					"id": 222,
					"userId":1,
					"title": null,
					"body":"This should not be created",
					"version": null
				}
				""";

		Post post = new Post(222, 1, "My automatic post", "This is created automatically", null);
		when(postRepository.save(post)).thenReturn(post);

		mockMvc.perform(post("/api/v1/posts")
				.contentType("application/json")
				.content(jsonBody))
				.andExpect(status().isBadRequest());
	}

	// PATCH /api/v1/posts/1
	@Test
	public void shouldUpdatePostWithValidIdAndBody() throws Exception {
		String jsonBody = """
				{
					"id": 1,
					"userId":1,
					"title": "Post updated by test",
					"body":"Updated body",
					"version": null
				}
				""";

		Post post = new Post(1, 1, "Post updated by test", "Updated body", null);
		when(postRepository.save(post)).thenReturn(post);
		when(postRepository.findById(1)).thenReturn(Optional.of(post));

		mockMvc.perform(patch("/api/v1/posts/1")
				.contentType("application/json")
				.content(jsonBody))
				.andExpect(status().isOk())
				.andExpect(content().json(jsonBody));
	}

	// PATCH /api/v1/posts/999
	@Test
	public void shouldNotUpdatePostWithInvalidId() throws Exception {
		String jsonBody = """
				{
					"id": 999,
					"userId":1,
					"title": "Post updated by test",
					"body":"Updated body",
					"version": null
				}
				""";

		Post post = new Post(999, 1, "Post updated by test", "Updated body", null);
		when(postRepository.save(post)).thenReturn(post);
		when(postRepository.findById(999)).thenThrow(PostNotFoundException.class);

		mockMvc.perform(patch("/api/v1/posts/999")
				.contentType("application/json")
				.content(jsonBody))
				.andExpect(status().isNotFound());
	}

	// PATCH /api/v1/posts/1
	@Test
	public void shouldNotUpdatePostWithInvalidBody() throws Exception {
		String jsonBody = """
				{
					"id": 1,
					"userId":1,
					"title": null
					"body":"Updated body",
					"version": null
				}
				""";

		Post post = new Post(1, 1, "Post updated by test", "Updated body", null);
		when(postRepository.save(post)).thenReturn(post);
		when(postRepository.findById(1)).thenReturn(Optional.of(post));

		mockMvc.perform(patch("/api/v1/posts/999")
				.contentType("application/json")
				.content(jsonBody))
				.andExpect(status().isBadRequest());
	}

	// DELETE /api/v1/posts/1
	@Test
	public void shouldDeletePostWithValidId() throws Exception {

		Post post = new Post(1, 1, "Post updated by test", "Updated body", null);
		when(postRepository.findById(1)).thenReturn(Optional.of(post));
		doNothing().when(postRepository).deleteById(1);

		mockMvc.perform(delete("/api/v1/posts/1"))
				.andExpect(status().isOk());

		verify(postRepository, times(1)).deleteById(1);
	}

	// DELETE /api/v1/posts/999
	@Test
	public void shouldNotDeletePostWithInvalidId() throws Exception {

		when(postRepository.findById(999)).thenThrow(PostNotFoundException.class);
		doNothing().when(postRepository).deleteById(999);

		mockMvc.perform(delete("/api/v1/posts/999"))
				.andExpect(status().isNotFound());
	}
}
