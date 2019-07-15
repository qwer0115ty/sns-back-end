package com.boot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.boot.model.board.BoardsTarget;
import com.boot.model.oauth2.LoginUser;
import com.boot.service.BoardService;
import com.boot.service.UserService;

@Controller
public class MyController {
	@Autowired
	private BoardService boardService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value = "/api/my/follow/user/{seq}", method = RequestMethod.PUT)
	public @ResponseBody Object putFollowUser(@AuthenticationPrincipal LoginUser lu, @PathVariable int seq) {
		return userService.setFollowUser(lu.getSeq(), seq);
	}
	
	@RequestMapping(value = "/api/my/follow/user/boards", method = RequestMethod.GET)
	public @ResponseBody Object getFollowUserBoard(Integer page) throws Exception {
		int pageNo = 1;
		
		if (page != null) {
			pageNo = page;
		}
		
		return boardService.getBoards(new PageRequest(pageNo - 1, 1), BoardsTarget.FOLLOW);
	}
	
	@RequestMapping(value = "/api/my/like/boards", method = RequestMethod.GET)
	public @ResponseBody Object getLikedBoard(Integer page) throws Exception {
		int pageNo = 1;
		
		if (page != null) {
			pageNo = page;
		}
		
		return boardService.getBoards(new PageRequest(pageNo - 1, 1), BoardsTarget.LIKE);
	}
}
