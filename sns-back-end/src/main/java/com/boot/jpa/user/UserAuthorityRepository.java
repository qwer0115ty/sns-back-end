package com.boot.jpa.user;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.boot.model.user.UserAuthority;

public interface UserAuthorityRepository extends CrudRepository<UserAuthority, Integer> {
	List<UserAuthority> findByUserSeq(Integer userSeq);
}
