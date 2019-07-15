package com.boot.model;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(Include.NON_EMPTY)
@Entity(name="sns_aws_s3_file")
public class AwsS3File {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer seq;
	@Column(nullable = false)
	private String saveFilename;
	@Column(nullable = false)
	private String originalFilename;
	@Column(nullable = false)
	private String path;
	@Column(nullable = false)
	private String fullPath;
	@Column(insertable = false, updatable = false)
	private Date createTime;
}
