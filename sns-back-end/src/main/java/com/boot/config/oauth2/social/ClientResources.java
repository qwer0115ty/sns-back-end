package com.boot.config.oauth2.social;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;

import lombok.Getter;

@Getter
public class ClientResources {
	@NestedConfigurationProperty
	private AuthorizationCodeResourceDetails client = new AuthorizationCodeResourceDetails();
	@NestedConfigurationProperty
	private ResourceServerProperties resource = new ResourceServerProperties();
}
