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
 
Copyright (C) 2007 Marco Aurelio Graciotto Silva <magsilva@gmail.com>
*/

package com.ironiacorp.messaging.jms;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.JMSException;
import javax.jms.TextMessage;

public class JMSPublisher extends JMSAgent
{
	public JMSPublisher(String topicName, String subscriptionName)
	{
		super(topicName, subscriptionName);
	}

	protected void startSession()
	{
		MessageProducer producer = null;
		try {
			producer = session.createProducer((Destination)topic);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			producer.setPriority(4); // value between 0 and 9, 9 being the highest priority
			producer.setTimeToLive(0);
			TextMessage message = session.createTextMessage();
			message.setText("Hello World");
			producer.send(message);
		} catch (JMSException e) {
		} finally {
			try {
				producer.close();
				session.close();
			} catch (JMSException e) {
			}
		}
	}
}
