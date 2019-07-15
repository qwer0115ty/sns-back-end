package com.boot.model.board;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "board")
@JsonInclude(Include.NON_EMPTY)
@Entity(name="sns_board_like")
public class BoardLike {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer seq;
	private Integer boardSeq;
	private Integer userSeq;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="boardSeq", insertable=false, updatable=false)
	private Board board;
}
