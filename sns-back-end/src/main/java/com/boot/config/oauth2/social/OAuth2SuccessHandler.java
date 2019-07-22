package com.boot.config.oauth2.social;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.boot.model.oauth2.GoogleUser;
import com.boot.model.oauth2.LoginUser;
import com.boot.service.oauth2.AuthService;
import com.boot.util.CustomUtils;
import com.google.gson.Gson;

public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
	private final AuthService socialAuthService;
	private Gson gson = new Gson();
	
	public OAuth2SuccessHandler(AuthService socialAuthService) {
		this.socialAuthService = socialAuthService;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		GoogleUser gu = null;
		
		try {
			gu = socialAuthService.getGoogleUserByAuthentication(authentication);
		} catch (Exception e) {
			// JsonParseException, JsonMappingException, IOException
			e.printStackTrace();
		}
		
		LoginUser lu = socialAuthService.getLoginUserBySub(gu);
		
		String path = "/WEB-INF/jsp/";
		if(lu != null) {
			lu.setSub(gu.getSub());
			OAuth2AccessToken accessToken = socialAuthService.getOAuth2AccessTokenByLoginUser(lu);
			
			request.setAttribute("access_token", accessToken.getValue());
			request.setAttribute("refresh_token", accessToken.getRefreshToken());
			
			lu.setUserAuthorities(lu.getUserAuthorities());
			lu.setAuthorities(null);
			String ju = gson.toJson(lu);
			request.setAttribute("user", CustomUtils.encodeURIComponent(ju));
			
			path += "googleAuthSuccess.jsp";
		} else {
			request.setAttribute("gu", CustomUtils.encodeURIComponent(gson.toJson(gu)));
			path += "signupGoogleAuthSuccess.jsp";
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(path);
		rd.forward(request, response);
	}
}
