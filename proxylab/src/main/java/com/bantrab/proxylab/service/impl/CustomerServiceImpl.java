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

import com.bantrab.proxylab.entity.Customer;
import com.bantrab.proxylab.service.CustomerService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
	private RestTemplate restTemplate;
	private Gson gson;
	
	@Value("${external.api.base.url}")
    private String baseUrl;
	
	@Value("${external.api.customers.get.all}")
    private String getAllCustomers;
	
	@Value("${external.api.customers.get.single}")
    private String getSingleCustomers;
	
	private @PostConstruct void init() {
		restTemplate = new RestTemplate();
		gson = new Gson();
	}
	
	@Override
	public List<Customer> getAllCustomers() {
		String appPath = baseUrl+getAllCustomers;
		List<Customer> response = null;
		try {
			HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            ResponseEntity<String> externalServiceResponse = restTemplate.exchange(appPath, HttpMethod.GET, null, String.class);
            
            if(externalServiceResponse.getStatusCode().is2xxSuccessful()) {
            	String json = externalServiceResponse.getBody();
            	response = gson.fromJson(json, new TypeToken<List<Customer>>() {}.getType());
            }
		}catch(Exception ex) {
			logger.info(ex.getMessage());
        	
		}
		return response;
	}

	@Override
	public Customer getSingleCustomer(int customerId) {
		String singleCustomerAddCustomerIdPath = String.format(getSingleCustomers, customerId);
		String appPath = baseUrl+singleCustomerAddCustomerIdPath;
		Customer response = null;
		try {
			HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            ResponseEntity<String> externalServiceResponse = restTemplate.exchange(appPath, HttpMethod.GET, null, String.class);
            
            if(externalServiceResponse.getStatusCode().is2xxSuccessful()) {
            	String json = externalServiceResponse.getBody();
            	response = gson.fromJson(json, Customer.class);
            }
		}catch(Exception ex) {
			logger.info(ex.getMessage());
        	
		}
		return response;
	}

}
