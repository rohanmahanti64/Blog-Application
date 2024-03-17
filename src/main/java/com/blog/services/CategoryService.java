package com.blog.services;

import java.util.List;

import com.blog.payloads.CategoryDto;

public interface CategoryService {
	
	// create Category -->
	public CategoryDto createCategory(CategoryDto categoryDto);
	
	// get a single category -->
	public CategoryDto getCategory(Integer categoryId);
	
	// get all categories -->
	public List<CategoryDto> getAllCategories();
	
	// update category -->
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	
	// Delete Category -->
	
	public void deleteCategory(Integer categoryId);

	

}
