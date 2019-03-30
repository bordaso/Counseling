package org.Backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UseMe {
	
	private TestInt tt;
	
	@Autowired
	public void setTT(TestInt in) {
		tt=in;
	}
	
	public boolean callMeBaby() {
		
		return tt.returnMe();
	}

}
