package com.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.blog.entities.Category;
import com.blog.entities.Post;
import com.blog.entities.User;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
	// select * from post where post = x;
	// below method represents the above query 
	// spring data-jpa provides the implementation by the method name and the
	// passed parameter.
	// this is called custom finder method. 
 public List<Post> findByUser(User user);
 public List<Post> findByCategory(Category category); 
 
 // custom finder method for searching operation -->
  public List<Post> findByTitleContaining(String title);
  // by using Query -->
  // @Query("select p from Post p where p.title like :key")
  @Query("SELECT p FROM Post p WHERE " +
          "p.title LIKE CONCAT('%',:title, '%')")
  public List<Post> findByTitle(String title);
 
}
