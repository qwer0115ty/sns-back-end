package com.boot.jpa.user;

import org.springframework.data.repository.CrudRepository;

import com.boot.model.user.UserRole;

public interface UserRoleRepository extends CrudRepository<UserRole, Integer> {
}
