package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.blog.config.AppConstraints;
import com.blog.entities.Role;
import com.blog.entities.User;
import com.blog.payloads.UserDto;
import com.blog.repositories.RoleRepository;
import com.blog.repositories.UserRepository;
import com.blog.services.UserService;
import com.blog.exceptions.*;
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user = dtoToUser(userDto);
		 User savedUser =this.userRepository.save(user);
		
		return userToDto(savedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepository.findById(userId).orElseThrow(() ->
		new ResourceNotFoundException("User", "Id", userId));
		UserDto userDto = userToDto(user);
		return userDto;
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepository.findAll();
		// converting the list of users to list of userDtos -->
		
		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user))
				.collect(Collectors.toList());
		return userDtos;
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
	 User user = userRepository.findById(userId).orElseThrow(()->
	 new ResourceNotFoundException("User", "id", userId));
	 
	 user.setName(userDto.getName());
	 user.setEmail(userDto.getEmail());
	 user.setPassword(userDto.getPassword());
	 user.setAbout(userDto.getAbout());
	 
	 User updatedUser = userRepository.save(user);
	 UserDto updatedUserDto = userToDto(updatedUser);
	 
		return updatedUserDto;
	}

	@Override
	public void deleteUser(Integer userId) {
	 User user = this.userRepository.findById(userId).orElseThrow(()->
	 new ResourceNotFoundException("User", "Id", userId));
	 this.userRepository.delete(user);

	}
	
	public User dtoToUser(UserDto userDto) {
		// manual process to convert Dto to User
//		User user = new User();
//		user.setUid(userDto.getUid());
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setPassward(userDto.getPassward());
//		user.setAbout(userDto.getAbout());
//		return user;
		
		// By using MOdelMapper -->
		User user = this.modelMapper.map(userDto, User.class);
		return user;
	}
	//converting User to UserDto -->
	public UserDto userToDto(User user) {
		// Manually converting User to Dto -->
		
//		UserDto userDto = new UserDto();
//		userDto.setUid(user.getUid());
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setPassward(user.getPassward());
//		userDto.setAbout(user.getAbout());
//		return userDto;
		
		// By using ModelMapper -->
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		// saving encoded password in user entity -->
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		// setting role of a user as NORMAL_USER by default while creating a user. 
		Role role = this.roleRepository.findById(AppConstraints.NORMAL_USER).get();
		user.getRoles().add(role);
		User registeredUser = this.userRepository.save(user);
		return this.userToDto(registeredUser);
	}
	

}
