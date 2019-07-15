package com.boot.config.oauth2.client;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties("security.oauth2.client")
public class CustomClientDetail {
	private String clientId;
    private String clientSecret;
    private List<String> scope = new ArrayList<String>();
    private List<String> authorizedGrantTypes = new ArrayList<String>();
    private Integer accessTokenValiditySeconds;
    private Integer refreshTokenValiditySeconds;
    
    public String getScopes () {
    	return this.scope.stream().collect(Collectors.joining(","));
    }
}
