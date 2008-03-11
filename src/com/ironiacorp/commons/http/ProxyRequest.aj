package com.ironiacorp.commons.http;

import java.util.Properties;
import com.ironiacorp.commons.http.SocksProxy;


public aspect ProxyRequest
{
	private SocksProxy proxy = new SocksProxy("localhost", 3333);
	
	pointcut connectionConfiguration(Operation op):
		call(void Operation.run())
		&& target(op);
	
	
	before(Operation op): connectionConfiguration(op) {
		Properties props = System.getProperties();
		
		props.setProperty("socksProxyHost", proxy.getHost());
		props.setProperty("socksProxyPort", Integer.toString(proxy.getPort()));
		
		if (proxy.getUsername() != null) {
			props.setProperty("socksProxyUserName", proxy.getUsername());
			props.setProperty("java.net.socks.username", proxy.getUsername());
			props.setProperty("socksProxyPassword", proxy.getPassword());
			props.setProperty("java.net.socks.password", proxy.getPassword());
		}
	}

	after(Operation op): connectionConfiguration(op) {
		Properties props = System.getProperties();

		props.remove("socksProxyHost");
		props.remove("socksProxyPort");
		props.remove("socksProxyUserName");
		props.remove("java.net.socks.username");
		props.remove("socksProxyPassword");
		props.remove("java.net.socks.password");
	}
}
