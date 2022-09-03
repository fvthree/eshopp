package com.fvthree.eshop.categories;

import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface CategoryService {
	Category save(CategoryDto categoryDto);
	CategoriesResponse getAllCategory(int pageNo, int pageSize, String sortBy, String sortDir) throws JsonProcessingException;
	Category getCategoryById(UUID id);
	Category updateCategory(CategoryDto categoryDto, UUID id);
	void deleteCategory(UUID id);
}
