package com.boot.model.board;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.springframework.security.core.context.SecurityContextHolder;

import com.boot.model.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(Include.NON_EMPTY)
@Entity(name="sns_board")
public class Board {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer seq;
	private Integer userSeq;
	@Column(nullable = false)
	private String content;
	@Column(insertable = false, updatable = false)
	private Date createTime;
	
	@Transient
	@JsonSerialize
	private BoardFile boardFile;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="userSeq", referencedColumnName="seq", insertable=false, updatable=false)
	private User user;
	
	@Transient
	public int getBoardLikeCount () {
		return boardLikes == null ? 0 : boardLikes.size();
	}
	
	@Transient
	public boolean getIsMeLikedBoard() {
		if(boardLikes != null && ! SecurityContextHolder.getContext().getAuthentication().getName().equals("anonymousUser")) {
			int userSeq = Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName());
			for (BoardLike bl : boardLikes) {
				if (bl.getUserSeq() == userSeq) {
					return true;
				}
			}
		}
		return false;
	}

	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="boardSeq", insertable=false, updatable=false)
	private List<BoardLike> boardLikes;
}
