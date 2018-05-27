package com.mock.fb.beans;

import java.security.NoSuchAlgorithmException;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mock.fb.model.User;



@Component
@Scope("request")
public class LoginBean {
	
	@Autowired
	private AuthenticationBean authenticationBean;
	
	@Autowired
	private ProfileBean profileBean;
	
	private Encryption encryption = new Encryption();
	
	private User user;
	private Boolean loginFailed;
	private String password;
	
	@PostConstruct
	private void init(){
		user = new User();
	}
	
	public String login(){
		String result = null;
		try {
			password = encryption.encrypt(password);
			user.setPassword(password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(authenticationBean.login(user)){
			loginFailed = false;
			result = "login-success";
			profileBean.selectUser(authenticationBean.getUser());
			
		}else{
			loginFailed = true;
			result = "login-failure";
		}
		System.out.println(result);
		return result;
	}
	
	public String register(){
		return "register";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Boolean getLoginFailed() {
		return loginFailed;
	}

	public void setLoginFailed(Boolean loginFailed) {
		this.loginFailed = loginFailed;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Encryption getEncryption() {
		return encryption;
	}

	public void setEncryption(Encryption encryption) {
		this.encryption = encryption;
	}
	
	
	
	
}

