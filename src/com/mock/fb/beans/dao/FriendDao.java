package com.mock.fb.beans.dao;

import java.util.List;

import com.mock.fb.model.Friends;
import com.mock.fb.model.User;


public interface FriendDao {
	
	public void saveFriend(User user, User friend);
	public void deleteFriend(User user, User friend);
	public List<User> getFriendsByUser(User user);
	public List<User> getFriends();
	public boolean isFriend(User user, User friend);
	public List<User> notFriends(User user);
	public boolean isWaiting(User user,User friend);
	public void setAcceptTrue(User user, User friend);
	/**Ignore Friend */
	public void ignoreFriend(User user, User friend);
	public boolean isIgnored(User user, User friend);
}
