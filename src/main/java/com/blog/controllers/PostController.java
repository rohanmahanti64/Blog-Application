package com.blog.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.PostDto;
import com.blog.payloads.PostResponse;
import com.blog.services.FileService;
import com.blog.services.PostService;

@RestController
@RequestMapping("/api/post")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}") // this is for retriving path from application.properties file
	private String path;

	// handler for creating new post -->
	@PostMapping("/{userId}/{categoryId}")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, 
			@PathVariable("userId") Integer userId, 
			@PathVariable("categoryId") Integer categoryId){
		System.out.println("********");
		PostDto createdPost = this.postService.createPost(postDto, userId, categoryId);

		return new ResponseEntity<>(createdPost, HttpStatus.CREATED);
	}
	
	// handler for getting post by postId -->
	@GetMapping("/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable("postId") Integer postId){
		PostDto post = this.postService.getPost(postId);
		return new ResponseEntity<PostDto>(post, HttpStatus.OK);
	}
	
	// handler for getting all posts -->
	@GetMapping("/all")
	public ResponseEntity<PostResponse> getAllPosts(
	@RequestParam(value = "pageNumber", defaultValue = "0",required = false) Integer pageNumber,
	@RequestParam(value = "pageSize", defaultValue = "4", required = false) Integer pageSize, 
	@RequestParam(value = "sortBy", defaultValue = "postId", required = false) String sortBy, 
	@RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){
		PostResponse postResponse = this.postService.getAllPosts(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<PostResponse>(postResponse, HttpStatus.OK);
	}
	
	// handler for getting post of a user -->
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<PostDto>> getAllPostsByUser(@PathVariable
			("userId") Integer userId){
		List<PostDto> postsByUser = this.postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(postsByUser, HttpStatus.OK);
	}
	// Handler for getting posts of a category -->
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<PostDto>> getAllPostsByCategory(@PathVariable
			("categoryId") Integer categoryId){
		List<PostDto> postsByCategory = this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(postsByCategory, HttpStatus.OK);
	}
	// handler for updating post -->
	@PutMapping("/update/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,
			@PathVariable("postId") Integer postId){
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
	}
	
	// handler for deleting post -->
	@DeleteMapping("/delete/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable("postId")
	Integer postId){
		this.postService.deletePost(postId);
		ApiResponse apiResponse = new ApiResponse("post deleted successfully", true);
		return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
	}
	
	// handler for search functionality -->
	@GetMapping("/search")
	public ResponseEntity<List<PostDto>> searchPosts(@RequestParam("keyword") String keyword){
		System.out.println(keyword);
		List<PostDto> searchedPosts = this.postService.searchPosts(keyword);
		System.out.println(searchedPosts);
		return new ResponseEntity<List<PostDto>>(searchedPosts, HttpStatus.OK);
	}
	
	// handler to upload postImage 
	@PostMapping("/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestParam("image") MultipartFile image,
			@PathVariable("postId") Integer postId) throws IOException{
		
		PostDto postDto = this.postService.getPost(postId);
		String fileName = this.fileService.uploadImage(path, image);
		
		postDto.setImageName(fileName);
		PostDto updatedPost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatedPost, HttpStatus.OK);
	}
}
