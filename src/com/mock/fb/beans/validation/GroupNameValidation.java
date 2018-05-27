package com.mock.fb.beans.validation;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.mock.fb.beans.dao.GroupDao;

@Component
@Scope("request")
public class GroupNameValidation implements Validator {

	@Autowired
	private GroupDao groupDao;

	@Override
	public void validate(FacesContext ctx, UIComponent component, Object value)
			throws ValidatorException {
		if (groupDao.getGroupByGroupName((String) value) != null) {
			throw new ValidatorException(new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Error",
					"Entered Group Name Already Exists"));
		}

	}

}
