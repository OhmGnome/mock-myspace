package com.mock.fb.beans.dao;

import java.util.List;

import com.mock.fb.model.Friends;
import com.mock.fb.model.User;

public interface UserDao {
	public void saveUser(User user);
	public User getUserByUsername(String username);
	public List<User> getUsersFromSearch(String search);
	public List<User> getUsers(User user);
}
