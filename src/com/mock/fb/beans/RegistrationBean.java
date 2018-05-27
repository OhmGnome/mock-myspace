package com.mock.fb.beans;

import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mock.fb.beans.dao.PersonDao;
import com.mock.fb.beans.dao.UserDao;
import com.mock.fb.model.Person;
import com.mock.fb.model.User;

@Component
@Scope("request")
public class RegistrationBean {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PersonDao personDao;

	private Encryption encryption = new Encryption();

	private User user;

	private Person person;

	private String password;

	@PostConstruct
	public void init() {
		user = new User();
		person = new Person();
	}

	public String register() {
		String result = null;
		try {
			user.setPassword(encryption.encrypt(password));
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			userDao.saveUser(user);
			person.setUser(user);
			
			personDao.savePerson(person);
			result = "registration-success";
		} catch (Throwable t) {
			t.printStackTrace();
			result = "registration-failure";
		}

		return result;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
