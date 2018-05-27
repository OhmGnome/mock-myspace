package com.mock.fb.beans.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mock.fb.beans.dao.PostDao;
import com.mock.fb.model.Post;
import com.mock.fb.model.User;


@Repository
@Transactional
public class PostDaoImpl implements PostDao{
	
	@Autowired
	SessionFactory factory;

	@Override
	public List<Post> getPostsByUser(User user) {
		Session session = factory.getCurrentSession();		
		Integer id = user.getId();	
		return session.createQuery("SELECT p FROM Post p WHERE p.userByPosterId = :id ORDER BY p.timestamp DESC").setInteger("id", id).list();
	}

	@Override
	public void savePost(Post post) {
		Session session = factory.getCurrentSession();
		session.save(post);
	}

	@Override
	public List<Post> getPostsByWall(User user) {
		Session session = factory.getCurrentSession();		
		Integer id = user.getId();
		return session.createQuery("SELECT DISTINCT p FROM Post p, User u, Friends f WHERE p.userByWallId = :userId AND p.userByPosterId NOT IN (SELECT p.userByPosterId FROM Friends f WHERE p.userByPosterId = f.userByFriendId AND f.userByUserId = :userId AND f.ignored = 1) ORDER BY p.timestamp DESC").setInteger("userId", id).list();
	}
	
}
