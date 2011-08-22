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
