package com.blog.services;

import java.util.List;

import com.blog.payloads.UserDto;

public interface UserService {
	public UserDto createUser(UserDto user);
	
	public UserDto getUserById(Integer userId);
	
	public List<UserDto> getAllUsers();
	
	public UserDto updateUser(UserDto user, Integer userId);
	
	public void deleteUser(Integer userId);
	
	public UserDto registerNewUser(UserDto userDto);

}
