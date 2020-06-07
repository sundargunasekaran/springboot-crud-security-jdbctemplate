package com.springweb.service;

import java.util.List;

import com.springweb.model.UserModel;

public interface  UserService {
	
	 public List<UserModel> getAllUserDetails();
	 
	 public UserModel findUserById(UserModel user);
	 
	 public void addUser(UserModel userModel);
	 
	 public void updateUser(UserModel userModel);
	 
	 public void deleteUser(int id);
	 
	 public boolean validateUserLogin(UserModel userModel);

}
