package com.blog.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private int uid;
	@NotBlank(message = "Username must not be blank ! ")
	private String name;
	@NotBlank
	@Email(message = "Email must be well formated ! ")
	private String email;
	@NotBlank
	@Size(min = 3, message = "passward must be min of 3 chars ! ")
	  
	private String password;
	private String about;
	private Set<RoleDto> roles = new HashSet<>();

	@JsonIgnore // this annotation for not sending the password to client with response 
	public String getPassword() {
		 return this.password;
	}
	
	@JsonProperty // this annotation will help to serialize the password coming from client  
	public void setPassword(String password) {
		this.password = password;
	}
}
