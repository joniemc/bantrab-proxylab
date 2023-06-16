package com.bantrab.proxylab.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class Product {
	private int id;
    private String title;
    private double price;
    private String description;
    private String category;
    private String image;
    private Rating rating;
}
