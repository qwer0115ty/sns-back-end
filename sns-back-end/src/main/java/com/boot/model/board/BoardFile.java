package com.boot.model.board;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.boot.model.AwsS3File;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(Include.NON_EMPTY)
@Entity(name="sns_board_file")
public class BoardFile {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer seq;
	private Integer boardSeq;
	private Integer awsS3Seq;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="awsS3Seq", insertable=false, updatable=false)
	private AwsS3File awsS3File;
}
