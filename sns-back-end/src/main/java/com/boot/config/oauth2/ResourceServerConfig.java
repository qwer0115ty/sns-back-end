package com.boot.config.oauth2;

import java.util.Arrays;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.boot.model.oauth2.LoginUser;
import com.google.gson.Gson;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
	@Autowired
	@Qualifier("ssoFilter")
	private Filter ssoFilter;

	@Autowired
	private JwtTokenStore tokenStore;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	private Gson gson = new Gson();
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(HttpMethod.GET, "/**").permitAll()
			.antMatchers(HttpMethod.GET, "/api/**").authenticated()
			.antMatchers(HttpMethod.POST, "/api/user").permitAll()
			.antMatchers(HttpMethod.GET, "/api/user/validation/name").permitAll()
			.antMatchers(HttpMethod.POST, "/api/user/**/token/refresh").permitAll()
			.antMatchers(HttpMethod.GET, "/api/user/**/**").authenticated()
			.antMatchers(HttpMethod.GET, "/api/board/**", "/api/boards", "/api/user/**/boards", "/api/user/**").permitAll()
			.antMatchers("/WEB-INF/jsp/googleAuthSuccess.jsp", "/WEB-INF/jsp/signupGoogleAuthSuccess.jsp",
					"/static/**", "/templates/**", "/").permitAll()
			.anyRequest().authenticated().and().formLogin().disable()
			.addFilterBefore(ssoFilter, BasicAuthenticationFilter.class);
	}
	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore)
			.authenticationManager(authenticationManager());
	}
	
	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(Arrays.asList(new PreAuthenticatedAuthenticationProvider() {
			@Override
			public Authentication authenticate(Authentication authentication) throws AuthenticationException {
				OAuth2AuthenticationDetails details = (OAuth2AuthenticationDetails) authentication.getDetails();
				OAuth2AccessToken accessToken = tokenStore.readAccessToken(details.getTokenValue());
				
				if(accessToken.isExpired()) {
					throw new InvalidTokenException("token expire");
				}
				
				String t = gson.toJson(accessToken.getAdditionalInformation().get("user"));
				LoginUser su = gson.fromJson(t, LoginUser.class);
				su.setAuthoritiesByUserAuthorities();
				
				return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(su, null, su.getAuthorities()));
			}
		}, new AbstractUserDetailsAuthenticationProvider() {
			@Override
			protected void additionalAuthenticationChecks(UserDetails userDetails,
					UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
			}

			@Override
			protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
					throws AuthenticationException {
				return (LoginUser) authentication.getPrincipal();
			}
		}));
	}
}
