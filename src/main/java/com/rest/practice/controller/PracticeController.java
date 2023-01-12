package com.rest.practice.controller;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@RestController
@RequestMapping("/practice")
public class PracticeController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	ProductRepository repository;
	
	@GetMapping("/products")
	public Object getProductList() throws JsonMappingException, JsonProcessingException
	{
		Gson obj=new Gson();
		HttpHeaders headers=new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity= new HttpEntity<String>(headers);
		Object response=restTemplate.exchange("http://localhost:9988/searchService/products", HttpMethod.GET, entity, Object.class).getBody();
		String responseString=obj.toJson(response);
		System.out.println(responseString);
		
		ObjectMapper mapper=new ObjectMapper();
		
		JSONArray array=new JSONArray(responseString);
		System.out.println(array);
		for(int i=0;i<array.length();i++)
			
		{
			JSONObject prodJson=array.getJSONObject(i);
			String prod= prodJson.toString();
			ProductDTO productToSave=mapper.readValue(prod, ProductDTO.class);
			repository.save(productToSave);
		}
		
		System.out.println(array.get(0));
		return response;
		
	}
	
	
	

}
