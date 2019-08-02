package com.boot.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class HttpClientServiceImpl implements HttpClientService {
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public <T>T sendPostRequest(Map<String, String> parameters, String url, Class<T> returnType) throws Exception {
		HttpPost post = new HttpPost(url);
		
		List<NameValuePair> urlParameters = new ArrayList<>();
		parameters.entrySet().forEach(entry ->{
			urlParameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
		});
		
		post.setEntity(new UrlEncodedFormEntity(urlParameters));
		HttpClient client = HttpClientBuilder.create().build();
		HttpResponse response = client.execute(post);
		
		return getClassFromResponse(response, returnType);
	}
	
	private <T>T getClassFromResponse(HttpResponse response, Class<T> type) throws Exception {
		Gson gson = new Gson();
		String jsonString = EntityUtils.toString(response.getEntity());
		@SuppressWarnings("unchecked")
		Map<String, String> hm = gson.fromJson(jsonString, HashMap.class);
		return modelMapper.map(hm, type);
	}
}
