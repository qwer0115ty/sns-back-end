package com.boot.model.oauth2;

import org.springframework.security.core.GrantedAuthority;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonInclude(Include.NON_EMPTY)
public class Authority implements GrantedAuthority{
	private static final long serialVersionUID = -2669454543203443416L;
	private String authority;
	public Authority() {
	}
	
	public Authority(String authority) {
		this.authority = authority;
	}
	@Override
	public String getAuthority() {
		return authority;
	}
}
