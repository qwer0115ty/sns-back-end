package com.boot.service.oauth2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.boot.jpa.LoginUserRepository;
import com.boot.jpa.user.UserAuthorityRepository;
import com.boot.jpa.user.UserRoleRepository;
import com.boot.model.oauth2.Authority;
import com.boot.model.oauth2.LoginUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	@Autowired
	private LoginUserRepository loginUserRepository;
	@Autowired
	private UserAuthorityRepository userAuthorityRepository;
	@Autowired
	private UserRoleRepository userRoleRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LoginUser user = loginUserRepository.findBySeqAndStatus(Integer.parseInt(username), true);
		if (user == null) {
			throw new UsernameNotFoundException("UsernameNotFound [" + username + "]");
		}
		
		List<Authority> authorities = getAuthorities(user.getSeq());
		user.setAuthorities(authorities);
		return user;
	}
	
	private List<Authority> getAuthorities (int userSeq) {
		List<Authority> authorities = new ArrayList<>();
		userAuthorityRepository.findByUserSeq(userSeq).forEach(ua -> {
			authorities.add(new Authority(userRoleRepository.findOne(ua.getUserRoleSeq()).getAuthority()));
		});
		return authorities;
	}
}
