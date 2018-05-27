package com.mock.fb.beans.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mock.fb.beans.dao.GroupDao;
import com.mock.fb.model.Group;
import com.mock.fb.model.User;

@Repository
@Transactional
public class GroupDaoImpl implements GroupDao {
	
	@Autowired
	SessionFactory factory;

	@Override
	public void saveGroup(String groupName, User user) {
		Session session = factory.getCurrentSession();
		Group group = new Group();
		group.setName(groupName);
		group.setUser(user);
		session.save(group);
	}

	
	
	@Override
	public void deletegroupByGroupName(String groupName) {
		Session session = factory.getCurrentSession();
		Group group = (Group) session.createQuery("SELECT g FROM Group g WHERE g.name = :groupName").setString("groupName", groupName).uniqueResult();
		group.setDeleted(true);
		session.save(group);
	}

	@Override
	public Group getGroupByGroupName(String groupName) {
		Session session = factory.getCurrentSession();
		return (Group) session.createQuery("SELECT g FROM Group g WHERE g.name = :groupName").setString("groupName", groupName).uniqueResult();
	}

	@Override
	public User getOwnerByGroupName(String groupName) {
		Session session = factory.getCurrentSession();
		return (User) session.createQuery("SELECT u FROM Group g, User u WHERE u.id  = g.user AND g.name LIKE :groupName").setString("groupName", groupName).uniqueResult();
	}

	@Override
	public List<Group> getGroupsByUser(User user) {
		Session session = factory.getCurrentSession();
		Integer id = user.getId();
		return session.createQuery("SELECT g FROM Group g WHERE g.user.id = :id UNION SELECT g FROM Group g, GroupMember gm WHERE gm.user = :id AND g.id = gm.group AND gm.accept = 1 g.deleted = 0").setInteger("id", id).list();
	}
}
