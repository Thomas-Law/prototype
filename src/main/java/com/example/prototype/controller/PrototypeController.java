package com.example.prototype.controller;

import java.net.InetSocketAddress;
import java.net.Proxy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.prototype.model.Product;
import com.example.prototype.model.Token;

@RestController
public class PrototypeController {
	
	private Logger logger = LogManager.getLogger();
	
	@GetMapping(value = "/token")
	public Token getToken() {
		
		Token token = new Token();
		
		SimpleClientHttpRequestFactory clientHttpReq = new SimpleClientHttpRequestFactory();
		clientHttpReq.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.pccw.com", 8080)));
		 
		// RestTemplate restTemplate = new RestTemplate(clientHttpReq);
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://login.salesforce.com/services/oauth2/token?grant_type=password&client_id=3MVG9xB_D1giir9qtfYCSW8G3MH6tPwp0bgH4gfZfVA9EWFMcbZh4aci9d9xXBoeXp04J8KCS5J37l2QAN0p9&client_secret=3AE0E43926AD17AAB3304231FD032C38AB47370E3C8D81C5F32866E09463E99C&username=thomas.kc.law@resourceful-fox-v8by8y.com&password=Pccw20200604";
		token = restTemplate.postForObject(url, null, Token.class);
		
		return token;
		
	}
	
	@GetMapping(value = "/product")
	public Product getProduct(@RequestParam(required = true) String productId) {
		
		SimpleClientHttpRequestFactory clientHttpReq = new SimpleClientHttpRequestFactory();
		clientHttpReq.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.pccw.com", 8080)));
		
		// RestTemplate tokenRestTemplate = new RestTemplate(clientHttpReq);
		RestTemplate tokenRestTemplate = new RestTemplate();
		String tokenUrl = "https://login.salesforce.com/services/oauth2/token?grant_type=password&client_id=3MVG97quAmFZJfVwJgZbh1OWH_UDBxCz58VScNtq..AL9S3RzFfa1JtcvWp6m6MxEY2CuH9Rvea3qRWjwq2Zd&client_secret=D7275AB547E4B25FCE59F680734A786A702D9D8AA9B44C212B12826CCCE61874&username=marco.wh.ng@pccw.com&password=Abc@12345";
		Token token = tokenRestTemplate.postForObject(tokenUrl, null, Token.class);
		/*
		logger.info("token.getAccess_token(): " + token.getAccess_token());
		logger.info("token.getInstance_url(): " + token.getInstance_url());
		logger.info("token.getId(): " + token.getId());
		logger.info("token.getToken_type(): " + token.getToken_type());
		logger.info("token.getIssued_at(): " + token.getIssued_at());
		logger.info("token.getSignature(): " + token.getSignature());
		*/
		// RestTemplate productRestTemplate = new RestTemplate(clientHttpReq);
		RestTemplate productRestTemplate = new RestTemplate();
		String productUrl = token.getInstance_url() + "/services/data/v48.0/sobjects/Ruby_Product__c/" + productId;
		
		HttpHeaders productHeaders = new HttpHeaders();
		productHeaders.set("Authorization", token.getToken_type() + " " + token.getAccess_token());
		HttpEntity<String> productHttpEntity = new HttpEntity<String>("parameters", productHeaders);
		ResponseEntity<Product> productResponseEntity = productRestTemplate.exchange(productUrl, HttpMethod.GET, productHttpEntity, Product.class);
		
		return productResponseEntity.getBody();
		
	}

}
