package com.boot.jpa.user;

import org.springframework.data.repository.CrudRepository;

import com.boot.model.user.UserFollow;

public interface UserFollowRepository extends CrudRepository<UserFollow, Integer> {
	UserFollow findFirstByUserSeqAndFollowerSeq(Integer userSeq, Integer followerSeq);
}
