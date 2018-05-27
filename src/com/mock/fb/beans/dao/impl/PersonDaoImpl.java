package com.mock.fb.beans.dao.impl;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mock.fb.beans.dao.PersonDao;
import com.mock.fb.model.Person;
import com.mock.fb.model.User;

@Repository
@Transactional
public class PersonDaoImpl implements PersonDao{
	
	@Autowired
	SessionFactory factory;
	
	@Override
	public void savePerson(Person person) {
		Session session = factory.getCurrentSession();
		session.save(person);
	}

	@Override
	public Person getPersonFromUser(User user) {
		Session session = factory.getCurrentSession();
		Person person = (Person) session.
							createQuery("FROM Person p WHERE p.id = :id").
							setInteger("id", user.getId()).
							uniqueResult();
		return person;
	}
}
