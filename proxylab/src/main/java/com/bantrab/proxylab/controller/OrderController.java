package com.bantrab.proxylab.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bantrab.proxylab.entity.Order;
import com.bantrab.proxylab.entity.OrderProductDetail;
import com.bantrab.proxylab.pojos.PaymentResponse;
import com.bantrab.proxylab.service.OrderService;
import com.bantrab.proxylab.service.ProductService;
import com.bantrab.proxylab.utils.GeneralParams;
import com.bantrab.proxylab.utils.GeneralResponse;

@RestController
@RequestMapping("api/proxy/orders/v1")
public class OrderController {
	private static final Logger logger = LoggerFactory.getLogger(OrderController.class);
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("get/all")
	public ResponseEntity<GeneralResponse> getAllOrder(){
		ResponseEntity<GeneralResponse> response=null;
		List<Order> getAllOrderResponse=null;
		try {
			getAllOrderResponse = orderService.getAllOrders();
			if(getAllOrderResponse != null) {
				response = new ResponseEntity<>(new GeneralResponse(GeneralParams.SUCCESS_CODE,GeneralParams.SUCCESS_MSSG,getAllOrderResponse),HttpStatus.OK);
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
	
	@GetMapping("get/single/{orderId}")
	public ResponseEntity<GeneralResponse> getSingleOrder(@PathVariable("orderId") int orderId){
		ResponseEntity<GeneralResponse> response=null;
		Order getSingleOrderResponse=null;
		try {
			getSingleOrderResponse = orderService.getSingleOrder(orderId);
			if(getSingleOrderResponse != null) {
				response = new ResponseEntity<>(new GeneralResponse(GeneralParams.SUCCESS_CODE,GeneralParams.SUCCESS_MSSG,getSingleOrderResponse),HttpStatus.OK);
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
	
	@PostMapping("add")
	public ResponseEntity<GeneralResponse> addNewOrder(@RequestBody Order newOrder){
		ResponseEntity<GeneralResponse> response=null;
		Order addNewOrderResponse=null;
		try {
			addNewOrderResponse = orderService.addNewOrder(newOrder);
			if(addNewOrderResponse != null) {
				response = new ResponseEntity<>(new GeneralResponse(GeneralParams.SUCCESS_CODE,GeneralParams.SUCCESS_MSSG,addNewOrderResponse),HttpStatus.OK);
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
	
	@PostMapping("payment")
	public ResponseEntity<GeneralResponse> payOrder(@RequestBody Order newOrder){
		ResponseEntity<GeneralResponse> response=null;
		PaymentResponse paymentResponse=null;
		try {
			if(!newOrder.getProducts().isEmpty()) {
				double total = 0; 
				for(OrderProductDetail orderProductDetail: newOrder.getProducts()) {
					total += productService.getSingleProduct(orderProductDetail.getProductId()).getPrice()*orderProductDetail.getQuantity();
				}
				
				// Add Block Code for payment external api
				if(total>0) {
					paymentResponse = new PaymentResponse(newOrder,total,GeneralParams.STATUS_PAYMENT);
					response = new ResponseEntity<>(new GeneralResponse(GeneralParams.SUCCESS_CODE,GeneralParams.SUCCESS_MSSG,paymentResponse), HttpStatus.OK);
				}
				else {
					response = new ResponseEntity<>(new GeneralResponse(GeneralParams.SUCCESS_CODE,GeneralParams.STATUS_PAYMENT_MSSG01,null), HttpStatus.OK);
				}
				
			}
		}catch(Exception ex){
			response = new ResponseEntity<>(new GeneralResponse(GeneralParams.ERROR01_CODE,GeneralParams.ERROR01_MSSG,null), HttpStatus.BAD_REQUEST);
        	logger.info(ex.getMessage());
        	
		}
		return response;
	}
	
}
