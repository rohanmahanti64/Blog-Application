package com.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Comment;
import com.blog.entities.Post;
import com.blog.entities.User;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CommentDto;
import com.blog.repositories.CommentRepository;
import com.blog.repositories.PostRepository;
import com.blog.repositories.UserRepository;
import com.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	PostRepository postRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public CommentDto doComment(CommentDto commentDto, Integer postId, Integer userId) {
		Post post = this.postRepository.findById(postId).orElseThrow(()->
		new ResourceNotFoundException("Post", "postId : ", postId));
		
		User user = this.userRepository.findById(userId).orElseThrow(() ->
		new ResourceNotFoundException("User","user-id", userId));
		
		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setContent(commentDto.getContent());
		comment.setPost(post);
		comment.setUser(user);
		//System.out.println(post.hashCode() +user.hashCode());
		
		Comment savedComment = this.commentRepository.save(comment);
		return this.modelMapper.map(savedComment, commentDto.getClass());
	}

	@Override
	public void deleteComment(Integer commentId) {
	  Comment comment = this.commentRepository.findById(commentId).orElseThrow(() ->
	  new ResourceNotFoundException("Comment","Comment-id", commentId));
	  this.commentRepository.delete(comment);

	}

	@Override
	public CommentDto updateComment(CommentDto commentDto, Integer postId, Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

}
