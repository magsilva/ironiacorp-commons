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

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import com.ironiacorp.ws.Service;
import com.ironiacorp.ws.discovery.criteria.Criterion;
import com.ironiacorp.ws.discovery.criteria.ScalarPropertyCriterion;

import ch.ethz.iks.slp.Locator;
import ch.ethz.iks.slp.ServiceLocationException;
import ch.ethz.iks.slp.ServiceType;
import ch.ethz.iks.slp.ServiceURL;
import ch.ethz.iks.slp.impl.ServiceLocationManager;

/**
 * Formely jSLP (http://jslp.sourceforge.net/), the code has been donated to
 * the Eclipse ECF Project.
 */
public class EclipseSlpManager implements SlpServiceDiscoverer
{
	private Set<String> scopes;
	
	int port = SlpServiceDiscoverer.DEFAULT_PORT;

	public EclipseSlpManager()
	{
		scopes = new HashSet<String>();
	}

	@Override
	public void addScope(String scope)
	{
		scopes.add(scope);
	}

	@Override
	public void removeScope(String scope)
	{
		scopes.remove(scope);
	}

	@Override
	public Set<String> getScopes()
	{
		return scopes;
	}


	@Override
	public void setPort(int port)
	{
		if (port > 0) {
			this.port = port;
		}
	}

	@Override
	public int getPort()
	{
		return port;
	}
	
	public Set<Service> find(Criterion... criteria)
	{
		try {
			ServiceLocationManager locationManager = new ServiceLocationManager();
			Locator locator = locationManager.getLocator(Locale.ENGLISH);
			List<String> scopeList = new ArrayList<String>(scopes);
			ServiceType serviceType = null;
			for (Criterion<?> criterion : criteria) {
				if (criterion instanceof ScalarPropertyCriterion) {
					ScalarPropertyCriterion scalarCriterion = (ScalarPropertyCriterion) criterion;
					if ("name".equals(scalarCriterion.getPropertyName())) {
						serviceType = new ServiceType((String) scalarCriterion.getPropertyValue());
					}
				}
			}

			Enumeration<?> enumeration = locator.findServices(serviceType, scopeList, null);
			while (enumeration.hasMoreElements()) {
				ServiceURL service = (ServiceURL) enumeration.nextElement();
				System.out.println(service.toString());
			}
		} catch (ServiceLocationException e) {
			e.printStackTrace();
		}

		return null;
	}
}
