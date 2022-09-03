package com.fvthree.eshop.products;

import java.util.UUID;

public interface ProductService {
	Product save(ProductDto productDto); 
	ProductsResponse getAllProduct(int pageNo, int pageSize, String sortBy, String sortDir) ;
	Product getProductById(UUID id);
	Product updateProduct(ProductDto productDto, UUID id) ;
	void deleteProduct(UUID id);
}
