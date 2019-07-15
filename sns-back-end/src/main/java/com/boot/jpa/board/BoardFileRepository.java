package com.boot.jpa.board;

import org.springframework.data.repository.CrudRepository;

import com.boot.model.board.BoardFile;

public interface BoardFileRepository extends CrudRepository<BoardFile, Integer> {
	public BoardFile findFirstByBoardSeq(int boardSeq);
}
