/*
 * expvvt.util.mail.MailManager
 * 
 * Criado em 24/08/2005.
 */
package com.ironiacorp.commons;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 
 */
public final class MailUtil
{
	public static final String SMTP_PROPERTY = "mail.smtp.host";
	
	public static void sendMail(String server, String from, String to, String subject, String content)
	throws AddressException, MessagingException
	{
		Properties props = new Properties();
		props.put(SMTP_PROPERTY, server);
		Session session = Session.getDefaultInstance(props);
		sendMail(session, from, to, subject, content);
	}
	
	public static void sendMail(Session session, String from, String to, String subject, String content)
	throws AddressException, MessagingException
	{
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(from));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		msg.setContent(content, "text/html");
		msg.setSentDate(new Date());
		sendMail(msg);
	}
	
	public static void sendMail(Message msg, String from, String to, String content)
	throws AddressException, MessagingException
	{
		msg.setFrom(new InternetAddress(from));
		msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		msg.setContent(content, "text/html");
		msg.setSentDate(new Date());
		sendMail(msg);
	}
	
	/**
	 * 
	 * @param msg
	 * @throws MessagingException
	 */
	public static void sendMail(Message msg) throws MessagingException
	{
		msg.setSentDate(new Date());
		Transport.send(msg);
	}
}
