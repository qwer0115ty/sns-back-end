package com.boot.jpa.board;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.model.board.BoardLike;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Integer> {
	public BoardLike findFirstByBoardSeqAndUserSeq(int boardSeq, int userSeq);
	public int deleteByBoardSeq(int boardSeq);
}
