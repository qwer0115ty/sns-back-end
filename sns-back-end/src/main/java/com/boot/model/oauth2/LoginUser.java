package com.boot.model.oauth2;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
@JsonInclude(Include.NON_EMPTY)
@Entity
@Table(name="sns_user")
public class LoginUser implements UserDetails {
	private static final long serialVersionUID = -2812273319787271288L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer seq;
	@Column(nullable = false)
	private String name;
	@Column(insertable=false)
	private Boolean status;
	
	@Transient
	private String sub;
	
	@JsonIgnore
	@Transient
	private List<Authority> authorities;
	
	@Transient
	private List<String> userAuthorities;
	
	public List<String> getUserAuthorities() {
		List<String> stirngAuthorities = new ArrayList<>();
		this.authorities.forEach(authority -> {
			stirngAuthorities.add(authority.getAuthority());
		});
		
		return stirngAuthorities;
	}
	
	public void setAuthoritiesByUserAuthorities() {
		List<Authority> authorities = new ArrayList<>();
		this.userAuthorities.forEach(authority -> {
			authorities.add(new Authority(authority));
		});
		this.authorities = authorities;
	}
	
	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@JsonIgnore
	@Override
	public String getUsername() {
		return seq.toString();
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}
}
