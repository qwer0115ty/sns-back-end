package com.boot.model.user;

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
@Entity(name="sns_user_authority")
public class UserAuthority {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer seq;
	@Column(nullable=false)
	private Integer userSeq;
	@Column(nullable=false)
	private Integer userRoleSeq;
}