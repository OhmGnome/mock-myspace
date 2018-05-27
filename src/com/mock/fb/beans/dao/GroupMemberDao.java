package com.mock.fb.beans.dao;

import java.util.List;

import com.mock.fb.model.Group;
import com.mock.fb.model.User;


public interface GroupMemberDao {
	public void saveMember(User user, Group group);
	public void deleteMember(User user, Group group);
	public void setAcceptTrue(User user, Group group);
	public void declineMember(User user, Group group);	
	public List<User> getMembers(Group group);
	public List<Group> notMembers(User user);
	public boolean isWaiting(User user,Group group);
}
