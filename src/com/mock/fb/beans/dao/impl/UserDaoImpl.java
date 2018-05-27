package com.mock.fb.beans.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mock.fb.beans.dao.UserDao;
import com.mock.fb.model.User;



@Repository
@Transactional
public class UserDaoImpl implements UserDao{
	
Logger log = LoggerFactory.getLogger(UserDaoImpl.class);
	
	@Autowired
	private SessionFactory factory;

	@Override
	public void saveUser(User user) {
		Session session = factory.getCurrentSession();
			
			try {
				session.save(user);
				
				log.debug("saveUser saved");
			} catch (Exception e) {
				//log
				e.printStackTrace();
				log.debug("saveUser user not saved");
			}
				
			log.trace("leaving saveUser");
	}

	
	@Override
	public User getUserByUsername(String username) {
		Session session = factory.getCurrentSession();
		return (User)  session.createQuery("SELECT u FROM User u WHERE u.name LIKE :username").setString("username", username).uniqueResult();
	}


	@Override
	public List<User> getUsersFromSearch(String search) {
			List<User> result = null;
			Session session = factory.getCurrentSession();
				Query q = session
						.createQuery(
								"FROM User u WHERE u.name LIKE :search")
						.setString("search", String.format("%%%s%%", search));
				result = q.list();
			return ((result != null)? result : new ArrayList<User>());
		}
	

	@Override
	public List<User> getUsers(User user) {
		Session session = factory.getCurrentSession();
		return session.createQuery("SELECT DISTINCT u, u.name FROM User u WHERE u.id NOT IN (SELECT u.id FROM Friends f WHERE u.id = f.userByFriendId AND f.userByUserId = :userId AND f.ignored = 1)").setInteger("userId", user.getId()).list();
	}
}