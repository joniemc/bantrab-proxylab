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

import com.bantrab.proxylab.entity.Product;
import com.bantrab.proxylab.service.ProductService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Service
public class ProductServiceImpl implements ProductService{
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	private RestTemplate restTemplate;
	private Gson gson;
	
	@Value("${external.api.base.url}")
    private String baseUrl;
	
	@Value("${external.api.products.get.all}")
    private String getAllProducts;
	
	@Value("${external.api.products.get.single}")
    private String getSingleProduct;
	
	private @PostConstruct void init() {
		restTemplate = new RestTemplate();
		gson = new Gson();
	}
	
	@Override
	public List<Product> getAllProducts() {
		String appPath = baseUrl+getAllProducts;
		List<Product> response = null;
		try {
			HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            ResponseEntity<String> externalServiceResponse = restTemplate.exchange(appPath, HttpMethod.GET, null, String.class);
            
            if(externalServiceResponse.getStatusCode().is2xxSuccessful()) {
            	String json = externalServiceResponse.getBody();
            	response = gson.fromJson(json, new TypeToken<List<Product>>() {}.getType());
            }
		}catch(Exception ex) {
			logger.info(ex.getMessage());
        	
		}
		return response;
	}
	
	

	@Override
	public Product getSingleProduct(int productId) {
		String singleProductAddProductIdPath = String.format(getSingleProduct, productId);
		String appPath = baseUrl+singleProductAddProductIdPath;
		Product response = null;
		try {
			HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            ResponseEntity<String> externalServiceResponse = restTemplate.exchange(appPath, HttpMethod.GET, null, String.class);
            
            if(externalServiceResponse.getStatusCode().is2xxSuccessful()) {
            	String json = externalServiceResponse.getBody();
            	response = gson.fromJson(json, Product.class);
            }
		}catch(Exception ex) {
			logger.info(ex.getMessage());
        	
		}
		return response;
	}
}
