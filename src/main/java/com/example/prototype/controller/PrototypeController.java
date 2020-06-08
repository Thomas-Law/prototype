package com.example.prototype.controller;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.prototype.model.OrderLineItem;
import com.example.prototype.model.Product;
import com.example.prototype.model.Token;
import com.example.prototype.service.DataSourceService;

@CrossOrigin
@RestController
public class PrototypeController {
	
	private Logger logger = LogManager.getLogger();
	
	@Autowired
	private DataSourceService dataSourceService;
	
	@PutMapping(value = "/order-line-item/{orderLineItemId}")
	public OrderLineItem updateOrderLineItem(
			@PathVariable String orderLineItemId, 
			@RequestBody OrderLineItem orderLineItem) {
		
		String fulfillmentState = orderLineItem.getFulfillmentState();
		
		try {
			URI dbUri = new URI(dataSourceService.getDbUrl());
			String username = dbUri.getUserInfo().split(":")[0];
			String password = dbUri.getUserInfo().split(":")[1];
			Properties props = new Properties();
			props.setProperty("user", username);
			props.setProperty("password" ,password);
			props.setProperty("ssl", "true");
			String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ":" + dbUri.getPort() + dbUri.getPath();
			Connection conn = DriverManager.getConnection(dbUrl, props);
			Statement stmt = conn.createStatement();
			String sql = 
					"begin;" +
					"set transaction read write;" + 
					"UPDATE salesforce.Ruby_Order_Item__c SET Fulfillment_State__c = '" + fulfillmentState + "' WHERE sfid = '" + orderLineItemId + "';" +
					"commit;";
			stmt.executeUpdate(sql);
		} catch (URISyntaxException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return orderLineItem;
	}
	
	/*
	@PostMapping(value = "/order-line-item")
	@ResponseBody
	public String updateOrderLineItemFulfillmentState() {
		return dataSourceService.getDbUrl();
	}
	*/

	@GetMapping(value = "/db")
	public String db() {
		
		String result = null;
		
		/*try (Connection connection = dataSourceService.dataSource().getConnection()) {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)");
			stmt.executeUpdate("INSERT INTO ticks VALUES (now())");
			ResultSet rs = stmt.executeQuery("SELECT tick FROM ticks");

			ArrayList<String> output = new ArrayList<String>();
			while (rs.next()) {
				output.add("Read from DB: " + rs.getTimestamp("tick"));
			}
			return "db";
		} catch (Exception e) {
			return e.toString();
		}*/
		
		// postgres://kjctmxxlfahqoa:ca4cd3c7617788c65164696ab918e41e7ebebfc6bcd0797d166638aa117128e0@ec2-35-169-254-43.compute-1.amazonaws.com:5432/d587lfufmabkpp
		
		String url = "jdbc:postgresql://ec2-35-169-254-43.compute-1.amazonaws.com:5432/d587lfufmabkpp";
		Properties props = new Properties();
		props.setProperty("user","kjctmxxlfahqoa");
		props.setProperty("password","ca4cd3c7617788c65164696ab918e41e7ebebfc6bcd0797d166638aa117128e0");
		props.setProperty("ssl","true");
		try {
			Connection conn = DriverManager.getConnection(url, props);
			Statement stmt = conn.createStatement();
			// String sql = "select to_char(now(), 'day') AS TEST";
			String sql = "SELECT Name FROM salesforce.Ruby_Offer_Product__c WHERE sfid = 'a092x000000WvcOAAS'";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				// String test = rs.getString("TEST");
				String test = rs.getString("Name");
				result = test;
			}
			// sql = "UPDATE salesforce.Ruby_Offer_Product__c SET Name = 'ihpone Thomas Test' WHERE sfid = 'a092x000000WvcOAAS'";
			sql = "UPDATE salesforce.Ruby_Order_Item__c SET Name = 'hello3' WHERE sfid = 'a0B2x000000QeS7EAK'";
			stmt.executeUpdate(sql);
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			result = "fail";
			e.printStackTrace();
		}
		
		return result;		
		
	}
	
	
	@GetMapping(value = "/token")
	public Token getToken(
			@RequestParam(required = true) String tokenCode,
			@RequestParam(required = true) String tokenRedirectUrl
	) {
		
		SimpleClientHttpRequestFactory clientHttpReq = new SimpleClientHttpRequestFactory();
		// clientHttpReq.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.pccw.com", 8080)));
		 
		RestTemplate restTemplate = new RestTemplate(clientHttpReq);
		
		String tokenUrl = "https://login.salesforce.com/services/oauth2/token";
		
		HttpHeaders tokenHeaders = new HttpHeaders();
		
		MultiValueMap<String, String> tokenMap = new LinkedMultiValueMap<String, String>();
		tokenMap.add("grant_type", "authorization_code");
		// tokenMap.add("code", "aPrx9pB8PA1X2QMcNNiey19tRfhWcrmkfGVj.zyw.aTHwxBN_sj5p40UaI95StGx72ozyNsJvQ==");
		tokenMap.add("code", tokenCode);
		tokenMap.add("client_id", "3MVG97quAmFZJfVwJgZbh1OWH_UDBxCz58VScNtq..AL9S3RzFfa1JtcvWp6m6MxEY2CuH9Rvea3qRWjwq2Zd");
		tokenMap.add("client_secret", "D7275AB547E4B25FCE59F680734A786A702D9D8AA9B44C212B12826CCCE61874");
		tokenMap.add("redirect_uri", tokenRedirectUrl);

		HttpEntity<MultiValueMap<String, String>> tokenHttpentity = new HttpEntity<MultiValueMap<String, String>>(tokenMap, tokenHeaders);

		// ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
		
		Token token = restTemplate.postForObject(tokenUrl, tokenHttpentity, Token.class);
		
		return token;
		
	}
	
	@GetMapping(value = "/product")
	public Product getProduct(
			@RequestParam(required = true) String productId,
			@RequestParam(required = true) String tokenInstanceUrl,
			@RequestHeader(name = "Authorization") String authorization
	) {
		
		SimpleClientHttpRequestFactory clientHttpReq = new SimpleClientHttpRequestFactory();
		// clientHttpReq.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.pccw.com", 8080)));
		RestTemplate productRestTemplate = new RestTemplate(clientHttpReq);
		// RestTemplate productRestTemplate = new RestTemplate();
		String productUrl = tokenInstanceUrl + "/services/data/v48.0/sobjects/Ruby_Product__c/" + productId;
		
		HttpHeaders productHeaders = new HttpHeaders();        
        productHeaders.setAccessControlAllowOrigin("*");
		productHeaders.set("Authorization", authorization);
		HttpEntity<String> productHttpEntity = new HttpEntity<String>("parameters", productHeaders);
		ResponseEntity<Product> productResponseEntity = productRestTemplate.exchange(productUrl, HttpMethod.GET, productHttpEntity, Product.class);
		
		return productResponseEntity.getBody();
		
	}
	
	/*
	@GetMapping(value = "/product")
	public Product getProduct(
			@RequestParam(required = true) String productId,
			@RequestParam(required = true) String tokenCode
	) {
		
		SimpleClientHttpRequestFactory clientHttpReq = new SimpleClientHttpRequestFactory();
		// clientHttpReq.setProxy(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("proxy.pccw.com", 8080)));
		
		RestTemplate tokenRestTemplate = new RestTemplate(clientHttpReq);
		// RestTemplate tokenRestTemplate = new RestTemplate();
		// String tokenUrl = "https://login.salesforce.com/services/oauth2/token?grant_type=password&client_id=3MVG97quAmFZJfVwJgZbh1OWH_UDBxCz58VScNtq..AL9S3RzFfa1JtcvWp6m6MxEY2CuH9Rvea3qRWjwq2Zd&client_secret=D7275AB547E4B25FCE59F680734A786A702D9D8AA9B44C212B12826CCCE61874&username=marco.wh.ng@pccw.com&password=Abc@12345";
		// String tokenUrl = "https://login.salesforce.com/services/oauth2/token?grant_type=authorization_code&code=aPrx9pB8PA1X2QMcNNiey19tRfq1eY50P69TYUyFFHzKXV_ivlj7uDZwjhaAjRLfDoyTGkUoCg==&client_id=3MVG97quAmFZJfVwJgZbh1OWH_UDBxCz58VScNtq..AL9S3RzFfa1JtcvWp6m6MxEY2CuH9Rvea3qRWjwq2Zd&client_secret=D7275AB547E4B25FCE59F680734A786A702D9D8AA9B44C212B12826CCCE61874&redirect_uri=https://ruby-demo-gdcn.herokuapp.com";
		
		String tokenUrl = "https://login.salesforce.com/services/oauth2/token";
		
		HttpHeaders tokenHeaders = new HttpHeaders();
		
		MultiValueMap<String, String> tokenMap = new LinkedMultiValueMap<String, String>();
		tokenMap.add("grant_type", "authorization_code");
		// tokenMap.add("code", "aPrx9pB8PA1X2QMcNNiey19tRfhWcrmkfGVj.zyw.aTHwxBN_sj5p40UaI95StGx72ozyNsJvQ==");
		tokenMap.add("code", tokenCode);
		tokenMap.add("client_id", "3MVG97quAmFZJfVwJgZbh1OWH_UDBxCz58VScNtq..AL9S3RzFfa1JtcvWp6m6MxEY2CuH9Rvea3qRWjwq2Zd");
		tokenMap.add("client_secret", "D7275AB547E4B25FCE59F680734A786A702D9D8AA9B44C212B12826CCCE61874");
		tokenMap.add("redirect_uri", "https://ruby-demo-gdcn.herokuapp.com");

		HttpEntity<MultiValueMap<String, String>> tokenHttpentity = new HttpEntity<MultiValueMap<String, String>>(tokenMap, tokenHeaders);

		// ResponseEntity<String> response = restTemplate.postForEntity( url, request , String.class );
		
		Token token = tokenRestTemplate.postForObject(tokenUrl, tokenHttpentity, Token.class);
		
		RestTemplate productRestTemplate = new RestTemplate(clientHttpReq);
		// RestTemplate productRestTemplate = new RestTemplate();
		String productUrl = token.getInstance_url() + "/services/data/v48.0/sobjects/Ruby_Product__c/" + productId;
		
		HttpHeaders productHeaders = new HttpHeaders();        
        productHeaders.setAccessControlAllowOrigin("*");
		productHeaders.set("Authorization", token.getToken_type() + " " + token.getAccess_token());
		HttpEntity<String> productHttpEntity = new HttpEntity<String>("parameters", productHeaders);
		ResponseEntity<Product> productResponseEntity = productRestTemplate.exchange(productUrl, HttpMethod.GET, productHttpEntity, Product.class);
		
		return productResponseEntity.getBody();
		
	}
	*/

}
