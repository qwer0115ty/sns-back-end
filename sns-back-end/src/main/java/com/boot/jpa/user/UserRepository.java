package com.boot.jpa.user;

import org.springframework.data.repository.CrudRepository;

import com.boot.model.user.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	public User findFirstByNameAndStatus (String name, boolean status);
}
