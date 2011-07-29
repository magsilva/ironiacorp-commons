/*
 * Copyright (C) 2011 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.ironiacorp.email;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public final class JavaMailEmailDispatcher implements EmailDispatcher
{
	public enum ServerConnectionType {
		PLAIN(25, "smtp"),
		TLS(587, "smtp"),
		SSL(465, "smtps");
		
		public final int port;
		
		public final String transportProtocol;
		
		private ServerConnectionType(int port, String transportProtocol)
		{
			this.port = port;
			this.transportProtocol = transportProtocol;
		}
	}
	
	public static final ServerConnectionType DEFAULT_SERVER_CONNECTION_TYPE = ServerConnectionType.PLAIN;
	
	private ServerConnectionType connectionType = DEFAULT_SERVER_CONNECTION_TYPE;
	
	private String transportProtocol;
	
	private String serverName;
	
	private int serverPort;
	
	private String username = "";
	
	private String password = "";
	
	private boolean authenticationRequired;

	/* (non-Javadoc)
	 * @see com.ironiacorp.email.EmailDispatcher#getServerName()
	 */
	@Override
	public String getServerName()
	{
		return serverName;
	}

	/* (non-Javadoc)
	 * @see com.ironiacorp.email.EmailDispatcher#setServerName(java.lang.String)
	 */
	@Override
	public void setServerName(String serverName)
	{
		this.serverName = serverName;
	}

	/* (non-Javadoc)
	 * @see com.ironiacorp.email.EmailDispatcher#getServerPort()
	 */
	@Override
	public int getServerPort()
	{
		return serverPort;
	}

	/* (non-Javadoc)
	 * @see com.ironiacorp.email.EmailDispatcher#setServerPort(int)
	 */
	@Override
	public void setServerPort(int serverPort)
	{
		this.serverPort = serverPort;
	}

	/* (non-Javadoc)
	 * @see com.ironiacorp.email.EmailDispatcher#getUsername()
	 */
	@Override
	public String getUsername()
	{
		return username;
	}

	/* (non-Javadoc)
	 * @see com.ironiacorp.email.EmailDispatcher#setUsername(java.lang.String)
	 */
	@Override
	public void setUsername(String username)
	{
		this.username = username;
	}

	/* (non-Javadoc)
	 * @see com.ironiacorp.email.EmailDispatcher#getPassword()
	 */
	@Override
	public String getPassword()
	{
		return password;
	}

	/* (non-Javadoc)
	 * @see com.ironiacorp.email.EmailDispatcher#setPassword(java.lang.String)
	 */
	@Override
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	/* (non-Javadoc)
	 * @see com.ironiacorp.email.EmailDispatcher#getConnectionType()
	 */
	@Override
	public ServerConnectionType getConnectionType()
	{
		return connectionType;
	}

	/* (non-Javadoc)
	 * @see com.ironiacorp.email.EmailDispatcher#setConnectionType(com.ironiacorp.email.JavaMailEmailDispatcher.ServerConnectionType)
	 */
	@Override
	public void setConnectionType(ServerConnectionType connectionType)
	{
		this.connectionType = connectionType;
	}

	public String getTransportProtocol()
	{
		return transportProtocol;
	}

	public void setTransportProtocol(String transportProtocol)
	{
		this.transportProtocol = transportProtocol;
	}

	
	private void setSender(Message message, Recipient sender) throws MessagingException, UnsupportedEncodingException
	{
		InternetAddress emailAddress = new InternetAddress(sender.getEmail(), sender.getName());
		message.setFrom(emailAddress);
	}
	
	private void setRecipients(Message message, Email email) throws MessagingException, UnsupportedEncodingException
	{
		List<InternetAddress> to = new ArrayList<InternetAddress>();
		List<InternetAddress> cc = new ArrayList<InternetAddress>();
		List<InternetAddress> bcc = new ArrayList<InternetAddress>();
		
		for (Recipient recipient : email.getRecipients()) {
			InternetAddress emailAddress = new InternetAddress(recipient.getEmail(), recipient.getName());
			switch (email.getVisibility(recipient)) {
				case TO:
					to.add(emailAddress);
					break;
				case CC:
					cc.add(emailAddress);
					break;
				case BCC:
					bcc.add(emailAddress);
					break;
			}
		}
		
		message.setRecipients(RecipientType.TO, to.toArray(new InternetAddress[0]));
		message.setRecipients(RecipientType.CC, cc.toArray(new InternetAddress[0]));
		message.setRecipients(RecipientType.BCC, bcc.toArray(new InternetAddress[0]));
	}
	
	/* (non-Javadoc)
	 * @see com.ironiacorp.email.EmailDispatcher#sendEmails(com.ironiacorp.email.Email)
	 */
	@Override
	public void sendEmails(Email... emails)
	{
		List<Message> messages = new ArrayList<Message>();
		Properties props = new Properties();
		int port;
		String protocol;
		Session session;


		port = connectionType.port;
		protocol = connectionType.transportProtocol;
		switch (connectionType) {
			case TLS:
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", serverName);
				break;
			case SSL:
				props.put("mail.smtp.host", serverName);
				props.put("mail.smtp.port", port);
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.socketFactory.port", port);
				props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.socketFactory.fallback", "false");
		}

		if (serverPort != 0) {
			port = serverPort;
		}
		if (transportProtocol != null) {
			protocol = transportProtocol;
		}

		
		if (authenticationRequired) {
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtps.auth", "true");
			props.put("mail.smtp.user", username);
			session = Session.getInstance(props, new Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});
		} else {
			session = Session.getInstance(props);
		}
		
		for (Email email : emails) {
			Message message = new MimeMessage(session);
			Map<String, DataSource> attachments = email.getAttachments();
			try {
				setSender(message, email.getSender());
				setRecipients(message, email);
				message.setSubject(email.getSubject());
				message.setSentDate(email.getDate());
				if (attachments.size() == 0) { 
					message.setText(email.getMessage());
				} else {
					Multipart multipart = new MimeMultipart();
					BodyPart messageBodyPart;

					// Message
					messageBodyPart = new MimeBodyPart();
					messageBodyPart.setText(email.getMessage());
					multipart.addBodyPart(messageBodyPart);

					// Attachments
					for (String name : attachments.keySet()) {
						messageBodyPart = new MimeBodyPart();
						messageBodyPart.setFileName(name);
						messageBodyPart.setDataHandler(new DataHandler(attachments.get(name)));
						multipart.addBodyPart(messageBodyPart);
					}

					// Put parts in message
					message.setContent(multipart);
				}
			} catch (Exception e) {
				throw new IllegalArgumentException("Error on the creation of the email message", e);
			}
			messages.add(message);
		}
		
		try {
			Transport transport = session.getTransport(protocol);
			if (! transport.isConnected()) {
				if (authenticationRequired) {
					if (connectionType != ServerConnectionType.SSL) {
						transport.connect(serverName, port, username, password);
					}
				} else {
					transport.connect(serverName, port, "", "");
				}
			}
			for (Message message : messages) {
				Transport.send(message);
			}
		} catch (MessagingException e) {
			throw new UnsupportedOperationException("Could not sent the email", e);
		}
	}

	/* (non-Javadoc)
	 * @see com.ironiacorp.email.EmailDispatcher#isAuthenticationRequired()
	 */
	@Override
	public boolean isAuthenticationRequired()
	{
		return authenticationRequired;
	}

	/* (non-Javadoc)
	 * @see com.ironiacorp.email.EmailDispatcher#setAuthenticationRequired(boolean)
	 */
	@Override
	public void setAuthenticationRequired(boolean authenticationRequired)
	{
		this.authenticationRequired = authenticationRequired;
	}
}
