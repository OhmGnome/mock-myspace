package com.mock.fb.beans;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mock.fb.beans.dao.UserDao;
import com.mock.fb.model.User;

@Component
@Scope("session")
public class SearchUserBean {
	
	@Autowired
	private UserDao userDao;
	
	private List<User> searchedUsers;
	
	private String userNameFragment;
	
	@PostConstruct
	public void init(){
		userNameFragment = null;
	}
	
	
	public String searched(){
		searchedUsers = userDao.getUsersFromSearch(userNameFragment);
		userNameFragment = null;
		return "searched";
	}


	public List<User> getSearchedUsers() {
		return searchedUsers;
	}


	public void setSearchedUsers(List<User> searchedUsers) {
		this.searchedUsers = searchedUsers;
	}


	public String getUserNameFragment() {
		return userNameFragment;
	}


	public void setUserNameFragment(String userNameFragment) {
		this.userNameFragment = userNameFragment;
	}
	
	
}
