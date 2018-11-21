package com.softplan.restapi.api.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.softplan.restapi.api.entity.User;
import com.softplan.restapi.api.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User createOrUpdate(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Page<User> findAll(int page, int count) {
		// TODO Auto-generated method stub
		return null;
	}

}
