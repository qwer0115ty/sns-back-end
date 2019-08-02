package com.boot.service;

import java.util.Map;

public interface HttpClientService {
	public <T>T sendPostRequest(Map<String, String> parameters, String url, Class<T> returnType) throws Exception;
}
