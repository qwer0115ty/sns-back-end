package com.boot.model.oauth2;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(Include.NON_EMPTY)
public class GoogleUser {
	private String sub;
	private String name;
	private String given_name;
	private String family_name;
	private String picture;
	private String email;
	private Boolean email_verified;
	private String locale;
	private String hd;
}
