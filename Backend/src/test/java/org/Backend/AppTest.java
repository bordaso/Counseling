package org.Backend;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

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
	
		@Autowired
		private EmpDao dao;
		
		@Autowired
		private Employee emp;
		
		@Before
		public void setup() {
			emp.setName("testEmp");
		} 
		
		@Test
		public void testH2() {
		assertNotNull(testMeH2);
		testMeH2.callMeH2();			
		}
	 
		@Test
		public void testDaoSave() {
			assertNotNull(dao);		
			dao.saveEmployee(emp);
			assertNotNull(dao.selectEmployee("testEmp").get(0));
			assertTrue(dao.selectEmployee("testEmp").get(0).getName().equals("testEmp"));
			}
		
		@Test
		public void testDaoDrop() {
			assertNotNull(dao);
			assertTrue(dao.selectEmployee("testEmp").isEmpty());
			dao.saveEmployee(emp);	
			assertNotNull(dao.selectEmployee("testEmp").get(0));
			assertTrue(dao.deleteEmployee("testEmp")==0);
			}
		
		@Test
		public void testDaoUpdate() {
			assertNotNull(dao);
			assertTrue(dao.selectEmployee("testEmp").isEmpty());
			dao.saveEmployee(emp);
			assertFalse(dao.selectEmployee("testEmp").isEmpty());
			dao.updateEmployee("testEmp", "NAME_UPDATED");
			assertFalse(dao.selectEmployee("NAME_UPDATED").isEmpty());
			assertTrue(dao.selectEmployee("NAME_UPDATED").get(0).getName().equals("NAME_UPDATED"));
			}
		
		 
	
	
}


