package com.boot.jpa.board.support;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.boot.model.board.Board;
import com.boot.model.board.BoardLike;
import com.boot.model.board.QBoard;
import com.boot.model.board.QBoardLike;
import com.querydsl.jpa.JPQLQuery;

@Repository
public class BoardLikeRepositorySupport extends QueryDslRepositorySupport{
	public BoardLikeRepositorySupport() {
		super(BoardLike.class);
	}
	
	public Page<Board> getLikedBoards(Pageable pageable, int userSeq) {
		QBoardLike qbl = QBoardLike.boardLike;
		QBoard qb = QBoardLike.boardLike.board;
		
		JPQLQuery<BoardLike> query = from(qbl).join(qb);
		query.where(qbl.userSeq.eq(userSeq));
		query.orderBy(qb.seq.desc());
		
		List<Board> boards = getQuerydsl().applyPagination(pageable, query.select(qb)).fetch();
		return new PageImpl<>(boards, pageable, query.fetchCount()); 
	}
}
