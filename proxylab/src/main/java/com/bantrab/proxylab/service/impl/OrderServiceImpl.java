package com.bantrab.proxylab.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bantrab.proxylab.entity.Order;
import com.bantrab.proxylab.service.OrderService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.HttpEntity;


@Service
public class OrderServiceImpl implements OrderService{

	private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	private RestTemplate restTemplate;
	private Gson gson;
	
	@Value("${external.api.base.url}")
    private String baseUrl;
	
	@Value("${external.api.orders.path}")
    private String ordersPath;
	
	@Value("${external.api.orders.get.single}")
    private String getSingleOrderPath;
	
	private @PostConstruct void init() {
		restTemplate = new RestTemplate();
		gson = new Gson();
	}
	
	@Override
	public List<Order> getAllOrders() {
		String appPath = baseUrl+ordersPath;
		List<Order> response = null;
		try {
			HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            ResponseEntity<String> externalServiceResponse = restTemplate.exchange(appPath, HttpMethod.GET, null, String.class);
            
            if(externalServiceResponse.getStatusCode().is2xxSuccessful()) {
            	String json = externalServiceResponse.getBody();
            	response = gson.fromJson(json, new TypeToken<List<Order>>() {}.getType());
            }
		}catch(Exception ex) {
			logger.info(ex.getMessage());
        	
		}
		return response;
	}

	@Override
	public Order getSingleOrder(int orderId) {
		String singleCartsAddOrderIdPath = String.format(getSingleOrderPath, orderId);
		String appPath = baseUrl+singleCartsAddOrderIdPath;
		Order response = null;
		try {
			HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            ResponseEntity<String> externalServiceResponse = restTemplate.exchange(appPath, HttpMethod.GET, null, String.class);
            
            if(externalServiceResponse.getStatusCode().is2xxSuccessful()) {
            	String json = externalServiceResponse.getBody();
            	response = gson.fromJson(json, Order.class);
            }
		}catch(Exception ex) {
			logger.info(ex.getMessage());
        	
		}
		return response;
	}

	@Override
	public Order addNewOrder(Order newOrder) {
		String appPath = baseUrl+ordersPath;
		Order response = null;
		try {
			HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            String jsonRequest = gson.toJson(newOrder);
            HttpEntity<String> requestEntity = new HttpEntity<>(jsonRequest, headers);
            
            ResponseEntity<String> externalServiceResponse = restTemplate.exchange(appPath, HttpMethod.POST, requestEntity, String.class);
            
            if(externalServiceResponse.getStatusCode().is2xxSuccessful()) {
            	String json = externalServiceResponse.getBody();
            	response = gson.fromJson(json, Order.class);
            }
		}catch(Exception ex) {
			logger.info(ex.getMessage());
        	
		}
		return response;
	}

}
