package com.mock.fb.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mock.fb.beans.dao.UserDao;
import com.mock.fb.model.User;

@Component
@Scope("session")
public class AuthenticationBean {
	
	@Autowired
	private UserDao userDao;
	
	
	private User user;
	
	//not
//	public boolean login(User user){
//		boolean isLogin = false;
//		if(user != null && user.getName() != null && user.getPassword() != null){
//			String userName = user.getName().trim();
//			String password = user.getPassword().trim();
//			if(!userName.isEmpty() && !password.isEmpty()){
//				User dbUser = userDao.getUserByUsername(userName);
//				if(dbUser != null && dbUser.getPassword().equals(password)){
//					this.user = dbUser;
//					isLogin = true;
//				}
//				
//			}
//		}
//		return isLogin;
//	}
	
	public boolean login(User user) {

		if (user == null)
			return false;
		if (user.getName() == null || user.getPassword() == null)
			return false;

		String username = user.getName().trim();
		String password = user.getPassword().trim();

		if (username.isEmpty() || password.isEmpty())
			return false;

		User dbUser = userDao.getUserByUsername(username);

		if (dbUser == null)
			return false;

		if (dbUser.getPassword().equals(password)) {
			this.user = dbUser;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isLoggedIn(){
		return user != null;
	}
	
	public String logout(){
		user = null;
		
		return "logged-out";
	}
	
	public String getUserName(){
		String userName = null;
		if(user != null){
			userName = user.getName();
		}
		
		return userName;
	}



	public User getUser() {
		return user;
	}



	public void setUser(User user) {
		this.user = user;
	}
	
}
