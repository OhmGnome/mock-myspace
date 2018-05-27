package com.mock.fb.beans.dao;

import java.util.List;

import com.mock.fb.model.Group;
import com.mock.fb.model.User;

public interface GroupDao {
	public void saveGroup(String groupName, User user);
	public void deletegroupByGroupName(String groupName);
	public Group getGroupByGroupName(String groupName);
	public User getOwnerByGroupName(String groupName);
	public List<Group> getGroupsByUser(User user);
}
