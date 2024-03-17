package com.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.entities.Category;
import com.blog.exceptions.ResourceNotFoundException;
import com.blog.payloads.CategoryDto;
import com.blog.repositories.CategoryRepository;
import com.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	ModelMapper modelMapper;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.categoryDtoToCategory(categoryDto);
		Category savedCategory = this.categoryRepository.save(category);
		
		return this.categoryToCategoryDto(savedCategory);
		
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(() -> 
		new ResourceNotFoundException("Category", "Category Id", categoryId));
		return categoryToCategoryDto(category);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categories = this.categoryRepository.findAll();
		
		// converting all categories to categoryDtos -->
		List<CategoryDto> categoryDtos = categories.stream().map(category -> this.categoryToCategoryDto(category))
		       .collect(Collectors.toList());
		return categoryDtos;
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category category = this.categoryRepository.findById(categoryId)
	.orElseThrow(() -> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDesc(categoryDto.getCategoryDesc());
		Category updatedCategory = this.categoryRepository.save(category);
		return categoryToCategoryDto(updatedCategory);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
	Category category = this.categoryRepository.findById(categoryId).orElseThrow(()-> new 
		ResourceNotFoundException("Category", "CategoryId", categoryId));
	this.categoryRepository.delete(category);

	}
	// CategoryDto to Category -->
	public Category categoryDtoToCategory( CategoryDto categoryDto) {
		Category category = this.modelMapper.map(categoryDto, Category.class);
		return category;
	}
	
	// Category to CategoryDto -->
	public CategoryDto categoryToCategoryDto(Category category) {
		CategoryDto categoryDto = this.modelMapper.map(category, CategoryDto.class);
		return categoryDto;
	}

}
