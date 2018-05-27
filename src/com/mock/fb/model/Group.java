package com.mock.fb.model;

// Generated Jun 2, 2015 9:17:34 AM by Hibernate Tools 4.3.1

import java.util.HashSet;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Group generated by hbm2java
 */
@XmlRootElement
public class Group implements java.io.Serializable {

	private Integer id;
	private User user;
	private String name;
	private boolean deleted;
	private Set groupMembers = new HashSet(0);
	private Set groupPosts = new HashSet(0);

	public Group() {
	}

	public Group(User user, String name, boolean deleted) {
		this.user = user;
		this.name = name;
		this.deleted = deleted;
	}

	public Group(User user, String name, boolean deleted, Set groupMembers,
			Set groupPosts) {
		this.user = user;
		this.name = name;
		this.deleted = deleted;
		this.groupMembers = groupMembers;
		this.groupPosts = groupPosts;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlTransient
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isDeleted() {
		return this.deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	@XmlTransient
	public Set getGroupMembers() {
		return this.groupMembers;
	}

	public void setGroupMembers(Set groupMembers) {
		this.groupMembers = groupMembers;
	}

	@XmlTransient
	public Set getGroupPosts() {
		return this.groupPosts;
	}

	public void setGroupPosts(Set groupPosts) {
		this.groupPosts = groupPosts;
	}

}