package com.boot.model.board;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(Include.NON_EMPTY)
public class BoardLikeResponseBoardDto {
	private int boardLikeCount;
	private boolean isMeLikedBoard;
}
