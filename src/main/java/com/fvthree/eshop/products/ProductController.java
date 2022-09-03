package com.fvthree.eshop.products;

import java.util.UUID;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
public class ProductController {
	
	@Autowired
	ProductService productService;
		

	@PostMapping("/product")
	public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDto productDto) {
		return new ResponseEntity<>(productService.save(productDto), HttpStatus.CREATED);
	}
	
    @GetMapping("/products")
    public ProductsResponse getAllProducts(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "dateCreated", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "desc", required = false) String sortDir) 
            		throws JsonProcessingException {
    	
        log.info(" size ::: " + pageSize + ", number ::: " + pageNo + ", sortBy ::: " + sortBy + ", sortDir ::: " + sortDir);
        
        return productService.getAllProduct(pageNo, pageSize, sortBy, sortDir);
    }
    
    @GetMapping(value = "/product/{id}")
    public ResponseEntity<?> getProductById(@PathVariable(name = "id") UUID id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }
    
    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(@Valid @RequestBody ProductDto productDto, 
    		@PathVariable(name = "id") UUID id) {

       return new ResponseEntity<>(productService.updateProduct(productDto, id), HttpStatus.OK);
    }
    
    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable(name = "id") UUID id) {

        productService.deleteProduct(id);

        return new ResponseEntity<>("Product entity deleted successfully.", HttpStatus.OK);
    }
	
}
