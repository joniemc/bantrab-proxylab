package com.bantrab.proxylab.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class Address {
	private String city;
    private String street;
    private int number;
    private String zipcode;
    private Geolocation geolocation;
}
