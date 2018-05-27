package com.mock.fb.controller;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.mock.fb.model.Group;

@XmlRootElement
public class GroupsWrapper {
	List<Group> list;

  @XmlElement(name="Item")
	public List<Group> getList() {
		return list;
	}

	public void setList(List<Group> list) {
		this.list = list;
	}
}
