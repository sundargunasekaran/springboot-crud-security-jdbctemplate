package com.springweb.model;

import java.util.ArrayList;
import java.util.List;

public class UserModel {
	
	
	private int userId;
	private String username;
	private String password;
	private String emailId;
	private String role;
	private int roleId;
	private List<PolicyModel> policyModelList = new ArrayList<PolicyModel>();
	
	public UserModel(){
		userId = -1;
		username = "";
		password = "";
		emailId = "";
		role = "";
		roleId = -1;
		policyModelList = null;
	}
	
	
	public List<PolicyModel> getPolicyModelList() {
		return policyModelList;
	}
	public void setPolicyModelList(List<PolicyModel> policyModelList) {
		this.policyModelList = policyModelList;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	

}
