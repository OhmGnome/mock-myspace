package com.mock.fb.beans.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mock.fb.beans.dao.GroupPostDao;
import com.mock.fb.model.Group;
import com.mock.fb.model.GroupPost;
import com.mock.fb.model.User;

@Repository
@Transactional
public class GroupPostDaoImpl implements GroupPostDao {
	
	@Autowired
	SessionFactory factory;
	
	@Override
	public void savePost(GroupPost groupPost) {
		Session session = factory.getCurrentSession();		
		session.save(groupPost);
	}

	@Override
	public List<GroupPost> getMessagesByGroup(Group group) {
		Session session = factory.getCurrentSession();	
		return session.createQuery("SELECT p FROM GroupPost p WHERE p.group = :groupId").setInteger("groupId", group.getId()).list();
	}

}
