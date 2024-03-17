package com.blog.payloads;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryDto {
private Integer categoryId;
@NotBlank(message = "category titlr must not be blank ! ")
private String categoryTitle;
private String categoryDesc;
}
