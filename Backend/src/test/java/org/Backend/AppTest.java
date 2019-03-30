package org.Backend;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class AppTest {

	@Autowired
	private H2Test testMeH2;
	
	
	@Test
	public void testH2() {
	assertNotNull(testMeH2);
	testMeH2.callMeH2();
		
	}

}


