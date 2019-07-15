package com.boot.service.oauth2;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import com.boot.config.oauth2.client.CustomClientDetail;

@Service
public class ClientDetailsServiceImpl implements ClientDetailsService{
	@Autowired
	private CustomClientDetail client; 
	
	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		if (!clientId.equals(client.getClientId())) {
			throw new NoSuchClientException("No client with requested id: " + clientId);
		}
	    
		String scopes = client.getScopes();
        String grantTypes = client.getAuthorizedGrantTypes().stream().collect(Collectors.joining(","));
	    
	    BaseClientDetails base = new BaseClientDetails(client.getClientId(), null, scopes, grantTypes, null);
	    base.setClientSecret(client.getClientSecret());
	    base.setAccessTokenValiditySeconds(client.getAccessTokenValiditySeconds());
	    base.setRefreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds());
	    return base;
	}
}
