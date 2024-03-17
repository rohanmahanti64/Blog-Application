package com.blog.services;

import com.blog.payloads.CommentDto;

public interface CommentService {

	public CommentDto doComment(CommentDto commentDto, Integer postId, Integer userId);
	public void deleteComment(Integer commentId);
	public CommentDto updateComment(CommentDto commentDto, Integer postId, Integer userId);
}
