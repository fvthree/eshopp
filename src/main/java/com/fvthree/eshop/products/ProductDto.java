package com.fvthree.eshop.products;

import java.math.BigDecimal;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class ProductDto {
	
    private UUID product_id;
    
    @NotEmpty
    @Size(min = 5, message = "Product name should have at least 5 characters")
    private String name;

    @NotEmpty
    private String description;

    @NotEmpty
    private String richDescription;
    
    @NotEmpty
    private String image;

    @NotEmpty
    private String brand;

    private BigDecimal price;
    
    private UUID category_id;

    private Integer countInStock;

    private Double rating;
    
    private Integer numReviews;
   
    private Boolean isFeatured;
}
