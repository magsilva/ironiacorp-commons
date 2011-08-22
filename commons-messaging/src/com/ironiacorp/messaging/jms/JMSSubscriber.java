/*
This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.
 
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
 
You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 
Copyright (C) 2006 Marco Aurelio Graciotto Silva <magsilva@gmail.com>
*/

package com.ironiacorp.messaging.jms;

import javax.jms.Connection;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.Topic;

public class JMSSubscriber extends JMSAgent
{
	public JMSSubscriber(String topicName, String subscriptionName)
	{
		super(topicName, subscriptionName);
	}


	protected Connection connection = null;
	
	private boolean durableTopic = false;
	
	private String subscriptionName;

	protected MessageConsumer consumer;
	
	protected void startSession()
	{
		try {
			consumer = null;
			if (durableTopic) {
				consumer = session.createDurableSubscriber(topic, subscriptionName);
			} else {
				consumer = session.createConsumer(topic);
			}

			// Ignore messages without an XML file as content (type = TextMessage)
			// String selector = "JMSType =  'TextMessage'"; 
			// MessageConsumer consumer = session.createConsumer(topic, selector);
		} catch (JMSException e) {
		}
	}
	
	
	public void process()
	{
		try {
			// Message m = consumer.receiveNoWait();
			// Message m = consumer.receive(1000); //timeout
			Message m = consumer.receive();
			processMessage(m);
			// session.unsubscribe(subname);
			m.acknowledge();
		} catch (JMSException e) {
			
		}
	}
	
	protected void processMessage(Message m) throws JMSException
	{
		if (m instanceof TextMessage)	{
			TextMessage message = (TextMessage)m;
			System.out.println(message.getText());
		}
	}
}
