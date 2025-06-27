package com.example.service;

import java.util.List;

import com.example.dto.CategoryDto;
import com.example.dto.CategoryResponse;
import com.example.entity.Category;



public interface CategoryService {
public Boolean saveCategory(CategoryDto categoryDto) throws Exception;
	
	public List<CategoryDto> getAllCategory();

	public List<CategoryResponse> getActiveCategory();

	public CategoryDto getCategoryById(Integer id) throws Exception;

	public Boolean deleteCategory(Integer id);

}
