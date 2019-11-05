package com.feng.security.model;

public class ApplicationPrivilege {

	private Long privilegeid;

	String privilege;

	String object;

	public Long getPrivilegeid() {
		return privilegeid;
	}

	public void setPrivilegeid(Long privilegeid) {
		this.privilegeid = privilegeid;
	}

	public String getPrivilege() {
		return privilege;
	}

	public void setPrivilege(String privilege) {
		this.privilege = privilege;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

}
