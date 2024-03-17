package com.blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	// custom finder method to get user by email. optional class is used for null check. 
	public Optional<User> findByEmail(String email);
}
