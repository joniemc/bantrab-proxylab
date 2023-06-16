package com.bantrab.proxylab.servicetests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.bantrab.proxylab.entity.Address;
import com.bantrab.proxylab.entity.Customer;
import com.bantrab.proxylab.entity.Geolocation;
import com.bantrab.proxylab.entity.Name;
import com.bantrab.proxylab.service.impl.CustomerServiceImpl;
import com.google.gson.Gson;

public class CustomerServiceImplTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(customerService, "baseUrl", "https://fakestoreapi.com");
        ReflectionTestUtils.setField(customerService, "getAllCustomers", "/users");
        ReflectionTestUtils.setField(customerService, "getSingleCustomers", "/users/%d");
        Gson gson = new Gson();
        ReflectionTestUtils.setField(customerService, "gson", gson);
    }
    
    @Test
    public void testGetSingleCustomer_Successful() {
        // Mock response from external service
        int customerId = 1;
        Customer customer = new Customer(1, "john@gmail.com","johnd","m38rmF$",new Name("john","doe"),new Address("kilcoole","new road",7682,"12926-3874",new Geolocation("-37.3159","null")),"1-570-236-7033");
        String jsonResponse = "{\r\n"
        		+ "    \"id\": 1,\r\n"
        		+ "    \"email\": \"john@gmail.com\",\r\n"
        		+ "    \"username\": \"johnd\",\r\n"
        		+ "    \"password\": \"m38rmF$\",\r\n"
        		+ "    \"name\": {\r\n"
        		+ "        \"firstname\": \"john\",\r\n"
        		+ "        \"lastname\": \"doe\"\r\n"
        		+ "    },\r\n"
        		+ "    \"address\": {\r\n"
        		+ "        \"city\": \"kilcoole\",\r\n"
        		+ "        \"street\": \"new road\",\r\n"
        		+ "        \"number\": 7682,\r\n"
        		+ "        \"zipcode\": \"12926-3874\",\r\n"
        		+ "        \"geolocation\": {\r\n"
        		+ "            \"lat\": \"-37.3159\",\r\n"
        		+ "            \"lon\": null\r\n"
        		+ "        }\r\n"
        		+ "    },\r\n"
        		+ "    \"phone\": \"1-570-236-7033\"\r\n"
        		+ "}";

        // Set up mock RestTemplate
        ResponseEntity<String> responseEntity = new ResponseEntity<>(jsonResponse, HttpStatus.OK);
        when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), eq(null), eq(String.class)))
                .thenReturn(responseEntity);

        // Perform the test
        Customer result = customerService.getSingleCustomer(customerId);

        // Verify the interaction with RestTemplate
        verify(restTemplate).exchange("https://fakestoreapi.com/users/1", HttpMethod.GET, null,String.class);

        // Verify the result
        assertNotNull(result);
        assertEquals(customerId, result.getId());
        assertEquals("john@gmail.com", result.getEmail());
        assertEquals("johnd", result.getUsername());
        assertEquals("m38rmF$", result.getPassword());
    }

    
}

