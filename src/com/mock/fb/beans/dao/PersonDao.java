package com.mock.fb.beans.dao;

import com.mock.fb.model.Person;
import com.mock.fb.model.User;

public interface PersonDao {
	public void savePerson(Person person);
	public Person getPersonFromUser(User user);
}
