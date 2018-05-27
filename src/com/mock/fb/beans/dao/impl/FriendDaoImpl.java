package com.mock.fb.beans.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import com.mock.fb.beans.dao.FriendDao;
import com.mock.fb.model.Friends;
import com.mock.fb.model.User;

@Repository
@Transactional
public class FriendDaoImpl implements FriendDao {

	@Autowired
	SessionFactory factory;

	//send a friend request
	@Override
	public void saveFriend(User user, User friend) {
		Session session = factory.getCurrentSession();
		Friends befriend = new Friends();
		Query q = session
				.createQuery(
						"SELECT f FROM Friends f WHERE (f.userByUserId = :userId AND f.userByFriendId = :friendId) AND f.accept = 0")
				.setInteger("userId", user.getId())
				.setInteger("friendId", friend.getId());
		
		if((Friends) q.uniqueResult()== null){
			System.out.println("Relationship doesn't exist yet " + user.getName() +" "+ friend.getName());
			befriend.setUserByUserId(user);
			befriend.setUserByFriendId(friend);
			befriend.setAccept(false);
			befriend.setDealtWith(true);
			session.save(befriend);
			System.out.println("In friendDaoImpl, session.save success");
		} else{
			System.out.println("Relathion already exits");
			befriend =(Friends) q.uniqueResult(); 
			befriend.setAccept(false);
			befriend.setDealtWith(true);
			session.update(befriend);
		}
		
	}
	
	//accept friend
	@Override
	public void setAcceptTrue(User user, User friend) {
		Session session = factory.getCurrentSession();
		Friends hasFriend = new Friends();
		Query q = session
				.createQuery(
						"SELECT f FROM Friends f WHERE (f.userByUserId = :userId AND f.userByFriendId = :friendId) AND f.accept = 0")
				.setInteger("userId", user.getId())
				.setInteger("friendId", friend.getId());
		hasFriend = (Friends) q.uniqueResult();
		System.out.println("The relationship has id "+ hasFriend.getId());
		hasFriend.setAccept(true);
		session.update(hasFriend);
		
	}

	@Override
	public void deleteFriend(User user, User friend) {
		Session session = factory.getCurrentSession();
		Friends unfriend = new Friends();

		Query q = session
				.createQuery(
						"SELECT f FROM Friends f WHERE f.userByUserId = :userId AND f.userByFriendId = :friendId")
				.setInteger("userId", user.getId())
				.setInteger("friendId", friend.getId());
		unfriend = (Friends) q.uniqueResult();
		if(unfriend!=null){
			unfriend.setAccept(false);
			unfriend.setDealtWith(false);
			session.update(unfriend);
			session.update(unfriend);
		}
		Query q1 = session
				.createQuery(
						"SELECT f FROM Friends f WHERE f.userByFriendId = :userId AND f.userByUserId = :friendId")
				.setInteger("userId", user.getId())
				.setInteger("friendId", friend.getId());
		unfriend = (Friends) q1.uniqueResult();
		if(unfriend!=null){
			unfriend.setAccept(false);
			unfriend.setDealtWith(false);
			session.update(unfriend);
			session.update(unfriend);
		}
	}
	
	@Override
	public List<User> getFriends() {
		Session session = factory.getCurrentSession();
		return session.createQuery("FROM User").list();
	}

	@Override
	public List<User> getFriendsByUser(User user) {
		Session session = factory.getCurrentSession();
		Query q = session
				.createQuery(
						"SELECT u FROM User u LEFT JOIN FETCH u.friendsesForFriendId As uf LEFT JOIN FETCH u.friendsesForUserId As fu WHERE (uf.userByUserId = :userId AND uf.accept = 1) OR (fu.userByFriendId = :userId AND uf.accept = 1)")
				.setInteger("userId", user.getId());
		List<User> result = q.list();
		return result;
	}

	@Override
	public boolean isFriend(User user, User friend) {
		Session session = factory.getCurrentSession();
		Friends hasFriend = new Friends();
		Query q = session
				.createQuery(
						"SELECT f FROM Friends f WHERE (f.userByUserId = :userId AND f.userByFriendId = :friendId) AND f.accept = 1")
				.setInteger("userId", user.getId())
				.setInteger("friendId", friend.getId());
		hasFriend = (Friends) q.uniqueResult();
		if (hasFriend != null) {
			System.out.println("isFriend DaoImpl not null");
			return true;
		} else {
			System.out.println("isFriend DaoImpl null");
			return false;
		}
	}

	@Override
	public boolean isWaiting(User user, User friend) {
		Session session = factory.getCurrentSession();
		Friends isWaitingFriend = new Friends();
		Query q = session
				.createQuery(
						"SELECT f FROM Friends f WHERE (f.userByUserId = :userId AND f.userByFriendId = :friendId) AND f.accept = 0 AND f.dealtWith = 1")
				.setInteger("userId", user.getId())
				.setInteger("friendId", friend.getId());
		isWaitingFriend = (Friends) q.uniqueResult();
		if (isWaitingFriend != null) {
			System.out.println("isWaitingFriend DaoImpl not null");
			return true;
		} else {
			System.out.println("isWaitingFriend DaoImpl null");
			return false;
		}
	}
	@Override
	public List<User> notFriends(User user) {
		Session session = factory.getCurrentSession();
		Query q = session
				.createQuery(
						"SELECT u FROM User u, Friends f WHERE (u = f.userByFriendId AND f.userByUserId  = :userId) AND f.accept = 0 AND f.dealtWith = 1")
				.setInteger("userId", user.getId());
		List<User> result = q.list();
		System.out.println("------------------------------>Print users friends requesst");
		for(User a : result){
			
			System.out.println(a.getName());
		}
		return result;
	}


	
	/** Ignore Friend*/
	@Override
	public void ignoreFriend(User user, User friend) {
		Session session = factory.getCurrentSession();
		Friends hasFriend = new Friends();
		Query q = session
				.createQuery(
						"SELECT f FROM Friends f WHERE f.userByUserId = :userId AND f.userByFriendId = :friendId")
				.setInteger("userId", user.getId())
				.setInteger("friendId", friend.getId());
		
		if((Friends) q.uniqueResult()!=null){	
			hasFriend = (Friends) q.uniqueResult();
			hasFriend.setIgnored(true);
			session.update(hasFriend);
		} else{
			hasFriend.setUserByUserId(user);
			hasFriend.setUserByFriendId(friend);
			hasFriend.setIgnored(true);
			hasFriend.setAccept(false);
			hasFriend.setDealtWith(false);
			session.save(hasFriend);
		}
	}

	@Override
	public boolean isIgnored(User user, User friend) {
		Session session = factory.getCurrentSession();
		Friends isIgnored = new Friends();
		Query q = session
				.createQuery(
						"SELECT f FROM Friends f WHERE (f.userByUserId = :userId AND f.userByFriendId = :friendId) AND f.ignored = 1")
				.setInteger("userId", user.getId())
				.setInteger("friendId", friend.getId());
		isIgnored = (Friends) q.uniqueResult();
		if (isIgnored != null) {
			System.out.println("You are ignored");
			return true;
		} else {
			System.out.println("You are not ignored");
			return false;
		}
	}
}
