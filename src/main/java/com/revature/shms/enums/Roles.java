package com.revature.shms.enums;

public enum Roles {
	USER("USER"),
	EMPLOYEE("EMPLOYEE"),
	MANAGER("MANAGER");
	String value;
	Roles(String value) {
		this.value = value;
	}
	public String getValue() {
		return value;
	}
}
