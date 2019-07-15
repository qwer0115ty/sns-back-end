package com.boot.jpa.board.support;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QueryDslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.boot.model.board.Board;
import com.boot.model.board.QBoard;
import com.boot.model.user.QUserFollow;
import com.querydsl.jpa.JPQLQuery;

@Repository
public class BoardRepositorySupport extends QueryDslRepositorySupport{
	public BoardRepositorySupport() {
		super(Board.class);
	}
	
	public Page<Board> getFollowingUsersBoards(Pageable pageable, int userSeq) {
		QBoard qb = QBoard.board;
		QUserFollow quf = QUserFollow.userFollow;
		
		JPQLQuery<Board> query = from(qb).join(qb.user.userFollowsAsUser, quf);
		query.where(quf.followerSeq.eq(userSeq));
		query.orderBy(qb.seq.desc());
		
		List<Board> boards = getQuerydsl().applyPagination(pageable, query.select(qb)).fetch();
		return new PageImpl<>(boards, pageable, query.fetchCount()); 
	}
}
