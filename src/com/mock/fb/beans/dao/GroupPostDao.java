package com.mock.fb.beans.dao;

import java.util.List;

import com.mock.fb.model.Group;
import com.mock.fb.model.GroupPost;

public interface GroupPostDao {
	public void savePost(GroupPost groupPost);
	List<GroupPost> getMessagesByGroup(Group group);
}
