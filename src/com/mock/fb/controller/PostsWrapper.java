package com.mock.fb.controller;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.mock.fb.model.Post;

@XmlRootElement
public class PostsWrapper{
	List<Post> list;

  @XmlElement(name="Item")
	public List<Post> getList() {
		return list;
	}

	public void setList(List<Post> list) {
		this.list = list;
	}
}
