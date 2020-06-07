package com.springweb.model;

public class PolicyModel {
	
	private int policyId;
	private String policyName;
	private String policyCompany;
	private int policyYrs;
	private String policyEmail;
	
	public int getPolicyId() {
		return policyId;
	}
	public void setPolicyId(int policyId) {
		this.policyId = policyId;
	}
	public String getPolicyName() {
		return policyName;
	}
	public void setPolicyName(String policyName) {
		this.policyName = policyName;
	}
	public String getPolicyCompany() {
		return policyCompany;
	}
	public void setPolicyCompany(String policyCompany) {
		this.policyCompany = policyCompany;
	}
	public int getPolicyYrs() {
		return policyYrs;
	}
	public void setPolicyYrs(int policyYrs) {
		this.policyYrs = policyYrs;
	}
	public String getPolicyEmail() {
		return policyEmail;
	}
	public void setPolicyEmail(String policyEmail) {
		this.policyEmail = policyEmail;
	}

}
