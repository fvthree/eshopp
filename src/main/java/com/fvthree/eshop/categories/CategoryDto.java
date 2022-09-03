package com.fvthree.eshop.categories;

import java.util.UUID;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
	
	private UUID category_id;
	
	@NotBlank
	private String name;
	
	private String color;
	
	private String icon;
	
	private String image;
}
