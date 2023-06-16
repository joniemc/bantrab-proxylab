package com.bantrab.proxylab.service;

import com.bantrab.proxylab.pojos.LoginRequest;
import com.bantrab.proxylab.pojos.LoginResponse;

public interface SecurityLoginService {
	LoginResponse login(LoginRequest loginRequest);
}
