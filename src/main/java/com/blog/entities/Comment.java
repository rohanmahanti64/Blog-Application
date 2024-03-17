package com.blog.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;

@Getter
@Setter

@Entity
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer commentId;
	private String content;
	@ManyToOne
	@JoinColumn(name = "post_id")
	@JsonIgnore
	private Post post;
	@ManyToOne
	@JsonIgnore
	private User user;
	

}
