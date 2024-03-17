package com.blog.payloads;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ApiResponse {
	private String message;
	private boolean success;

}
