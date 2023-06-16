package com.bantrab.proxylab.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class Customer {
	private int id;
    private String email;
    private String username;
    private String password;
    private Name name;
    private Address address;
    private String phone;
}
