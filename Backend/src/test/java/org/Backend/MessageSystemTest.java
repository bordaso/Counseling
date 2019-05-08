package org.Backend;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.NotSupportedException;

import org.Backend.Config.Config;
import org.Backend.DAOs.BookingDetailsDao;
import org.Backend.DAOs.BookingsDao;
import org.Backend.DAOs.EmployeeDao;
import org.Backend.DAOs.MessageDao;
import org.Backend.DAOs.MessageDetailsDao;
import org.Backend.DAOs.NotificationSetupDao;
import org.Backend.DAOs.PatientDao;
import org.Backend.Entities.BookingDetails;
import org.Backend.Entities.Bookings;
import org.Backend.Entities.Employee;
import org.Backend.Entities.Message;
import org.Backend.Entities.MessageDetails;
import org.Backend.Entities.NotificationSetup;
import org.Backend.Entities.Patient;
import org.Backend.Enums.BookingResponse;
import org.Backend.Utilities.ReportCreator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.itextpdf.text.DocumentException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
@WebAppConfiguration
public class MessageSystemTest {

	@Autowired
	private MessageDao msgDao;

	@Autowired
	private MessageDetailsDao msgDtlsDao;

	@Autowired
	private EmployeeDao empDao;

	@Autowired
	private PatientDao patDao;

	@Autowired
	private Message msg1;

	@Autowired
	private Message msg2SendingToMultiplePatient;

	@Autowired
	private Message msg3ReplyToMsg1;

	@Autowired
	private MessageDetails msgDtls1;

	@Autowired
	private MessageDetails msgDtls2;

	@Autowired
	private MessageDetails msgDtls3;

	@Autowired
	private MessageDetails msgDtls4;

	@Autowired
	private Employee empBoss;

	@Autowired
	private Employee emp;

	@Autowired
	private Patient pat1;

	@Autowired
	private Patient pat2;

	private List<MessageDetails> listOfMessageDetails;

	private byte[] attachment;

	private List<BookingDetails> listOfBookingDetails;
	@Autowired
	private Bookings bkngs1;
	@Autowired
	private BookingDetails bkngs1_bkngsDtls1;
	@Autowired
	private BookingDetails bkngs1_bkngsDtls2;
	@Autowired
	private BookingDetails bkngs1_bkngsDtls3;
	@Autowired
	private NotificationSetup bkngs1_ntfsnStp1;
	@Autowired
	private ReportCreator reportCreator;

	@Before
	public void createAttachment() throws DocumentException, URISyntaxException, IOException {
		bkngs1.setTitle("booking1");
		bkngs1.setStart(LocalDateTime.of(2019, Month.APRIL, 11, 14, 00));
		bkngs1.setEnd(LocalDateTime.of(2019, Month.APRIL, 11, 15, 00));
		bkngs1.setRoom("room1");
		bkngs1.setArchived(false);
		bkngs1_bkngsDtls1.setBooking(bkngs1);
		bkngs1_bkngsDtls1.setEmployee(empBoss);
		bkngs1_bkngsDtls1.setPatient(null);
		bkngs1_bkngsDtls2.setBooking(bkngs1);
		bkngs1_bkngsDtls2.setEmployee(emp);
		bkngs1_bkngsDtls2.setPatient(null);
		bkngs1_bkngsDtls2.setResponse(BookingResponse.ACCEPTED);
		bkngs1_bkngsDtls3.setBooking(bkngs1);
		bkngs1_bkngsDtls3.setEmployee(null);
		bkngs1_bkngsDtls3.setPatient(pat1);
		bkngs1_bkngsDtls3.setResponse(BookingResponse.ACCEPTED);
		bkngs1_ntfsnStp1.setRecurringTime(3000l);
		bkngs1_ntfsnStp1.setContent("notification content for bk1");
		bkngs1_ntfsnStp1.setEmail(true);
		listOfBookingDetails = new ArrayList<BookingDetails>();
		listOfBookingDetails.add(bkngs1_bkngsDtls1);
		listOfBookingDetails.add(bkngs1_bkngsDtls2);
		listOfBookingDetails.add(bkngs1_bkngsDtls3);
		bkngs1.setNotificationId(bkngs1_ntfsnStp1);
		bkngs1_ntfsnStp1.setBoookingNotifId(bkngs1);
		bkngs1_bkngsDtls1.setBooking(bkngs1);
		bkngs1_bkngsDtls2.setBooking(bkngs1);
		bkngs1_bkngsDtls3.setBooking(bkngs1);

		attachment = reportCreator.retrieveAsByte(listOfBookingDetails, emp,
				"We had a great conversation, and lorem ipsum and lorem ipsum and lorem ipsum and lorem ipsum and lorem ipsum and lorem ipsum and lorem ipsum and lorem ipsum and lorem ipsum.");
	}

	@Before
	public void setupMsg1() {
		msg1.setSender(empBoss);
		msg1.setSentAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		msg1.setContent("Hello employeee, its great to have you");
	}

	@Before
	public void setupMsg2SendingToMultiplePatient() {
		msg2SendingToMultiplePatient.setSender(emp);
		msg2SendingToMultiplePatient.setSentAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		msg2SendingToMultiplePatient.setContent("Hello patients, thank you for comming to an appointment");
	}

	@Before
	public void setupMsg3ReplyToMsg1() {
		msg3ReplyToMsg1.setSender(emp);
		msg3ReplyToMsg1.setSentAt(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
		msg3ReplyToMsg1.setAttachment(attachment);
		msg3ReplyToMsg1.setContent("thanks, latest appointment report is attached, now, payme more");
	}

	@Before
	public void setupMsgDtls1() {
		msgDtls1.setMsgId(msg1);
		msgDtls1.setReceiver(emp);
	}

	@Before
	public void setupMsgDtls2() {
		msgDtls2.setMsgId(msg2SendingToMultiplePatient);
		msgDtls2.setReceiver(pat1);
	}

	@Before
	public void setupMsgDtls3() {
		msgDtls3.setMsgId(msg2SendingToMultiplePatient);
		msgDtls3.setReceiver(pat2);
	}

	@Before
	public void setupMsgDtls4() {
		msgDtls4.setMsgId(msg3ReplyToMsg1);
		msgDtls4.setReceiver(empBoss);
	}

	@Before
	public void setuplistOfMessageDetails() {
		listOfMessageDetails = new ArrayList<MessageDetails>();
		listOfMessageDetails.add(msgDtls1);
		listOfMessageDetails.add(msgDtls2);
		listOfMessageDetails.add(msgDtls3);
		listOfMessageDetails.add(msgDtls4);
	}

	@Before
	public void setupEmpBoss() {
		empBoss.setName("testEmpBoss");
		empBoss.setEmail("testBoss@testBoss.hu");
		empBoss.setUsername("testerBoss");
		empBoss.setPassword("abc123Boss");
		empBoss.setPersonalId("emp01Boss");
	}

	@Before
	public void setupEmp() {
		emp.setName("testEmp");
		emp.setEmail("test@test.hu");
		emp.setUsername("tester");
		emp.setPassword("abc123");
		emp.setPersonalId("emp01");
		emp.setPhoneNumber(123l);
		emp.setReportsTo(empBoss);
	}

	@Before
	public void setupPat1() {
		pat1.setName("testPat1");
		pat1.setEmail("testPat1@testPat1.hu");
		pat1.setUsername("testerPat1");
		pat1.setPassword("abc1231");
		pat1.setMedicalId(1231l);
	}

	@Before
	public void setupPat2() {
		pat2.setName("testPat2");
		pat2.setEmail("testPat2@testPat2.hu");
		pat2.setUsername("testerPat2");
		pat2.setPassword("abc1232");
		pat2.setMedicalId(1232l);
	}

	@Before
	@After
	public void clearTables() {
		assertNotNull(empDao);
		assertNotNull(patDao);
		assertNotNull(msgDao);
		assertNotNull(msgDtlsDao);
		assertNotNull(msg1);
		assertNotNull(msg2SendingToMultiplePatient);
		assertNotNull(msgDtls1);
		assertNotNull(msgDtls2);
		assertNotNull(msgDtls3);
		assertNotNull(msgDtls4);

		msgDtlsDao.clearMessageDetails();
		msgDao.clearMessage();
		empDao.clearEmployee();
		patDao.clearPatient();

		listOfMessageDetails.clear();
	}

	@Test
	public void testAutowired() {
		assertNotNull(empDao);
		assertNotNull(patDao);
		assertNotNull(msgDao);
		assertNotNull(msgDtlsDao);
		assertNotNull(msg1);
		assertNotNull(msg2SendingToMultiplePatient);
		assertNotNull(msg3ReplyToMsg1);
		assertNotNull(msgDtls1);
		assertNotNull(msgDtls2);
		assertNotNull(msgDtls3);
		assertNotNull(msgDtls4);
		assertNotNull(emp);
		assertNotNull(empBoss);
		assertNotNull(pat1);
		assertNotNull(pat2);
	}

	@Test
	public void testDaoSaveMsg() {
		assertNotNull(msgDao);
		assertTrue(msgDao.selectAllMessage().isEmpty());
		msgDao.saveMessage(msg1);
		assertTrue(msgDao.selectAllMessage().size() == 1);
	}

	@Test
	public void testDaoSaveMsgDtls() {
		assertNotNull(msgDtlsDao);
		assertTrue(msgDtlsDao.selectAllMessageDetails().isEmpty());
		msgDtlsDao.saveMessageDetails(msgDtls1);
		assertTrue(msgDtlsDao.selectAllMessageDetails().size() == 1);
	}

	@Test
	public void testDaoUpdateMsg() {
		assertNotNull(msgDao);
		assertTrue(msgDao.selectAllMessage().isEmpty());
		msgDao.saveMessage(msg1);
		assertTrue(msgDao.selectAllMessage().size() == 1);

		assertTrue(msgDao.selectAllMessage().get(0).getAttachment() == null);

		msg1.setAttachment(attachment);
		msgDao.updateMessage(msg1);

		assertTrue(msgDao.selectAllMessage().get(0).getAttachment() != null);
	}

	@Test
	public void testDaoUpdateMsgDtls() {
		assertNotNull(msgDtlsDao);
		assertTrue(msgDtlsDao.selectAllMessageDetails().isEmpty());
		msgDtlsDao.saveMessageDetails(msgDtls1);
		assertTrue(msgDtlsDao.selectAllMessageDetails().size() == 1);

		assertTrue(msgDtlsDao.selectAllMessageDetails().get(0).getMsgId().equals(msg1) );

		msgDtls1.setMsgId(msg2SendingToMultiplePatient);
		msgDtlsDao.updateMessageDetails(msgDtls1);

		assertTrue(msgDtlsDao.selectAllMessageDetails().get(0).getMsgId().equals(msg2SendingToMultiplePatient) );
}
	
	@Test(expected = NotSupportedException.class)
	public void testDaoDeleteMsgById() {
		assertNotNull(msgDao);
		assertTrue(msgDao.selectAllMessage().isEmpty());
		msgDao.saveMessage(msg1);
		assertTrue(msgDao.selectAllMessage().size() == 1);
		
		final long msg1Id = msg1.getId();
		msgDao.deleteMessage(msg1Id);
	}

	@Test
	public void testDaoDeleteMsgDtlsById() {
		assertNotNull(msgDtlsDao);
		assertTrue(msgDtlsDao.selectAllMessageDetails().isEmpty());
		msgDtlsDao.saveMessageDetails(msgDtls1);
		assertTrue(msgDtlsDao.selectAllMessageDetails().size() == 1);
		
		assertTrue(msgDtlsDao.selectAllMessageDetails().get(0).isArchived() == false);
		
		final long msgDtls1Id = msgDtls1.getId();
		msgDtlsDao.deleteMessageDetailsById(msgDtls1Id);
		
		assertTrue(msgDtlsDao.selectAllMessageDetails().get(0).isArchived() == true);		
	}
	
	@Test
	public void testDaoDeleteMsgDtlsByMsgId() {
		assertNotNull(msgDtlsDao);
		assertTrue(msgDtlsDao.selectAllMessageDetails().isEmpty());
		msgDtlsDao.saveMessageDetails(msgDtls1);
		msgDtlsDao.saveMessageDetails(msgDtls2);
		msgDtlsDao.saveMessageDetails(msgDtls3);
		msgDtlsDao.saveMessageDetails(msgDtls4);
		assertTrue(msgDtlsDao.selectAllMessageDetails().size() == 4);
		
		List<MessageDetails> listOfMsgDtls1 = msgDtlsDao.selectAllMessageDetails();		
		for(MessageDetails msgDtls : listOfMsgDtls1) {
			assertTrue(msgDtls.isArchived() == false);
		}
		
		msgDtlsDao.deleteMessageDetailsByMsgId(msgDtls2.getMsgId());
		
		int i = 0;
		
		List<MessageDetails> listOfMsgDtls2 = msgDtlsDao.selectAllMessageDetails();		
		for(MessageDetails msgDtls : listOfMsgDtls2) {
			if(msgDtls.getMsgId().equals(msgDtls2.getMsgId())) {
				assertTrue(msgDtls.isArchived() == true);
				i++;
				continue;
			}	
			assertTrue(msgDtls.isArchived() == false);
		}
		
		assertTrue(i==2);		
	}
	
	@Test
	public void testDaoSelectMessageById() {
		assertNotNull(msgDao);
		assertTrue(msgDao.selectAllMessage().isEmpty());
		msgDao.saveMessage(msg1);
		msgDao.saveMessage(msg2SendingToMultiplePatient);
		
		final long msg1Id = msg1.getId();		
		assertTrue(msg1.equals(msgDao.selectMessageById(msg1Id)));
		
		assertTrue(msgDao.selectAllMessage().size() == 2);
	}

	@Test
	public void testDaoSelectMessageDetailsById() {
		assertNotNull(msgDtlsDao);
		assertTrue(msgDtlsDao.selectAllMessageDetails().isEmpty());
		msgDtlsDao.saveMessageDetails(msgDtls1);
		msgDtlsDao.saveMessageDetails(msgDtls2);
		
		final long msgDtls1Id = msgDtls1.getId();		
		assertTrue(msgDtls1.equals(msgDtlsDao.selectMessageDetailsById(msgDtls1Id)));
		
		assertTrue(msgDtlsDao.selectAllMessageDetails().size() == 2);
	}
	
	@Test
	public void testDaoSelectMessageByReplyToId() {
		assertNotNull(msgDao);
		assertTrue(msgDao.selectAllMessage().isEmpty());
		
		msgDao.saveMessage(msg1);
		
		msg2SendingToMultiplePatient.setReplyToId(msg1.getId());		
		msgDao.saveMessage(msg2SendingToMultiplePatient);
		
		msg3ReplyToMsg1.setReplyToId(msg1.getId());
		msgDao.saveMessage(msg3ReplyToMsg1);
				
		assertTrue(msgDao.selectAllMessage().size() == 3);
		
		assertTrue(msgDao.selectMessageByReplyToId(msg1.getId()).size()==2);
		
	}

	@Test
	public void testDaoSelectMessageDetailsByMsgId() {
		assertNotNull(msgDtlsDao);
		assertTrue(msgDtlsDao.selectAllMessageDetails().isEmpty());
		msgDtlsDao.saveMessageDetails(msgDtls1);
		msgDtlsDao.saveMessageDetails(msgDtls2);
		msgDtlsDao.saveMessageDetails(msgDtls3);
		msgDtlsDao.saveMessageDetails(msgDtls4);
		assertTrue(msgDtlsDao.selectAllMessageDetails().size() == 4);
		
		assertTrue(msgDtlsDao.selectMessageDetailsByMsgId(msgDtls2.getMsgId()).size()==2);	
	}
	
	@Test
	public void testDaoSelectMessageBySender() {
		assertNotNull(msgDao);
		assertTrue(msgDao.selectAllMessage().isEmpty());
		msgDao.saveMessage(msg1);
		msgDao.saveMessage(msg2SendingToMultiplePatient);
		msgDao.saveMessage(msg3ReplyToMsg1);
		
		assertTrue(msgDao.selectAllMessage().size() == 3);
		
		assertTrue(msgDao.selectMessageBySender(emp).size() == 2);
	}

	@Test
	public void testDaoSelectMessageeDetailsByReceiver() {
		assertNotNull(msgDtlsDao);
		assertTrue(msgDtlsDao.selectAllMessageDetails().isEmpty());
		msgDtlsDao.saveMessageDetails(msgDtls1);
		msgDtlsDao.saveMessageDetails(msgDtls2);
		msgDtls3.setReceiver(empBoss);
		msgDtlsDao.saveMessageDetails(msgDtls3);
		msgDtlsDao.saveMessageDetails(msgDtls4);
		
		assertTrue(msgDtlsDao.selectAllMessageDetails().size() == 4);
		
		assertTrue(msgDtlsDao.selectMessageeDetailsByReceiver(empBoss).size() == 2);
	}

}
