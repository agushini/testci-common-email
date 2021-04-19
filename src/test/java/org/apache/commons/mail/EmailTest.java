package org.apache.commons.mail;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import java.nio.charset.IllegalCharsetNameException;
import java.util.Date;
public class EmailTest {
	
	private static final String[] TEST_EMAILS = {"ab@bc.com", "a.b@c.org",
	"abcdefghijklmnopqrst@abcdefghijklmnopqrst.com.bd"};
	//private static final String INVALID_EMAIL = "###";
	private static final String VALID_EMAIL = "abc@def.com";
	private static final String EMPTY = "";
	private static final String NAME = "asdasd";
	private static final String MESSAGE = "this is a message";
	
	//email concrete class for testing
	private EmailConcrete email;
	
	@Before
	public void setUpEmailTest() throws Exception {
	email = new EmailConcrete();
	}
	
	@After
	public void tearDownEmailTest() throws Exception{
	
	}
	
	@Test //test addBcc(String...)
	public void testAddBcc() throws Exception {
		
		email.addBcc(TEST_EMAILS);
		
		assertEquals(3,email.getBccAddresses().size());
	}

	@Test  //test addCC(String...)
	public void testAddCcEmails() throws Exception {
		email.addCc(TEST_EMAILS);
		
		assertEquals(3,email.getCcAddresses().size());
	}
	
	
	@Test //Test addCc(String)
	public void testAddCcValid() throws Exception {
		email.addCc(VALID_EMAIL);
		
		assertEquals(1,email.getCcAddresses().size());
	}
	
	@Test //Test addHeader(String String)
	public void testAddHeaderValid() throws Exception {
		
		email.addHeader(VALID_EMAIL, VALID_EMAIL);
		
		assertEquals(1, email.headers.size());
	}
	
	@Test (expected = IllegalArgumentException.class)//Test addHeader(String String) name empty
	public void testAddHeaderEmpty() throws Exception {
		email.addHeader(EMPTY, VALID_EMAIL);
		
		assertEquals(null, email.headers.size());
	}
	
	@Test (expected = IllegalArgumentException.class)//Test addHeader(String String) value empty
	public void testAddHeaderEmpty2() throws Exception {
		email.addHeader(VALID_EMAIL, EMPTY);
		
		assertEquals(null, email.headers.size());
	}
	
	@Test //test addReplyTo(string email string name)
	public void testAddReplyTo() throws Exception {
		email.addReplyTo(VALID_EMAIL, NAME);
		
		assertEquals(1, email.getReplyToAddresses().size());
	}
	
	@Test (expected = EmailException.class)//test buildMimeMessage() with pop
	public void testBuildMimeMessage() throws Exception {
		
		email.setHostName("localhost");
		email.setSmtpPort(0123);
		email.setFrom("ab@cd.com");
		email.addTo("ef@gh.com");
		email.setSubject("subject");
		email.setCharset("ISO-8859-1");
		email.setContent("test content", "test/plain" );
		email.addCc("ab@cd.com");
		email.addBcc("ef@gf.com");
		email.addHeader("abc", "123");
		email.setPopBeforeSmtp(true, "localhost", "username", "password");
		email.addReplyTo(VALID_EMAIL,NAME);
		
		
		email.buildMimeMessage();
		
		//assertEquals(null,email.getMimeMessage());
	}
	
	@Test //(expected = EmailException.class)//test buildMimeMessage() null content
	public void testBuildMimeMessage2() throws Exception {
		
		email.setHostName("localhost");
		email.setSmtpPort(0123);
		email.setFrom("ab@cd.com");
		email.addTo("ef@gh.com");
		email.setSubject("subject");
		email.setCharset("ISO-8859-1");
		email.setContent(null, null );
		email.addCc("ab@cd.com");
		email.addBcc("ef@gf.com");
		email.addHeader("abc", "123");
		email.addReplyTo(VALID_EMAIL,NAME);
		
		
		email.buildMimeMessage();
		
		
	}
	
	@Test (expected = IllegalCharsetNameException.class)//test buildMimeMessage() empty subject
	public void testBuildMimeMessage3() throws Exception {
		
		email.setHostName("localhost");
		email.setSmtpPort(0123);
		email.setFrom("ab@cd.com");
		email.addTo("ef@gh.com");
		email.setSubject("subject");
		email.setCharset(EMPTY);
		email.setContent(null, null);
		email.addCc("ab@cd.com");
		email.addBcc("ef@gf.com");
		email.addHeader("abc", "123");
		email.addReplyTo(VALID_EMAIL,NAME);
		
		email.buildMimeMessage();
		
	}
	
	@Test //test getHostName() valid
	public void testGetHostName() throws Exception {
		email.setHostName("localhost");
		
		assertEquals("localhost", email.getHostName());
	}
	
	@Test //test getHostName() null
	public void testGetHostNameNull() throws Exception {
		email.setHostName(null);
		
		assertEquals(null, email.getHostName());
	}
	
	@Test (expected = EmailException.class)//test getMailSession() empty hostname
	public void testGetMailSessionEmpty() throws Exception {
		email.setHostName(EMPTY);
		
		email.getMailSession();
	}
	
	@Test //test getSentDate()
	public void testGetSentDate() throws Exception {
		Date todaysDate = new Date();
		email.setSentDate(todaysDate);
		
		assertEquals(todaysDate, email.getSentDate());
	}
	
	@Test //test getSocketConnectionTimeout()
	public void testGetSocketConnection() throws Exception{
		int socketTimeout = email.getSocketTimeout();
		assertEquals(socketTimeout, email.getSocketConnectionTimeout());
	}
	
	@Test (expected = EmailException.class)//test setfrom(string email)
	public void testSetFrom() throws Exception{
		email.setFrom(EMPTY, NAME , "ISO-8859-1");
		assertEquals(EMPTY, email.setFrom(EMPTY));
	}
	
}
