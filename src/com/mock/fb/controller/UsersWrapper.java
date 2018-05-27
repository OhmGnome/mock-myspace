package com.mock.fb.controller;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.mock.fb.model.User;

@XmlRootElement
public class UsersWrapper{
	List<User> list;

  @XmlElement(name="Item")
	public List<User> getList() {
		return list;
	}

	public void setList(List<User> list) {
		this.list = list;
	}
}
