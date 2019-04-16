package org.Backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;


public class EditorBean extends SpringBeanAutowiringSupport  {

	private String value = "This editor is provided by PrimeFaces";
	
	@Autowired
	private Employee emp;

	public String getValue() {
		return value+" "+emp;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
