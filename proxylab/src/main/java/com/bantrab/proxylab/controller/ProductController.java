package com.bantrab.proxylab.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bantrab.proxylab.entity.Product;
import com.bantrab.proxylab.service.ProductService;
import com.bantrab.proxylab.utils.GeneralParams;
import com.bantrab.proxylab.utils.GeneralResponse;

@RestController
@RequestMapping("/api/proxy/products/v1")
public class ProductController {
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("get/all")
	public ResponseEntity<GeneralResponse> getAllProducts(){
		ResponseEntity<GeneralResponse> response=null;
		List<Product> getAllProductResponse=null;
		try {
			getAllProductResponse = productService.getAllProducts();
			if(getAllProductResponse != null) {
				response = new ResponseEntity<>(new GeneralResponse(GeneralParams.SUCCESS_CODE,GeneralParams.SUCCESS_MSSG,getAllProductResponse),HttpStatus.OK);
			}
			else {
				response = new ResponseEntity<>(new GeneralResponse(GeneralParams.ERROR01_CODE,GeneralParams.ERROR01_MSSG,null), HttpStatus.BAD_REQUEST);
			}
		}catch(Exception ex){
			response = new ResponseEntity<>(new GeneralResponse(GeneralParams.ERROR01_CODE,GeneralParams.ERROR01_MSSG,null), HttpStatus.BAD_REQUEST);
        	logger.info(ex.getMessage());
        	
		}
		return response;
	}
	
	@GetMapping("get/single/{productId}")
	public ResponseEntity<GeneralResponse> getSingleProducts(@PathVariable("productId") int productId){
		ResponseEntity<GeneralResponse> response=null;
		Product getSingleProductResponse=null;
		try {
			getSingleProductResponse = productService.getSingleProduct(productId);
			if(getSingleProductResponse != null) {
				response = new ResponseEntity<>(new GeneralResponse(GeneralParams.SUCCESS_CODE,GeneralParams.SUCCESS_MSSG,getSingleProductResponse),HttpStatus.OK);
			}
			else {
				response = new ResponseEntity<>(new GeneralResponse(GeneralParams.ERROR01_CODE,GeneralParams.ERROR01_MSSG,null), HttpStatus.BAD_REQUEST);
			}
		}catch(Exception ex){
			response = new ResponseEntity<>(new GeneralResponse(GeneralParams.ERROR01_CODE,GeneralParams.ERROR01_MSSG,null), HttpStatus.BAD_REQUEST);
        	logger.info(ex.getMessage());
        	
		}
		return response;
	}

}
