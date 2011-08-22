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

import org.junit.Test;

import com.ironiacorp.email.Recipient.Visibility;

public abstract class EmailDispatcherTest {

	protected EmailDispatcher dispatcher;

	@Test
	public void testSendEmails_Gmail_TLS() {
		Email email = new Email();
		Recipient sender = new Recipient();
		Recipient recipient = new Recipient();
		
		sender.setName("John Doe");
		sender.setEmail("test@ironiacorp.com");
		recipient.setName("Marco Aurélio Graciotto Silva");
		recipient.setEmail("magsilva@gmail.com");
		email.setSender(sender);
		email.addRecipient(recipient, Visibility.TO);
		email.setSubject("Test");
		email.setMessage("Testing, 1 2 3");
		
		dispatcher.setAuthenticationRequired(true);
		dispatcher.setServerName("smtp.gmail.com");
		dispatcher.setUsername("test@ironiacorp.com");
		dispatcher.setPassword("V7CwsXbNnGf6pR6U");
		dispatcher.setConnectionType(EmailServerConnectionType.TLS);
		dispatcher.sendEmails(email);
	}

	@Test
	public void testSendEmails_Gmail_SSL() {
		Email email = new Email();
		Recipient sender = new Recipient();
		Recipient recipient = new Recipient();
		
		sender.setName("John Doe");
		sender.setEmail("test@ironiacorp.com");
		recipient.setName("Marco Aurélio Graciotto Silva");
		recipient.setEmail("magsilva@gmail.com");
		email.setSender(sender);
		email.addRecipient(recipient, Visibility.TO);
		email.setSubject("Test");
		email.setMessage("Testing, 1 2 3");
		
		dispatcher.setAuthenticationRequired(true);
		dispatcher.setServerName("smtp.gmail.com");
		dispatcher.setUsername("test@ironiacorp.com");
		dispatcher.setPassword("V7CwsXbNnGf6pR6U");
		dispatcher.setConnectionType(EmailServerConnectionType.SSL);
		dispatcher.sendEmails(email);
	}

}