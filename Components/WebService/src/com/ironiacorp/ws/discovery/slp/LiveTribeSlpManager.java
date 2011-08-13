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
