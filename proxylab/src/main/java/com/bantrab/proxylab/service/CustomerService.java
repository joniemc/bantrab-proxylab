package com.bantrab.proxylab.service;

import java.util.List;

import com.bantrab.proxylab.entity.Customer;

public interface CustomerService {
	List<Customer> getAllCustomers();
	Customer getSingleCustomer(int customerId);
}
