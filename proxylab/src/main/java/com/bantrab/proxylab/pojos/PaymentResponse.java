package com.bantrab.proxylab.pojos;

import com.bantrab.proxylab.entity.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class PaymentResponse {
	private Order order;
	private double total;
	private String status;
}
