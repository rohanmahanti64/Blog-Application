package com.blog.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.payloads.UserDto;
import com.blog.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	@Autowired
	private UserService userService;
	
	// Creating new user -->
	@PreAuthorize("hasRole('ADMIN')") // only ADMIN can access the the API.
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser( @Valid @RequestBody UserDto userDto){
		
		UserDto createdUser = this.userService.createUser(userDto);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED); 
	}
	
	// Get Single user -->
	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable("userId") Integer userId){
		UserDto userById = userService.getUserById(userId);
		return new ResponseEntity<>(userById, HttpStatus.OK);
	}
	
	// Get All users -->
	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		List<UserDto> allUsers = this.userService.getAllUsers();
		return new  ResponseEntity<>(allUsers, HttpStatus.OK);
	}
	
	// Updating user -->
	@PutMapping("/{userId}")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,
			                        @PathVariable("userId") Integer userId){
		
		UserDto updatedUser = this.userService.updateUser(userDto, userId);
		return ResponseEntity.ok(updatedUser);
	}
	
	// Deleting User -->
	@PreAuthorize("hasRole('ADMIN')") // only ADMIN can access the the API.
	@DeleteMapping("/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable("userId") Integer userId){
		this.userService.deleteUser(userId);
		return new ResponseEntity<>("User deleted having user-id : "+ userId, 
				                                           HttpStatus.OK);
	}
	
	

}
