package com.boot.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.boot.model.board.Board;
import com.boot.model.board.BoardsTarget;
import com.boot.model.oauth2.LoginUser;
import com.boot.service.BoardService;

@Controller
public class BoardController {
	@Autowired
	private BoardService boardService;
	
	@PreAuthorize("hasAnyRole('ROLE_USER')")
	@RequestMapping(value = "/api/board", method = RequestMethod.POST)
	public @ResponseBody Object postBoard(MultipartHttpServletRequest mRequest, HttpSession session,
			@AuthenticationPrincipal LoginUser lu) throws Exception {
		String content = mRequest.getParameter("data").toString();
		MultipartFile mf = mRequest.getFile("file");
		
		if(content.equals("null") || mf == null) {
			throw new Exception("invalid");
		}
		
		return boardService.insertBoard(lu.getSeq(), mf, content);
	}
	
	@RequestMapping(value = "/api/board/{seq}", method = RequestMethod.GET)
	public @ResponseBody Object getBoard(@PathVariable int seq, @AuthenticationPrincipal LoginUser lu) throws Exception {
		return boardService.getBoard(seq);
	}
	
	@RequestMapping(value = "/api/boards", method = RequestMethod.GET)
	public @ResponseBody Object getBoard(Integer page) throws Exception {
		int pageNo = 1;
		
		if (page != null) {
			pageNo = page;
		}
		
		return boardService.getBoards(new PageRequest(pageNo - 1, 3), BoardsTarget.ALL);
	}
	
	@RequestMapping(value = "/api/board/{seq}", method = RequestMethod.DELETE)
	public @ResponseBody Object deleteBoard(@PathVariable int seq, @AuthenticationPrincipal LoginUser lu, 
			HttpServletRequest request) throws Exception {
		Board board = boardService.getBoard(seq);
		
		if (request.isUserInRole("ROLE_ADMIN") || lu.getSeq() == board.getUserSeq()) {
			return boardService.deleteBoard(board);	
		} else {
			throw new Exception();
		}
	}
	
	@RequestMapping(value = "/api/board/{seq}/form", method = RequestMethod.POST)
	public @ResponseBody Object putBoard(@PathVariable int seq, MultipartHttpServletRequest mRequest, 
			@AuthenticationPrincipal LoginUser lu) throws Exception {
		Board board = boardService.getBoard(seq);
		
		if (lu.getSeq() != board.getUserSeq()) {
			throw new Exception();
		}
		
		MultipartFile mf = mRequest.getFile("file");
		String content = mRequest.getParameter("data").toString();
		
		if(content.equals("null")) {
			throw new Exception("invalid");
		}
		
		board.setContent(content);
		return boardService.updateBoard(board, mf);
	}
	
	@RequestMapping(value = "/api/board/{seq}/like", method = RequestMethod.POST)
	public @ResponseBody Object postBoardLike(@PathVariable int seq, @AuthenticationPrincipal LoginUser lu) throws Exception {
		return boardService.setBoardLike(lu.getSeq(), seq);
	}
	
	@RequestMapping(value = "/api/user/{seq}/boards", method = RequestMethod.GET)
	public @ResponseBody Object getBoard(@PathVariable int seq, Integer page) throws Exception {
		int pageNo = 1;

		if (page != null) {
			pageNo = page;
		}

		return boardService.getBoards(new PageRequest(pageNo - 1, 6), seq);
	}
}
