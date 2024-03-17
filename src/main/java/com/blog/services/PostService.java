package com.blog.services;

import java.util.List;

import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;

public interface PostService {

	// create post -->
	public PostDto createPost(PostDto post,Integer userId, Integer postId);
	
	// get a single post -->
	public PostDto getPost(Integer postId);
	
	// get all posts -->
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize,
			String sortBy, String sortDir);
	
	// update post -->
	public PostDto updatePost(PostDto post, Integer postId);
	
	// delete post -->
	public void deletePost(Integer postId);
	
	// get all post by User -->
	public List<PostDto> getPostsByUser(Integer userId);
	
	// get all posts by Category -->
	public List<PostDto> getPostsByCategory(Integer categoryId);
	
	// search post by title -->
	public List<PostDto> searchPosts(String keyword);
}
