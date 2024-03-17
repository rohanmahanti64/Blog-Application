package com.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.repositories.CategoryRepository;
import com.blog.repositories.PostRepository;
import com.blog.repositories.UserRepository;
import com.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	PostRepository postRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	PostResponse postResponse;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User user = this.userRepository.findById(userId).orElseThrow(() -> 
		new ResourceNotFoundException("User", "User id", userId));
		
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(() ->
		new ResourceNotFoundException("Category", "Category id : ", categoryId));
		
		Post post = this.dtoToPost(postDto);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post savedPost = this.postRepository.save(post);
		return this.postToDto(savedPost);
	}

	@Override
	public PostDto getPost(Integer postId) {
		Post post = this.postRepository.findById(postId).orElseThrow(() ->
		new ResourceNotFoundException("Post", "post id", postId));
		return postToDto(post);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize,
			String sortBy, String sortDir) {
		// implementation of pagenation -->
	    // implementation of sorting -->
		// this if else condition to work on dynamic request on sort direction (ascending / descending)
		Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc")|| sortDir.equalsIgnoreCase("ascending")) {
			sort = Sort.by(sortBy).ascending();
		}
		else {
			sort = Sort.by(sortBy).descending();
		}
		// creating pageable object.
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort) ;
		// this findAll method of postRepository returns Page not list.
		Page<Post> pagePosts = this.postRepository.findAll(pageable);
		// converting Page<Post> to List<Post> 
		List<Post> allPosts = pagePosts.getContent();
		
		// converting Post to postDto ->
		List<PostDto> postDtos = allPosts.stream().map(post -> postToDto(post))
		.collect(Collectors.toList());
		
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setLastPage(pagePosts.isLast());
		return postResponse;
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepository.findById(postId).orElseThrow(() ->
		new ResourceNotFoundException("Post", "post id", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatedPost = this.postRepository.save(post);
		return postToDto(updatedPost);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepository.findById(postId).orElseThrow(() ->
		new ResourceNotFoundException("Post", "post id", postId));
		this.postRepository.delete(post);

	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user = this.userRepository.findById(userId).orElseThrow(()->
		new ResourceNotFoundException("User", "user id", userId));
		List<Post> posts = this.postRepository.findByUser(user);
		// converting Post to postDto -->
		List<PostDto> postDtos = posts.stream().map(post -> postToDto(post))
		.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->
		new ResourceNotFoundException("Category", "Category Id", categoryId));
		// getting posts by using custom finder method of postRepository
		List<Post> posts = this.postRepository.findByCategory(category);
		// converting Post to postDto's -->
		List<PostDto> postDtos = posts.stream().map(post -> postToDto(post))
		.collect(Collectors.toList());
		return postDtos;
	}
	
	
	
	 // converting Post to PostDto -->
	public PostDto postToDto(Post post) {
		PostDto postDto = this.modelMapper.map(post, PostDto.class);
		return postDto;
	}
	// converting PostDto to Post -->
	public Post dtoToPost(PostDto postDto) {
		Post post = this.modelMapper.map(postDto, Post.class);
		return post;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		//List<Post> posts = this.postRepository.findByTitleContaining(keyword);
		List<Post> posts = this.postRepository.findByTitle(keyword);
		// converting the Post to PostDtos 
		System.out.println(posts);
		List<PostDto> postDtos = posts.stream().map(post -> postToDto(post)).collect(Collectors.toList());
		System.out.println("LLLLLLL");
		return postDtos;
	}

}
