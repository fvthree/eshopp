package com.fvthree.eshop.orders;

import java.util.List;

import com.fvthree.eshop.users.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDto {
	
	private List<OrderItem> orderItems;
	
	private String shippingAddress1;
	
	private String shippingAddress2;
	
	private String city;
	
	private String zip;
	
	private String country;
	
	private String phone;
	
	private User user;
}
