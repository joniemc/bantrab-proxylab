package com.bantrab.proxylab.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bantrab.proxylab.pojos.LoginRequest;
import com.bantrab.proxylab.pojos.LoginResponse;
import com.bantrab.proxylab.service.impl.SecurityLoginServiceImpl;
import com.bantrab.proxylab.utils.GeneralParams;
import com.bantrab.proxylab.utils.GeneralResponse;

@RestController
@RequestMapping("/api/proxy/security/v1")
public class SecurityLoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(SecurityLoginController.class);
	
	@Autowired
	private SecurityLoginServiceImpl securityLoginService;
	
    @PostMapping("/login")
    public ResponseEntity<GeneralResponse> login(@RequestBody LoginRequest loginRequest) {
    	
        ResponseEntity<GeneralResponse> response = null;
        LoginResponse loginResponse = null;
        try {
        	loginResponse = securityLoginService.login(loginRequest);
        	if(loginResponse!=null) 
        	{
                response = new ResponseEntity<>(new GeneralResponse(GeneralParams.SUCCESS_CODE,GeneralParams.SUCCESS_MSSG,loginResponse), HttpStatus.OK);
        	}
        	else {
        		response = new ResponseEntity<>(new GeneralResponse(GeneralParams.ERROR02_CODE,GeneralParams.ERROR02_MSSG,null), HttpStatus.UNAUTHORIZED);
        	}
        }catch(Exception ex) {
        	response = new ResponseEntity<>(new GeneralResponse(GeneralParams.ERROR01_CODE,GeneralParams.ERROR01_MSSG,null), HttpStatus.BAD_REQUEST);
        	logger.info(ex.getMessage());
        }
        
        return response;
    }
}
