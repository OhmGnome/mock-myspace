package com.mock.fb.controller;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.mock.fb.model.GroupPost;

@XmlRootElement
public class GroupPostsWrapper{
	List<GroupPost> list;

  @XmlElement(name="Item")
	public List<GroupPost> getList() {
		return list;
	}

	public void setList(List<GroupPost> list) {
		this.list = list;
	}
}

