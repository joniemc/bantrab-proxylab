package com.bantrab.proxylab.service.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.bantrab.proxylab.pojos.LoginRequest;
import com.bantrab.proxylab.pojos.LoginResponse;
import com.bantrab.proxylab.service.SecurityLoginService;
import com.google.gson.Gson;

@Service
public class SecurityLoginServiceImpl implements SecurityLoginService{

	private static final Logger logger = LoggerFactory.getLogger(SecurityLoginServiceImpl.class);
	private RestTemplate restTemplate;
	private Gson gson;
	
	@Value("${external.api.base.url}")
    private String baseUrl;
	
	@Value("${external.api.auth.path}")
    private String loginPath;
	
	
	
	private @PostConstruct void init() {
		restTemplate = new RestTemplate();
		gson = new Gson();
	}
	
	@Override
	public LoginResponse login(LoginRequest loginRequest) {
		String appPath = baseUrl+loginPath;
		LoginResponse response = null;
		try {
			HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            HttpEntity<LoginRequest> requestEntity = new HttpEntity<>(loginRequest, headers);
            
            String json = restTemplate.exchange(
            		appPath,
                    HttpMethod.POST,
                    requestEntity,
                    String.class
            ).getBody();
            
            response = gson.fromJson(json, LoginResponse.class);
		}catch(Exception ex) {
			logger.info(ex.getMessage());
		}
		return response;
	}
	
	

}
