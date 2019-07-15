package com.boot.service;

import com.boot.model.user.User;
import com.boot.model.user.UserSocial;

public interface UserService {
	public UserSocial insertUser (String sub, String name) throws Exception;
	public User getUser (int seq);
	public int setFollowUser (int meUserSeq, int userSeq);
	public boolean getIsDuplicateUserName (String name);
}
