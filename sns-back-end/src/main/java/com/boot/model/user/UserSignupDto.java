package com.boot.model.user;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@JsonInclude(Include.NON_EMPTY)
public class UserSignupDto {
	@NotNull
	private String sub;
	@NotNull
	@Size(min=3, max=20)
	private String name;
	@NotNull
	@Email
	private String email;
}
