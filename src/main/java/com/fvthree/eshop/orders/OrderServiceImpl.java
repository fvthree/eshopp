package com.fvthree.eshop.orders;

import java.math.BigDecimal;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fvthree.eshop.exceptions.ResourceNotFoundException;
import com.fvthree.eshop.users.User;
import com.fvthree.eshop.users.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private UserRepository userRepository;


	@Override
	public Order save(OrderDto orderDto) throws JsonProcessingException {
		
		BigDecimal totalPrice = BigDecimal.valueOf(orderDto.getOrderItems().stream()
				.map(order -> order.getProduct())
				.mapToDouble(product -> product.getPrice().doubleValue())
				.sum());
				

		User user = userRepository.findByUserId(orderDto.getUser().getUserId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", orderDto.getUser().getUserId()));

		Order order = Order.builder()
				.user(user)
				.orderItem(new HashSet<>(orderDto.getOrderItems()))
				.shippingAddress1(orderDto.getShippingAddress1())
				.shippingAddress2(orderDto.getShippingAddress2())
				.status("Pending")
				.totalPrice(totalPrice)
				.city(orderDto.getCity())
				.zip(orderDto.getZip())
				.country(orderDto.getCountry())
				.phone(orderDto.getPhone())
				.build();

		Order o = orderRepository.save(order);
		return o;
	}

	@Override
	public OrdersResponse getAllOrder(int pageNo, int pageSize, String sortBy, String sortDir) {
		return null;
	}

	@Override
	public Order getOrderById(long id) {
		return null;
	}

	@Override
	public Order updateOrder(OrderDto OrderDto, long id) throws JsonProcessingException {
		return null;
	}

	@Override
	public void deleteOrder(long id) {

	}

}
