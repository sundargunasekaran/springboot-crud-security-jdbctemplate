package com.springweb.dao;

import java.util.List;

import com.springweb.model.UserModel;

public interface UserDAO {
	
	
	 public List<UserModel> getAllUserDetails();
	 
	 public UserModel findUserId(UserModel user);
	 
	 public void addUser(UserModel userModel);
	 
	 public void updateUser(UserModel userModel);
	 
	 public void deleteUser(int id);
	 
	 public boolean validateUserLogin(UserModel userModel);

}
