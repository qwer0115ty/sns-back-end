package com.boot.model.user;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.springframework.security.core.context.SecurityContextHolder;

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
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer seq;
	@Column(nullable = false)
	private String name;
	@Column(insertable=false)
	private Boolean status;

	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="userSeq", referencedColumnName="seq", insertable=false, updatable=false)
	private List<UserFollow> userFollowsAsUser;
	
	@JsonIgnore
	@OneToMany(fetch=FetchType.LAZY)
	@JoinColumn(name="followerSeq", referencedColumnName="seq", insertable=false, updatable=false)
	private List<UserFollow> userFollowsAsFollower;
	
	@Transient
	public int getFollowingCount () {
		return userFollowsAsFollower == null ? 0 : userFollowsAsFollower.size();
	}
	
	@Transient
	public int getFollowerCount () {
		return userFollowsAsUser == null ? 0 : userFollowsAsUser.size();
	}
	
	@Transient
	public boolean getIsMeFollowing () {
		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		if (userFollowsAsUser != null && name != null && ! name.equals("anonymousUser") && Integer.parseInt(name) != seq) {
			for (UserFollow uf : userFollowsAsUser) {
				int userSeq = Integer.parseInt(name);
				if (uf.getFollowerSeq() == userSeq) {
					return true;
				}
			}
		}
		return false;
	}
	
	@OneToOne(cascade=CascadeType.ALL,fetch = FetchType.LAZY, optional = true, mappedBy="user")
	@LazyToOne(LazyToOneOption.NO_PROXY)
	private UserProfile userProfile;
}
