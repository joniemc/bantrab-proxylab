package com.bantrab.proxylab.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class Geolocation {
	private String lat;
    private String lon;
}
