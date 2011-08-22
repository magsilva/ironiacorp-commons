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

import java.io.File;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.activation.DataSource;
import javax.activation.FileDataSource;

public class Email
{
	private String subject;
	
	private Recipient sender;
	
	private LinkedHashMap<Recipient, Recipient.Visibility> recipients;
	
	private String message;
	
	private LinkedHashMap<String, DataSource> attachments;
	
	private Date date;

	public Email()
	{
		recipients = new LinkedHashMap<Recipient, Recipient.Visibility>();
		attachments = new LinkedHashMap<String, DataSource>();
		date = new Date();
	}
	
	public void addRecipient(Recipient recipient, Recipient.Visibility visibility)
	{
		recipients.put(recipient, visibility);
	}
	
	public Collection<Recipient> getRecipients()
	{
		return recipients.keySet();
	}
	
	public void removeRecipient(Recipient recipient)
	{
		recipients.remove(recipient);
	}
	
	public Recipient.Visibility getVisibility(Recipient recipient)
	{
		if (! recipients.containsKey(recipient)) {
			throw new IllegalArgumentException("Recipient does not exist in the email");
		}
		return recipients.get(recipient);
	}
	
	public void setVisibility(Recipient recipient, Recipient.Visibility visibility)
	{
		if (! recipients.containsKey(recipient)) {
			throw new IllegalArgumentException("Recipient does not exist in the email");
		}
		addRecipient(recipient, visibility);
	}

	public String getSubject()
	{
		return subject;
	}

	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}
	
	public void addAttachment(File file)
	{
		addAttachment(file.getName(), file);
	}
	
	public void addAttachment(String name, File file)
	{
		DataSource source = new FileDataSource(file);
		attachments.put(name, source);
	}
	
	public void removeAttachment(File file)
	{
		attachments.remove(file.getName());	
	}
	
	public void removeAttachment(String name)
	{
		attachments.remove(name);	
	}

	public Map<String, DataSource> getAttachments()
	{
		return attachments;
	}
	
	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}

	public Recipient getSender()
	{
		return sender;
	}

	public void setSender(Recipient sender)
	{
		this.sender = sender;
	}
}
