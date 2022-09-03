package com.fvthree.eshop.categories;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fvthree.eshop.exceptions.APIException;
import com.fvthree.eshop.exceptions.ResourceNotFoundException;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class);
	
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public Category save(CategoryDto categoryDto) {
		if (categoryRepository.findByName(categoryDto.getName().trim()).isPresent()) {
			throw new APIException(HttpStatus.BAD_REQUEST, "Category already exists");
		}
		Category category = Category.builder()
				.color(categoryDto.getColor()).name(categoryDto.getName()).icon(categoryDto.getIcon()).image(categoryDto.getImage()).build();
		return categoryRepository.save(category);
	}

	@Override
	public CategoriesResponse getAllCategory(int pageNo, int pageSize, String sortBy, String sortDir) throws JsonProcessingException {
		
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
        Page<Category> categories = categoryRepository.findAll(pageable);
        
        List<Category> content = categories.getContent();
        
		CategoriesResponse categoryResponse = CategoriesResponse.builder()
				.content(content)
				.pageNo(categories.getNumber())
				.pageSize(categories.getSize())
				.totalElements(categories.getTotalElements())
				.totalPages(categories.getTotalPages())
				.last(categories.isLast())
				.build();
		
        return categoryResponse;
	}
	
	@Override
	public Category getCategoryById(UUID id) {
		
		LOGGER.info("[getCategoryById] category id ::: " + id);
		
		Category category = categoryRepository.findByCategoryId(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
		
		return category;
	}

	@Override
	public Category updateCategory(CategoryDto categoryDto, UUID id) {

		Category category = categoryRepository.findByCategoryId(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
		
		if (!categoryDto.getName().equals(category.getName())) {
			if (categoryRepository.findByName(categoryDto.getName()).isPresent()) {
				throw new APIException(HttpStatus.BAD_REQUEST, "Category already exists");
			}
		}
		
		category.setName(categoryDto.getName());
		category.setColor(categoryDto.getColor());
		category.setIcon(categoryDto.getIcon());
		
		return categoryRepository.save(category);
	}

	@Override
	public void deleteCategory(UUID id) {
		
		LOGGER.info("[deleteCategory]  id ::: " + id);
		
		Category category = categoryRepository.findByCategoryId(id)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
		
		categoryRepository.delete(category);
	}

}
