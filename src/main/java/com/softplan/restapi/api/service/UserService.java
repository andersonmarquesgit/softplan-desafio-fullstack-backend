package com.softplan.restapi.api.service;

import org.springframework.data.domain.Page;

import com.softplan.restapi.api.entity.User;

public interface UserService {

	User findByEmail(String email);
	
	User createOrUpdate(User user);
	
	User findById(String id);
	
	void delete(String id);
	
	Page<User> findAll(int page, int count);
}
