package com.springweb.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springweb.dao.impl.UserDAOImpl;
import com.springweb.model.UserModel;
import com.springweb.service.UserService;

@Service
public class UserServiceImpl implements UserService{

	 @Autowired
	 private UserDAOImpl userDao;
	
	@Override
	public List<UserModel> getAllUserDetails() {
		return userDao.getAllUserDetails();
	}

	@Override
	public UserModel findUserById(UserModel user) {
		// TODO Auto-generated method stub
		return userDao.findUserId(user);
	}

	@Override
	public void addUser(UserModel userModel) {
		userDao.addUser(userModel);
		
	}

	@Override
	public void updateUser(UserModel userModel) {
		userDao.updateUser(userModel);
		
	}

	@Override
	public void deleteUser(int id) {
		userDao.deleteUser(id);
		
	}

	public boolean validateUserLogin(UserModel user) {
		// TODO Auto-generated method stub
		return userDao.validateUserLogin(user);
	}

	
}
