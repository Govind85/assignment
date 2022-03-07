package com.banking.assignment.security.jwt;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryTokenHolder {
	
	private ConcurrentHashMap<String, String> tokenHolder;

	public InMemoryTokenHolder() {
		tokenHolder = new ConcurrentHashMap<>();
	}

	public void putToken(String key, String value) {
		tokenHolder.put(key, value);
	}

	public void removeTokenByKey(String key) {
		tokenHolder.remove(key);
	}

	public String getTokenByUserName(String key) {
		return tokenHolder.get(key);
	}

}