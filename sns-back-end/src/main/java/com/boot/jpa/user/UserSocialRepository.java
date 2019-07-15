package com.boot.jpa.user;

import org.springframework.data.repository.CrudRepository;

import com.boot.model.user.UserSocial;

public interface UserSocialRepository extends CrudRepository<UserSocial, Integer> {
	public UserSocial findByIdAndIsLinked(String id, boolean isLinked);
}
