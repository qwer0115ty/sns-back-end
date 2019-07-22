package com.boot.jpa.user;

import org.springframework.data.repository.CrudRepository;

import com.boot.model.user.UserProfile;

public interface UserProfileRepository extends CrudRepository<UserProfile, Integer> {
}
