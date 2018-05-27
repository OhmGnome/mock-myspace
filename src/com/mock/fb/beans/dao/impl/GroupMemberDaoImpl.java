package com.mock.fb.beans.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mock.fb.beans.dao.GroupMemberDao;
import com.mock.fb.model.Group;
import com.mock.fb.model.GroupMember;
import com.mock.fb.model.User;

@Repository
@Transactional
public class GroupMemberDaoImpl implements GroupMemberDao {

	@Autowired
	SessionFactory factory;

	// send a group request
	@Override
	public void saveMember(User user, Group group) {
		Session session = factory.getCurrentSession();
		GroupMember begroup = new GroupMember();

		begroup.setUser(user);
		System.out.println("User is " + begroup.getUser().getName());
		begroup.setGroup(group);

		begroup.setAccept(false);
		begroup.setDealtWith(false);
		System.out.println("The group name is " + begroup.getGroup().getName());
		session.save(begroup);

		System.out.println("Waiting for the user to accept the invite "
				+ begroup.getUser().getName());
	}
	
	// accept group
	@Override
	public void setAcceptTrue(User user, Group group) {
		Session session = factory.getCurrentSession();
		GroupMember hasMember = new GroupMember();
		Query q = session
				.createQuery(
						"SELECT f FROM GroupMember f WHERE (f.user = :userId AND f.group = :groupId) AND f.accept = 0")
				.setInteger("userId", user.getId())
				.setInteger("groupId", group.getId());
		hasMember = (GroupMember) q.uniqueResult();
		System.out.println("The relationship has id " + hasMember.getId());
		hasMember.setAccept(true);
		session.update(hasMember);

	}

	@Override
	public void deleteMember(User user, Group group) {
		Session session = factory.getCurrentSession();
		GroupMember ungroup = new GroupMember();

		Query q = session
				.createQuery(
						"SELECT f FROM GroupMember f WHERE f.user = :userId AND f.group = :groupId")
				.setInteger("userId", user.getId())
				.setInteger("groupId", group.getId());
		ungroup = (GroupMember) q.uniqueResult();
		if (ungroup != null) {
			ungroup.setAccept(false);
			ungroup.setDealtWith(false);
			session.update(ungroup);
		}
		Query q1 = session
				.createQuery(
						"SELECT f FROM GroupMember f WHERE f.user = :userId AND f.group = :groupId")
				.setInteger("userId", user.getId())
				.setInteger("groupId", group.getId());
		ungroup = (GroupMember) q1.uniqueResult();
		if (ungroup != null) {
			ungroup.setAccept(false);
			ungroup.setDealtWith(false);
			session.update(ungroup);
		}
	}

	@Override
	public void declineMember(User user, Group group) {
		Session session = factory.getCurrentSession();
		GroupMember gm = new GroupMember();
		gm = (GroupMember) session
				.createQuery(
						"SELECT f FROM GroupMember f WHERE f.user = :userId AND f.group = :groupId")
				.setInteger("userId", user.getId())
				.setInteger("groupId", group.getId()).uniqueResult();
		System.out.println("Userid "+ user.getId() + " " + " GROUPiD " +group.getId() );
		gm.setDealtWith(true);
		System.out.println("The relationship accept: dealtwith "+ gm.isAccept() + " :"+gm.isDealtWith());
		session.update(gm);
	}

	@Override
	public List<User> getMembers(Group group) {
		Session session = factory.getCurrentSession();
		return (List<User>) session
				.createQuery(
						"SELECT u FROM GroupMember g, User u WHERE u.id  = g.user AND g.group = :groupId AND g.accept = 1")
				.setInteger("groupId", group.getId()).list();
	}
	@Override
	public boolean isWaiting(User user, Group group) {
		Session session = factory.getCurrentSession();
		GroupMember isWaitingMember = new GroupMember();
		Query q = session
				.createQuery(
						"SELECT f FROM GroupMember f WHERE (f.user = :userId AND f.group = :groupId) AND f.accept = 0")
				.setInteger("userId", user.getId())
				.setInteger("groupId", group.getId());
		isWaitingMember = (GroupMember) q.uniqueResult();
		if (isWaitingMember != null) {
			System.out.println("isWaitingMember DaoImpl not null");
			return true;
		} else {
			System.out.println("isWaitingMember DaoImpl null");
			return false;
		}
	}

	@Override
	public List<Group> notMembers(User user) {
		Session session = factory.getCurrentSession();
		Query q = session
				.createQuery(
						"SELECT u FROM Group u, GroupMember f WHERE (u = f.group AND f.user = :userId) AND f.accept = 0 AND f.dealtWith = 0 AND u.deleted = 0")
				.setInteger("userId", user.getId());
		List<Group> result = q.list();
		System.out
				.println("------------------------------>Print users groups requesst");

		return result;
	}

}
