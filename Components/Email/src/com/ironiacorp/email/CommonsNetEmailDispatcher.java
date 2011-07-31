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

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;

import org.apache.commons.net.smtp.AuthenticatingSMTPClient;
import org.apache.commons.net.smtp.AuthenticatingSMTPClient.AUTH_METHOD;
import org.apache.commons.net.smtp.SMTPReply;
import org.apache.commons.net.smtp.SimpleSMTPHeader;

import com.ironiacorp.email.Recipient.Visibility;

public final class CommonsNetEmailDispatcher implements EmailDispatcher
{
	private EmailServerConnectionType connectionType = DEFAULT_SERVER_CONNECTION_TYPE;
	
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
	public EmailServerConnectionType getConnectionType()
	{
		return connectionType;
	}

	/* (non-Javadoc)
	 * @see com.ironiacorp.email.EmailDispatcher#setConnectionType(com.ironiacorp.email.JavaMailEmailDispatcher.ServerConnectionType)
	 */
	@Override
	public void setConnectionType(EmailServerConnectionType connectionType)
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
	
	/* (non-Javadoc)
	 * @see com.ironiacorp.email.EmailDispatcher#sendEmails(com.ironiacorp.email.Email)
	 */
	@Override
	public void sendEmails(Email... emails)
	{
		AuthenticatingSMTPClient client = null;
		boolean result = false;
		int port = connectionType.port;
		if (serverPort != 0) {
			port = serverPort;
		}

		try {
			client = new AuthenticatingSMTPClient();

			client.connect(serverName, port);
			if (connectionType == EmailServerConnectionType.SSL || connectionType == EmailServerConnectionType.TLS) {
				result = client.execTLS();
				if (result == false) {
					throw new IllegalArgumentException("Cannot start secure connection");
				}
			}
			
			if (authenticationRequired) {
				result = client.auth(AUTH_METHOD.PLAIN, username, password);
				if (result == false) {
					throw new IllegalArgumentException("Incorrect username or password");
				}
			}
			
			// After connection attempt, you should check the reply code to verify success.
			int reply = client.getReplyCode();
			if (! SMTPReply.isPositiveCompletion(reply)) {
				client.disconnect();
			}
			
			for (Email email : emails) {
				SimpleSMTPHeader header;
				Iterator<Recipient> i = email.getRecipients().iterator();
				Writer writer = client.sendMessageData();
				String to = null;
				
				while (i.hasNext()) {
					Recipient recipient = i.next();
					if (to == null && email.getVisibility(recipient) == Visibility.TO) {
						to = recipient.getEmail();
					} else {
						client.addRecipient(recipient.getEmail());
					}
				}
				
				header = new SimpleSMTPHeader(email.getSender().getEmail(), null, email.getSubject());
				
				 
				
				writer.write(header.toString());
				result = client.completePendingCommand();
				if (result == false) {
					throw new IllegalArgumentException("Could not send the email");
				}
				client.reset();
			}
		} catch (Exception e) {
			throw new UnsupportedOperationException("Could not send the email", e);
		} finally {
			if (client != null && client.isConnected()) {
				try {
					client.disconnect();
				} catch (IOException e) {
				}
			}
			
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
