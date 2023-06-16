package com.bantrab.proxylab.entity;

import java.util.ArrayList;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class Order {
	private int id;
	private int userId;
	private Date date;
	private ArrayList<OrderProductDetail> products;
}
