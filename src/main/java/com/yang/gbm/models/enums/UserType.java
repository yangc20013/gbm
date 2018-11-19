package com.yang.gbm.models.enums;

public enum UserType {
	REGUSER(0,"注册用户"),
	BACKUSER(1,"后台配置用户");
	
	private int type;
	private String name;

	private UserType(int type, String name) {
		this.type = type;
		this.name= name;
	}
	public int getValue() {
		return this.type;
	}
	
}
