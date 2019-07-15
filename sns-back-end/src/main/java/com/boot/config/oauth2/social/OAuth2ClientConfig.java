package com.boot.config.oauth2.social;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientAuthenticationProcessingFilter;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.web.filter.CompositeFilter;

import com.boot.service.oauth2.AuthService;


@EnableOAuth2Client
@Configuration
public class OAuth2ClientConfig {
	@Autowired
	private OAuth2ClientContext oauth2ClientContext;

	@Autowired
	private AuthService socialAuthService;
	
	@Bean("googleClient")
	@ConfigurationProperties("google")
	public ClientResources google() {
		return new ClientResources();
	}
	
	@Bean
	public FilterRegistrationBean oauth2ClientFilterRegistration(OAuth2ClientContextFilter filter) {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(filter);
		registration.setOrder(-100);
		return registration;
	}
	
	@Bean("ssoFilter")
	public Filter ssoFilter() {
		List<Filter> filters = new ArrayList<>();
		filters.add(ssoFilter(google(), "/api/login/google"));

		CompositeFilter filter = new CompositeFilter();
		filter.setFilters(filters);

		return filter;
	}
	
	private Filter ssoFilter(ClientResources client, String processPath) {
		OAuth2ClientAuthenticationProcessingFilter filter = new OAuth2ClientAuthenticationProcessingFilter(processPath);
		filter.setRestTemplate(new OAuth2RestTemplate(client.getClient(), oauth2ClientContext));
		filter.setTokenServices(new UserInfoTokenServices(client.getResource().getUserInfoUri()
				, client.getClient().getClientId()));
		filter.setAuthenticationSuccessHandler(new OAuth2SuccessHandler(socialAuthService));
		return filter;
	}
}
