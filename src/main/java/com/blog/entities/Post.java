package com.blog.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    private Integer postId;
	private String title;
	private String content;
	private String imageName;
	private Date addedDate;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	@JsonIgnore // to ignore infinite recursion
	private Category category;
	
	@ManyToOne
	@JsonIgnore // to ignore infinite recursion
	private User user;
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();

}
