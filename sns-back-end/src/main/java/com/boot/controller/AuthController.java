package com.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.service.oauth2.AuthService;

@Controller
public class AuthController {
	@Autowired
	private AuthService authService;
	
	@RequestMapping(value = "/api/user/{seq}/token/refresh", method = RequestMethod.POST)
	public @ResponseBody Object getRefreshToken(@PathVariable int seq, @RequestBody String token) throws Exception {
		return authService.getRefreshToken(token);
	}
}
