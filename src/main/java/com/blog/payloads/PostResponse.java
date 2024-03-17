package com.blog.payloads;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
// this class is for proper  implementation of pagenation -->
@Component
public class PostResponse {
 private List<PostDto> content;
 private Integer pageNumber;
 private Integer pageSize;
 private Long totalElements;
 private Integer totalPages;
 private boolean isLastPage;
 
}
