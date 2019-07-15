package com.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.model.oauth2.LoginUser;
import com.boot.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/api/user/{seq}", method = RequestMethod.GET)
	public @ResponseBody Object getUser(@AuthenticationPrincipal LoginUser lu, @PathVariable int seq) {
		return userService.getUser(seq);
	}
}
