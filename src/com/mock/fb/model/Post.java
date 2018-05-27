package com.mock.fb.model;

// Generated Jun 2, 2015 9:17:34 AM by Hibernate Tools 4.3.1

import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Post generated by hbm2java
 */
@XmlRootElement
public class Post implements java.io.Serializable {

	private Integer id;
	private Date timestamp;
	private User userByPosterId;
	private User userByWallId;
	private String text;

	public Post() {
	}

	public Post(User userByPosterId, User userByWallId, String text) {
		this.userByPosterId = userByPosterId;
		this.userByWallId = userByWallId;
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
	public User getUserByPosterId() {
		return this.userByPosterId;
	}

	public void setUserByPosterId(User userByPosterId) {
		this.userByPosterId = userByPosterId;
	}

	@XmlTransient
	public User getUserByWallId() {
		return this.userByWallId;
	}

	public void setUserByWallId(User userByWallId) {
		this.userByWallId = userByWallId;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
