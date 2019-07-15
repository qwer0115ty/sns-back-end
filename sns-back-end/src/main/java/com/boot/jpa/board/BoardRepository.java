package com.boot.jpa.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.boot.model.board.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {
	public Page<Board> findAllByOrderBySeqDesc(Pageable pageable);
	public Page<Board> findByUserSeqOrderBySeqDesc(Pageable pageable, int userSeq);
}
