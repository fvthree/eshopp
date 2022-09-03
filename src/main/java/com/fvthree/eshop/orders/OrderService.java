package com.fvthree.eshop.orders;

import com.fasterxml.jackson.core.JsonProcessingException;

public interface OrderService {
	Order save(OrderDto OrderDto) throws JsonProcessingException;
	OrdersResponse getAllOrder(int pageNo, int pageSize, String sortBy, String sortDir);
	Order getOrderById(long id);
	Order updateOrder(OrderDto OrderDto, long id) throws JsonProcessingException;
	void deleteOrder(long id);
}
