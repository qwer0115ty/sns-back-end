package com.boot.service;

import org.springframework.web.multipart.MultipartFile;

import com.boot.model.user.User;
import com.boot.model.user.UserSocial;

public interface UserService {
	public UserSocial insertUser (String sub, String name, String email) throws Exception;
	public User getUser (int seq);
	public int setFollowUser (int meUserSeq, int userSeq);
	public boolean getIsDuplicateUserName (String name);
	public int setUserProfileImg (MultipartFile mf, int userSeq) throws Exception;
	public int deleteUserProfileImg (int userSeq) throws Exception;
}
