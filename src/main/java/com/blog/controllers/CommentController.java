package com.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.ApiResponse;
import com.blog.payloads.CommentDto;
import com.blog.services.CommentService;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
     // handler to comment on post -->
	@PostMapping("/{postId}/{userId}")
	public ResponseEntity<CommentDto> doComment(@RequestBody CommentDto commentDto,
			@PathVariable("postId") Integer postId, @PathVariable Integer userId){
		CommentDto commentDto2 = this.commentService.doComment(commentDto, postId, userId);
		return new ResponseEntity<CommentDto>(commentDto2, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/delete/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
		this.commentService.deleteComment(commentId);
		ApiResponse apr = new ApiResponse("Comment Deleted successfully", true);
		return new ResponseEntity<ApiResponse>(apr, HttpStatus.OK);
	}
	
}
