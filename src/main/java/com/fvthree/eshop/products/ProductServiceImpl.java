package com.fvthree.eshop.products;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.fvthree.eshop.categories.Category;
import com.fvthree.eshop.categories.CategoryRepository;
import com.fvthree.eshop.exceptions.ResourceNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public Product save(ProductDto productDto) {
		
		Category c = categoryRepository.findByCategoryId(productDto.getCategory_id())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", productDto.getCategory_id()));
				
		Product product = Product.builder().brand(productDto.getBrand()).category(c)
				.countInStock(productDto.getCountInStock())
				.description(productDto.getDescription())
				.image(productDto.getImage())
				.isFeatured(false)
				.name(productDto.getName())
				.numReviews(productDto.getNumReviews())
				.price(productDto.getPrice())
				.rating(0.0)
				.richDescription(productDto.getRichDescription())
				.build();
		
		return productRepository.save(product);
	}

	@Override
	public ProductsResponse getAllProduct(int pageNo, int pageSize, String sortBy, String sortDir) {
		
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		
        Page<Product> products = productRepository.findAll(pageable);
        
        List<Product> listOfProducts = products.getContent();
   
		ProductsResponse productResponse = ProductsResponse.builder()
				.content(listOfProducts)
				.pageNo(products.getNumber())
				.pageSize(products.getSize())
				.totalElements(products.getTotalElements())
				.totalPages(products.getTotalPages())
				.last(products.isLast())
				.build();
		
        return productResponse;
	}

	@Override
	public Product getProductById(UUID id) {
		log.info("[getProductById] id ::: " + id );
		Product product = productRepository.findByProductId(id).orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
		return product;
	}

	@Override
	public Product updateProduct(ProductDto productDto, UUID id) {
		
		Product product = productRepository.findByProductId(id)
				.orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
		Category category = categoryRepository.findByCategoryId(productDto.getCategory_id())
				.orElseThrow(() -> new ResourceNotFoundException("Category", "id", productDto.getCategory_id()));
		
		product.setName(productDto.getName());
		product.setBrand(productDto.getBrand());
		product.setCategory(category);
		product.setCountInStock(productDto.getCountInStock());
		product.setDescription(productDto.getDescription());
		product.setRichDescription(productDto.getRichDescription());
		product.setImage(productDto.getImage());
		product.setPrice(productDto.getPrice());
		product.setCountInStock(productDto.getCountInStock());
		product.setRating(productDto.getRating());
		product.setNumReviews(productDto.getNumReviews());
		product.setIsFeatured(productDto.getIsFeatured());
		
		Product p = productRepository.save(product);
		return p;
	}

	@Override
	public void deleteProduct(UUID id) {
		
		log.info("[deleteProduct] id ::: " + id);
		
		Product product = productRepository.findByProductId(id)
				.orElseThrow(() -> new ResourceNotFoundException("Prodct", "id", id));
		
		productRepository.delete(product);
	}
	
}
