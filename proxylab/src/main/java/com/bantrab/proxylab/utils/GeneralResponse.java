package com.bantrab.proxylab.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
public class GeneralResponse {
	private int status;
	private String message;
	private Object data;
}
