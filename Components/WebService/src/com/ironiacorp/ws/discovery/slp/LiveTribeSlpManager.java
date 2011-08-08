package com.ironiacorp.ws.discovery.slp;

import java.util.Iterator;
import java.util.List;

import org.livetribe.slp.SLP;
import org.livetribe.slp.Scopes;
import org.livetribe.slp.ServiceInfo;
import org.livetribe.slp.ServiceType;
import org.livetribe.slp.settings.Key;
import org.livetribe.slp.settings.Keys;
import org.livetribe.slp.settings.MapSettings;
import org.livetribe.slp.settings.Settings;
import org.livetribe.slp.ua.UserAgentClient;

/**
 * LivreTribe (http://livetribe.codehaus.org/) implements several system
 * management tools, including SLP support.
 */
public class LiveTribeSlpManager
{
	public List<ServiceInfo> discoverServers(String serviceName)
	{
		Settings settings = new MapSettings();
	    settings.put(Keys.PORT_KEY, 8847);
		UserAgentClient uac = SLP.newUserAgentClient(settings);

		ServiceType serviceType = new ServiceType(serviceName);
		String language = null;
		Scopes scopes = Scopes.DEFAULT; // Scopes.NONE; 
		String filter = null;

		List<ServiceInfo> services = uac.findServices(serviceType, language, scopes, filter);
		Iterator<ServiceInfo> i = services.iterator();
		while (i.hasNext()) {
			ServiceInfo info = i.next();
			System.out.println(info.toString());
		}
		
		
		services = uac.findServices(serviceType, null , null, null);
		i = services.iterator();
		while (i.hasNext()) {
			ServiceInfo info = i.next();
			System.out.println(info.toString());
		}
		// ServiceURL serviceURL = new ServiceURL("http://localhost:8088/services/CmapWebService");
		return services;
	}
}
