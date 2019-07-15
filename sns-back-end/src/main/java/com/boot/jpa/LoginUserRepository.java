package com.boot.jpa;

import org.springframework.data.repository.CrudRepository;

import com.boot.model.oauth2.LoginUser;

public interface LoginUserRepository extends CrudRepository<LoginUser, Integer> {
	public LoginUser findBySeqAndStatus(int seq, boolean status);
}
