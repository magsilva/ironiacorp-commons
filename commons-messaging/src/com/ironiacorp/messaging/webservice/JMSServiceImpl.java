package com.ironiacorp.messaging.webservice;

import java.util.Hashtable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JMSServiceImpl implements JMSService
{
	Context context;

	Session session;

	Connection connection;

	MessageConsumer receiver;
	
	//javax.jms.Connection.setExceptionListener() to handle this error and reconnect
	//org.jboss.mq.SpyJMSException:

	//testTopic
	// testDurableTopic
	// testQueue
	public JMSServiceImpl()
	{
		Hashtable<String, String> properties = new Hashtable<String,String>();
		properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
		properties.put(Context.URL_PKG_PREFIXES, "org.jboss.naming:org.jnp.interfaces");
		properties.put(Context.PROVIDER_URL, "jnp://localhost:1099");
//		properties.put(Context.SECURITY_PRINCIPAL, "admin");
//		properties.put(Context.SECURITY_CREDENTIALS, "admin");
				
		try {
			context = new InitialContext(properties);
			ConnectionFactory factory = (ConnectionFactory) context.lookup("ConnectionFactory");
			connection = (Connection) factory.createConnection();
			session = (Session) connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			connection.start();
		} catch (NamingException e) {
			System.out.println(e.getMessage());
		} catch (JMSException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public TextMessage[] getMsgFromTopic(String topicName,String consumer) throws NamingException,JMSException
	{
		Topic destination=(Topic) context.lookup(topicName);
		try {
			receiver=session.createDurableSubscriber(destination,consumer);
		} catch(Exception ex){
		}
		return (getMsg(receiver));
	}
	
	public TextMessage[] getMsgFromQueue(String queueName) throws NamingException,JMSException
	{
		Queue destination=(Queue) context.lookup(queueName);
		MessageConsumer receiver=session.createConsumer(destination);
		return (getMsg(receiver));
	}
	
	public TextMessage postMsgToTopic(String topicName, String msg) throws NamingException,JMSException
	{
		Topic destination= (Topic)context.lookup(topicName);
		MessageProducer sender = session.createProducer(destination);
		return postMsg(sender,msg);
	}
	
	public TextMessage postMsgToQueue(String queueName, String msg) throws NamingException,JMSException
	{
		Queue destination= (Queue)context.lookup(queueName);
		MessageProducer sender = session.createProducer(destination);
		return postMsg(sender,msg);
	}
	
	private TextMessage postMsg(MessageProducer sender, String msg) throws JMSException
	{
		TextMessage message = session.createTextMessage(msg);
		sender.send(message);
		sender.close();
		return message;
	}
	
	private TextMessage[] getMsg(MessageConsumer receiver) throws JMSException
	{
		Message message=receiver.receive(100);
		TextMessage text= (message instanceof TextMessage) ? (TextMessage) message : null;
		TextMessage[] msgs= {text};
		return (msgs);
	}	
	
	public static void main(String[] args) throws NamingException, JMSException {
		JMSServiceImpl jms = new JMSServiceImpl();
		jms.postMsgToQueue("queue/testQueue", "Teste 1 2 3");
		TextMessage[] msg = jms.getMsgFromQueue("queue/testQueue");
		System.out.println(msg[0].getText());
	}
}
