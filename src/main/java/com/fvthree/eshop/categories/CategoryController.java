package com.fvthree.eshop.categories;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api")
public class CategoryController {

	private CategoryService categoryService;
	
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@PostMapping("/category")
	public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryDto categoryDto) throws JsonProcessingException {
		return new ResponseEntity<>(categoryService.save(categoryDto), HttpStatus.CREATED);
	}
	
    @GetMapping("/categories")
    public CategoriesResponse getAllCategories(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "dateCreated", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir) 
            		throws JsonProcessingException {
    	
        log.info(" size ::: " + pageSize + ", number ::: " + pageNo + ", sortBy ::: " + sortBy + ", sortDir ::: " + sortDir);

        return categoryService.getAllCategory(pageNo, pageSize, sortBy, sortDir);
    }
    
    @GetMapping(value = "/category/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable(name = "id") UUID id) {
        return ResponseEntity.ok(categoryService.getCategoryById(id));
    }
    
    @PutMapping("/category/{id}")
    public ResponseEntity<?> updateCategory(@Valid @RequestBody CategoryDto categoryDto, 
    		@PathVariable(name = "id") UUID id) throws JsonProcessingException {

       return new ResponseEntity<>(categoryService.updateCategory(categoryDto, id), HttpStatus.OK);
    }
    
    @DeleteMapping("/category/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable(name = "id") UUID id){

        categoryService.deleteCategory(id);

        return new ResponseEntity<>("Category entity deleted successfully.", HttpStatus.OK);
    }
}
