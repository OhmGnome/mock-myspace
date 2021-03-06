package com.mock.fb.model;

// Generated Jun 2, 2015 9:17:34 AM by Hibernate Tools 4.3.1

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * GroupPost generated by hbm2java
 */
@XmlRootElement
public class GroupPost implements java.io.Serializable {

	private Integer id;
	private Date timestamp;
	private Group group;
	private User user;
	private String text;

	public GroupPost() {
	}

	public GroupPost(Group group, User user, String text) {
		this.group = group;
		this.user = user;
		this.text = text;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getTimestamp() {
		return this.timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	@XmlTransient
	public Group getGroup() {
		return this.group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@XmlTransient
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
