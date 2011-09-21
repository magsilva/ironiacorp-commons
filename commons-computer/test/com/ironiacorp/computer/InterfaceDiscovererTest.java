/*
Copyright (C) 2011 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/


package com.ironiacorp.computer;

import static org.junit.Assert.*;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class InterfaceDiscovererTest
{
	private InterfaceDiscoverer id;
	
	@Before
	public void setUp() throws Exception
	{
		id = new InterfaceDiscoverer();
		id.setIpv4(true);
		id.setIpv6(true);
		id.setLocalhost(true);
	}

	@Test
	public void testDiscoverAddresses() throws Exception
	{
		Map<InetAddress, NetworkInterface> addresses = id.discoverAddresses();
		Iterator<InetAddress> i = addresses.keySet().iterator();
		while (i.hasNext()) {
			InetAddress address = i.next();
			if ("127.0.0.1".equals(address.getHostAddress())) {
				return;
			}
		}
		fail();
	}
	
	@Test
	public void testDiscoverNics() throws Exception
	{
		Set<NetworkInterface> nics = id.discoverNics();
		Iterator<NetworkInterface> i = nics.iterator();
		while (i.hasNext()) {
			NetworkInterface nic = i.next();
			if ("eth0".equals(nic.getDisplayName())) {
				return;
			}
		}
		fail();
	}
}