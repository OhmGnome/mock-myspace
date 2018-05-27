package com.mock.fb.beans.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import com.mock.fb.beans.AuthenticationBean;

@Component
@Scope("request")
public class AuthenticationFilter extends GenericFilterBean {

	@Autowired
	private AuthenticationBean auth;
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		if(auth.isLoggedIn()){
			chain.doFilter(request, response);
		}else{
			if(response instanceof HttpServletResponse){
				((HttpServletResponse) response).sendRedirect(String.format("%s/login.xhtml", ((HttpServletRequest) request).getContextPath()));
			}
		}

	}
	
	public String logout(){
		auth.logout();
		return "logged-out";
	}

}
