package com.boot.service.oauth2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Service;

import com.boot.config.oauth2.client.CustomClientDetail;
import com.boot.jpa.LoginUserRepository;
import com.boot.jpa.user.UserAuthorityRepository;
import com.boot.jpa.user.UserRoleRepository;
import com.boot.jpa.user.UserSocialRepository;
import com.boot.model.oauth2.Authority;
import com.boot.model.oauth2.GoogleUser;
import com.boot.model.oauth2.LoginUser;
import com.boot.model.user.UserSocial;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@Service
public class AuthServiceImpl implements AuthService {
	@Autowired
	private AuthorizationServerTokenServices authTokenServices;
	
	@Autowired
	private LoginUserRepository loginUserRepository;

	@Autowired
	private UserSocialRepository userSocialRepository;

	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Autowired
	private UserAuthorityRepository userAuthorityRepository;
	
	@Autowired
	private CustomClientDetail client;

	private Gson gson = new Gson();
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public GoogleUser getGoogleUserByAuthentication(Authentication authentication) throws Exception {
		OAuth2Authentication oauth2Authentication = (OAuth2Authentication) authentication;
		String details = gson.toJson(oauth2Authentication.getUserAuthentication().getDetails());
		
		return objectMapper.readValue(details, GoogleUser.class);
	}

	@Override
	public LoginUser getLoginUserBySub(String sub) {
		UserSocial us = userSocialRepository.findByIdAndIsLinked(sub, true);

		if (us != null) {
			LoginUser lu = loginUserRepository.findBySeqAndStatus(us.getUserSeq(), true);
			lu.setAuthorities(getAuthorities(lu.getSeq()));
			return lu;
		} else {
			return null;
		}
	}
	
	@Override
	public LoginUser getLoginUserByUserSeq(int userSeq) {
		LoginUser lu = loginUserRepository.findBySeqAndStatus(userSeq, true);
		lu.setAuthorities(getAuthorities(lu.getSeq()));
		return  lu;
	}
	
	private List<Authority> getAuthorities (int userSeq) {
		List<Authority> authorities = new ArrayList<>();
		userAuthorityRepository.findByUserSeq(userSeq).forEach(ua -> {
			authorities.add(new Authority(userRoleRepository.findOne(ua.getUserRoleSeq()).getAuthority()));
		});
		return authorities;
	}

	@Override
	public OAuth2AccessToken getOAuth2AccessTokenByLoginUser(LoginUser loginUser) {
		String grantType = "password";

		Map<String, String> authorizationParameters = new HashMap<String, String>();
		authorizationParameters.put("scope", client.getScopes());
		authorizationParameters.put("username", loginUser.getSeq().toString());
		authorizationParameters.put("client_id", client.getClientId());
		authorizationParameters.put("grant", grantType);

		Set<String> responseType = new HashSet<String>(Arrays.asList(grantType));
		Set<String> scopes = new HashSet<String>(client.getScope());

		OAuth2Request oAuth2Request = new OAuth2Request(authorizationParameters, client.getClientId(), 
				loginUser.getAuthorities(), true, scopes, null, "", responseType, null);

		UsernamePasswordAuthenticationToken authenticationToken = 
				new UsernamePasswordAuthenticationToken(loginUser, null, null);

		OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authenticationToken);
		oAuth2Authentication.setAuthenticated(true);

		OAuth2AccessToken token = authTokenServices.createAccessToken(oAuth2Authentication);
		
		return token;
	}
}
