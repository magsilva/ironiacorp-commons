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
import java.util.regex.Pattern;

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
		Pattern ethPattern = Pattern.compile("eth\\d+");
		Pattern emPattern = Pattern.compile("em\\d+");
		Pattern wlanPattern = Pattern.compile("wlan\\d+");
		Pattern wlan2Pattern = Pattern.compile("wlp\\d+s\\d+");
		Pattern virtualNicPattern = Pattern.compile("virbr\\d+");
		while (i.hasNext()) {
			NetworkInterface nic = i.next();
			String nicName = nic.getDisplayName();
			if (ethPattern.matcher(nicName).matches()) {
				return;
			}
			if (emPattern.matcher(nicName).matches()) {
				return;
			}
			if (wlanPattern.matcher(nicName).matches()) {
				return;
			}
			if (wlan2Pattern.matcher(nicName).matches()) {
				return;
			}
			if (virtualNicPattern.matcher(nicName).matches()) {
				return;
			}
		}
		fail();
	}
}
