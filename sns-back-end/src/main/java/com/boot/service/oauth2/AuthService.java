package com.boot.service.oauth2;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import com.boot.model.oauth2.GoogleUser;
import com.boot.model.oauth2.LoginUser;
import com.boot.model.oauth2.RefreshTokenRespDto;

public interface AuthService {
	public GoogleUser getGoogleUserByAuthentication(Authentication authentication) throws Exception;
	public LoginUser getLoginUserBySub(GoogleUser gu);
	public LoginUser getLoginUserByUserSeq(int userSeq);
	public OAuth2AccessToken getOAuth2AccessTokenByLoginUser(LoginUser loginUser);
	public RefreshTokenRespDto getRefreshToken(String token) throws Exception;
}
