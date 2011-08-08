package com.ironiacorp.ws.discovery.slp;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

import ch.ethz.iks.slp.Locator;
import ch.ethz.iks.slp.ServiceLocationException;
import ch.ethz.iks.slp.ServiceLocationManager;
import ch.ethz.iks.slp.ServiceType;

/**
 * Formely jSLP (http://jslp.sourceforge.net/), the code has been donated to
 * the Eclipse ECF Project.
 */
public class EclipseSlpManager
{
	/*
	public void discoverServices(String serviceName) throws Exception
	{
		ServiceLocationEnumeration sle = null;
		Locator locator = ServiceLocationManager.getLocator(new Locale("en"));
		List<String> scopes = new ArrayList<String>();
		scopes.add("default");
		scopes.add("cmapdp");
		sle = locator.findServiceTypes(null, scopes);
		while (sle.hasMoreElements()) {
			ServiceURL foundService = (ServiceURL) sle.nextElement();
			System.out.println(foundService);
		}
		
		// find all services of type "test" that have attribute "cool=yes"
		sle = locator.findServices(new ch.ethz.iks.slp.ServiceType(serviceName), null, null);
		while (sle.hasMoreElements()) {
			ServiceURL foundService = (ServiceURL) sle.nextElement();
			System.out.println(foundService);
		}
		
		ServiceURL url = new ServiceURL("service:service-agent://10.6.208.61:4447");
		System.out.println(url.getPort());
		System.out.println(url.getServiceType().toString());
	}
	*/
	
	public void discoverServices3(String serviceName) throws Exception
	{
		Locator locator = ServiceLocationManager.getLocator(Locale.ENGLISH);
        try {
                List<String> scopes = new ArrayList<String>();
        		scopes.add("cmapdp");
        		
                Enumeration<?> enumeration = locator.findServices(new ServiceType(serviceName), scopes, null);
                while (enumeration.hasMoreElements()) {
                        System.out.println(enumeration.nextElement().toString());
                }
         } catch (ServiceLocationException e) {
                e.printStackTrace();
        }
	}
}
