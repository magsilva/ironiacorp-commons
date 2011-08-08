package com.ironiacorp.ws.discovery.slp;

import java.util.Enumeration;
import java.util.Locale;
import java.util.Vector;

import org.sblim.slp.Locator;
import org.sblim.slp.ServiceLocationException;
import org.sblim.slp.ServiceLocationManager;

public class SublimeSlpManager
{
	public void discoverServices2(String serviceName) throws Exception
	{
		try {
			Locator locator = ServiceLocationManager.getLocator(Locale.ENGLISH);
			Vector<String> scopes = new Vector<String>();
			scopes.add("default");
			String query = "";
			org.sblim.slp.ServiceType servicetype = new org.sblim.slp.ServiceType(serviceName);
			Enumeration<?> enumeration = locator.findServices(servicetype, scopes, query);

			while (enumeration.hasMoreElements()) {
				System.out.println(enumeration.nextElement());
			}

		} catch (ServiceLocationException e) {
			e.printStackTrace();
		}
	}
}
