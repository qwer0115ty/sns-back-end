package com.boot.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.boot.model.board.Board;
import com.boot.model.board.BoardLikeResponseBoardDto;
import com.boot.model.board.BoardsTarget;

public interface BoardService {
	public Board insertBoard (int userSeq, MultipartFile mf, String content) throws Exception;
	public Board getBoard (int seq);
	public Page<Board> getBoards (Pageable pageable, BoardsTarget target);
	public Page<Board> getBoards (Pageable pageable, int userSeq);
	public int deleteBoard (Board board) throws Exception;
	public Board updateBoard (Board board, MultipartFile mf) throws Exception;
	public BoardLikeResponseBoardDto setBoardLike (int userSeq, int boardSeq) throws Exception;
}
