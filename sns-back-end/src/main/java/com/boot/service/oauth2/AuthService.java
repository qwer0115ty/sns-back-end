package com.boot.service.oauth2;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.boot.model.oauth2.GoogleUser;
import com.boot.model.oauth2.LoginUser;

public interface AuthService {
	public GoogleUser getGoogleUserByAuthentication(Authentication authentication) throws Exception;
	public LoginUser getLoginUserBySub(String sub);
	public LoginUser getLoginUserByUserSeq(int userSeq);
	public OAuth2AccessToken getOAuth2AccessTokenByLoginUser(LoginUser loginUser);
}
