package org.Backend;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Unit test for simple App.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class AppTest {

	@Autowired
	private UseMe useme;

	@Autowired
	private TestInt tt;

	@Test
	public void thisShouldBeNotNull() {
		assertNotNull(useme);

	}

	@Test
	public void thisShouldBeNotNulToo() {
		assertNotNull(tt);

	}

	@Test
	public void thisShouldBeTrue() {
		assertTrue(useme.callMeBaby());

	}

	@Test
	public void thisShouldBeTrueAsWell() {

		assertTrue(tt.returnMe());

	}

}
