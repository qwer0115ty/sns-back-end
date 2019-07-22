package com.boot.service;

import java.io.File;
import java.io.FileNotFoundException;

import javax.servlet.ServletContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.boot.jpa.user.UserAuthorityRepository;
import com.boot.jpa.user.UserFollowRepository;
import com.boot.jpa.user.UserProfileRepository;
import com.boot.jpa.user.UserRepository;
import com.boot.jpa.user.UserSocialRepository;
import com.boot.model.AwsS3File;
import com.boot.model.user.User;
import com.boot.model.user.UserAuthority;
import com.boot.model.user.UserFollow;
import com.boot.model.user.UserProfile;
import com.boot.model.user.UserSocial;
import com.boot.util.CustomUtils;

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

	@Autowired
	private UserProfileRepository userProfileRepository;
	
	@Autowired
	private AwsS3Service awsS3Service;
	
	@Autowired
	private ServletContext servletContext;
	
	@Transactional
	@Override
	public UserSocial insertUser(String sub, String name, String email) throws Exception {
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
			us.setEmail(email);
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
	
	@Transactional
	@Override
	public int setUserProfileImg (MultipartFile mf, int userSeq) throws Exception {
		if(mf != null) {
			File file = CustomUtils.makeTempFile(mf, servletContext.getRealPath("/"));
			file = CustomUtils.makeSquareImage(file);
			
			AwsS3File asf = new AwsS3File();
			asf.setOriginalFilename(mf.getOriginalFilename());
			asf.setSaveFilename(file.getName());
			asf.setPath("user/" + userSeq + "/profile/");
			
			asf = awsS3Service.uploadFile(file, asf);
			
			User u = userRepository.findOne(userSeq);
			UserProfile up = u.getUserProfile();
			
			if (up != null && up.getProfileImgAwsS3Seq() != null) {
				AwsS3File deleteFile = up.getAwsS3File();
				awsS3Service.deleteFile(deleteFile);
			} else if (up == null) {
				up = new UserProfile();
				up.setUserSeq(userSeq);
			}
			
			up.setProfileImgAwsS3Seq(asf.getSeq());
			userProfileRepository.save(up);
			
			file.delete();
			
			return 1;
		} else {
			throw new FileNotFoundException("asdasd");
		}
	}
	
	@Transactional
	@Override
	public int deleteUserProfileImg (int userSeq) throws Exception {
		User u = userRepository.findOne(userSeq);
		UserProfile up = u.getUserProfile();
		awsS3Service.deleteFile(up.getAwsS3File());
		up.setProfileImgAwsS3Seq(null);
		userProfileRepository.save(up);
		return 1;
	}
}
