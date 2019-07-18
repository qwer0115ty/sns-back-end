package com.boot.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.model.oauth2.LoginUser;
import com.boot.model.user.UserSignupDto;
import com.boot.model.user.UserSocial;
import com.boot.service.UserService;
import com.boot.service.oauth2.AuthService;

@Controller
public class SignupController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthService authService;
	
	@RequestMapping(value = "/api/user", method = RequestMethod.POST)
	public @ResponseBody Object signupUser(@RequestBody @Valid UserSignupDto user) throws Exception {
		UserSocial us = userService.insertUser(user.getSub(), user.getName(), user.getEmail());
		LoginUser lu = authService.getLoginUserByUserSeq(us.getUserSeq());
		lu.setSub(us.getId());
		
		return authService.getOAuth2AccessTokenByLoginUser(lu);
	}
	
	@RequestMapping(value = "/api/user/validation/name", method = RequestMethod.GET)
	public @ResponseBody Object validateUserName(String name) throws Exception {
		return ! userService.getIsDuplicateUserName(name);
	}
}
