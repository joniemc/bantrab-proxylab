package com.bantrab.proxylab.service;

import java.util.List;

import com.bantrab.proxylab.entity.Order;

public interface OrderService {
	List<Order> getAllOrders();
	Order getSingleOrder(int orderId);
	Order addNewOrder(Order newOrder);
}
