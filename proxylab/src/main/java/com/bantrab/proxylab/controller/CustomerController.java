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

import com.bantrab.proxylab.entity.Customer;
import com.bantrab.proxylab.service.CustomerService;
import com.bantrab.proxylab.utils.GeneralParams;
import com.bantrab.proxylab.utils.GeneralResponse;

@RestController
@RequestMapping("/api/proxy/customers/v1")
public class CustomerController {
	public static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	private CustomerService customerService;
	
	@GetMapping("get/all")
	public ResponseEntity<GeneralResponse> getAllCustomers(){
		ResponseEntity<GeneralResponse> response=null;
		List<Customer> getAllCustomersResponse=null;
		try {
			getAllCustomersResponse = customerService.getAllCustomers();
			if(getAllCustomersResponse != null) {
				response = new ResponseEntity<>(new GeneralResponse(GeneralParams.SUCCESS_CODE,GeneralParams.SUCCESS_MSSG,getAllCustomersResponse),HttpStatus.OK);
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
	
	@GetMapping("get/single/{customerId}")
	public ResponseEntity<GeneralResponse> getSingleCustomer(@PathVariable("customerId") int customerId){
		ResponseEntity<GeneralResponse> response=null;
		Customer getSingleCustomerResponse=null;
		try {
			getSingleCustomerResponse = customerService.getSingleCustomer(customerId);
			if(getSingleCustomerResponse != null) {
				response = new ResponseEntity<>(new GeneralResponse(GeneralParams.SUCCESS_CODE,GeneralParams.SUCCESS_MSSG,getSingleCustomerResponse),HttpStatus.OK);
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
