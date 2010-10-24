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

Copyright (C) 2006 Marco Aurélio Graciotto Silva <magsilva@gmail.com>
*/

package com.ironiacorp.messaging.jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.Topic;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * JMS Agent. Any JMS client or provider share this class behaviour. 
 *
 */
public abstract class JMSAgent
{
	private ConnectionFactory connectionFactory;

	protected Connection connection = null;
	
	protected Topic topic;
	
	protected Session session;
	
	protected String subscriptionName;

	/**
	 * Start a JMS agent for a temporary topic.
	 * 
	 * @param topicName The topic the agent will subscribe or publish to.
	 */
	public JMSAgent(String topicName)
	{
		this(topicName, null);
	}

	/**
	 * Start a JMS agent for a durable topic.
	 * 
	 * @param topicName The topic the agent will subscribe or publish to.
	 * @param subscriptionName The subscription name.
	 */
	public JMSAgent(String topicName, String subscriptionName)
	{
		try {
			Context messaging = new InitialContext();
			connectionFactory = (ConnectionFactory) messaging.lookup("ConnectionFactory");
			topic = (Topic) messaging.lookup(topicName);
			this.subscriptionName = subscriptionName;
		} catch (NamingException e) {
		}
		
		try {
			connection = connectionFactory.createConnection();
			/*
			 * AUTO_ACKNOWLEDGE: The session automatically acknowledges a client's receipt of a
			 * message either when the session has successfully returned from a call to receive
			 * or when the message listener the session has called to process the message
			 * successfully returns.
			 * CLIENT_ACKNOWLEDGE: The client acknowledges a consumed message by calling the
			 * message's acknowledge method.
			 * DUPS_OK_ACKNOWLEDGE: Instructs the session to lazily acknowledge the delivery of
			 * messages.
			 * SESSION_TRANSACTED: This value is returned from the method getAcknowledgeMode if
			 * the session is transacted.
			 */
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
		}
		
		establishSession();
		startSession();
	}
	
	protected void establishSession()
	{
		try {
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		} catch (JMSException e) {
		}
	}
	
	abstract protected void startSession();
	
	public void start()
	{
		try {
			connection.start();
		} catch (JMSException e) {
		}
	}
	
	public void pause()
	{
		try {
			connection.stop();
		} catch (JMSException e) {
		}
	}
	
	public void stop()
	{
		try {
			connection.stop();
			connection.close();
		} catch (JMSException e) {
		}
	}
}
