package com.boot.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.jpa.user.UserAuthorityRepository;
import com.boot.jpa.user.UserFollowRepository;
import com.boot.jpa.user.UserRepository;
import com.boot.jpa.user.UserSocialRepository;
import com.boot.model.user.User;
import com.boot.model.user.UserAuthority;
import com.boot.model.user.UserFollow;
import com.boot.model.user.UserSocial;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserAuthorityRepository userAuthorityRepository;
	
	@Autowired
	private UserSocialRepository userSocialRepository;
	
	@Autowired
	private UserFollowRepository userFollowRepository;

	@Transactional
	@Override
	public UserSocial insertUser(String sub, String name) throws Exception {
		UserSocial us = null;
		
		if (userSocialRepository.findByIdAndIsLinked(sub, true) != null) {
			throw new Exception("이미 가입된 회원");
		} else {
			User u = new User();
			u.setName(name);
			u = userRepository.save(u);
			
			us = new UserSocial();
			us.setId(sub);
			us.setUserSeq(u.getSeq());
			userSocialRepository.save(us);
			
			UserAuthority ua = new UserAuthority();
			ua.setUserSeq(u.getSeq());
			ua.setUserRoleSeq(1);
			ua = userAuthorityRepository.save(ua);
		}

		return us;
	}

	@Override
	public User getUser (int seq) {
		return userRepository.findOne(seq);
	}
	
	@Override
	public int setFollowUser (int meUserSeq, int userSeq) {
		UserFollow uf = userFollowRepository.findFirstByUserSeqAndFollowerSeq(userSeq, meUserSeq);
		
		if(uf != null) {
			userFollowRepository.delete(uf);
		} else {
			uf = new UserFollow();
			uf.setFollowerSeq(meUserSeq);
			uf.setUserSeq(userSeq);
			userFollowRepository.save(uf);
		}
		
		return 1;
	}
	
	@Override
	public boolean getIsDuplicateUserName (String name) {
		User u = userRepository.findFirstByNameAndStatus(name, true);
		return u != null;
	}
}
