package com.boot.model.user;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;

import com.boot.model.AwsS3File;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(exclude = "user")
@JsonInclude(Include.NON_EMPTY)
@Entity(name = "sns_user_profile")
public class UserProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer seq;
	private Integer userSeq;
	private Integer profileImgAwsS3Seq;
	
	@JsonBackReference
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "userSeq", referencedColumnName = "seq", insertable = false, updatable = false)
	@LazyToOne(LazyToOneOption.NO_PROXY)
	private User user;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="profileImgAwsS3Seq", insertable=false, updatable=false)
	private AwsS3File awsS3File;
}
