package com.ironiacorp.messaging.webservice;

import org.codehaus.xfire.XFire;
import org.codehaus.xfire.XFireFactory;
import org.codehaus.xfire.server.http.XFireHttpServer;
import org.codehaus.xfire.service.Service;
import org.codehaus.xfire.service.binding.ObjectServiceFactory;
import org.codehaus.xfire.service.invoker.BeanInvoker;


public class Demo
{
	XFireHttpServer server;
	
	public static void main(String[] args)
	{
		Demo starter = new Demo();
		try {
			starter.start();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void start() throws Exception
	{
		// Create an XFire Service
		ObjectServiceFactory serviceFactory = new ObjectServiceFactory();
		XFire xfire = XFireFactory.newInstance().getXFire();
		Service service = serviceFactory.create(JMSService.class);
		service.setInvoker(new BeanInvoker(new JMSServiceImpl()));
		xfire.getServiceRegistry().register(service);

		// Start the HTTP server
		server = new XFireHttpServer();
		server.setPort(8191);
		server.start();
	}
	
	public void stop() throws Exception
	{
		server.stop();
	}    
}