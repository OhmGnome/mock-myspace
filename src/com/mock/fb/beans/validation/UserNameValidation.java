package com.mock.fb.beans.validation;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mock.fb.beans.dao.UserDao;

@Component
@Scope("request")
public class UserNameValidation implements Validator{
	
	@Autowired
	private UserDao userDao;

	@Override
	public void validate(FacesContext ctx, UIComponent component, Object value)
			throws ValidatorException {
		if(userDao.getUserByUsername((String) value) != null){
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, 
					"Error", 
					"Entered User Name Already Exists"));
		}
		
	}
	
	
	
}