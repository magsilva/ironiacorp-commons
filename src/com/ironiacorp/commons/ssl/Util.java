package com.ironiacorp.commons.ssl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLSocket;

import org.apache.commons.ssl.KeyMaterial;
import org.apache.commons.ssl.SSLClient;
import org.apache.commons.ssl.SSLServer;
import org.apache.commons.ssl.TrustMaterial;

public final class Util
{
	public static SSLSocket createSSLClient() throws IOException, GeneralSecurityException
	{
		SSLClient client = new SSLClient();

		// Let's trust usual "cacerts" that come with Java. Plus, let's also trust a self-signed
		// cert
		// we know of. We have some additional certs to trust inside a java keystore file.
		client.addTrustMaterial(TrustMaterial.DEFAULT);
		client.addTrustMaterial(new TrustMaterial("/path/to/self-signed.pem"));
		client.addTrustMaterial(new KeyMaterial("/path/to/keystore.jks", "changeit".toCharArray()));

		// To be different, let's allow for expired certificates (not recommended).
		client.setCheckHostname(true); // default setting is "true" for SSLClient
		client.setCheckExpiry(false); // default setting is "true" for SSLClient
		client.setCheckCRL(true); // default setting is "true" for SSLClient

		// Let's load a client certificate (max: 1 per SSLClient instance).
		client.setKeyMaterial(new KeyMaterial("/path/to/client.pfx", "secret".toCharArray()));
		SSLSocket s = (SSLSocket) client.createSocket("www.cucbc.com", 443);
		
		return s;
	}

	public static SSLSocket createSSLServer() throws IOException, GeneralSecurityException
	{
		//Server Example (OpenSSL/Apache Style)
		// Compatible with the private key / certificate chain created from following the Apache2
		// TLS FAQ: "How do I create a self-signed SSL Certificate for testing purposes?"
		// http://httpd.apache.org/docs/2.2/ssl/ssl_faq.html#selfcert
	
		SSLServer server = new SSLServer();
	
		// Server needs some key material. We'll use an OpenSSL/PKCS8 style key (possibly encrypted).
		String certificateChain = "/path/to/this/server.crt";
		String privateKey = "/path/to/this/server.key";
		char[] password = "changeit".toCharArray();
		KeyMaterial km = new KeyMaterial( certificateChain, privateKey, password ); 
	
		server.setKeyMaterial( km );
	
		// These settings have to do with how we'll treat client certificates that are presented
		// to us. If the client doesn't present any client certificate, then these are ignored.
		server.setCheckHostname( false ); // default setting is "false" for SSLServer
		server.setCheckExpiry( true );    // default setting is "true" for SSLServer
		server.setCheckCRL( true );       // default setting is "true" for SSLServer
	
		// This server trusts all client certificates presented (usually people won't present
		// client certs, but if they do, we'll give them a socket at the very least).
		server.addTrustMaterial( TrustMaterial.TRUST_ALL );
		SSLServerSocket ss = (SSLServerSocket) server.createServerSocket( 7443 );
		SSLSocket socket = (SSLSocket) ss.accept();
		
		return socket;
	}
	
	public static SSLSocket createSSLServer2() throws IOException, GeneralSecurityException
	{
		// Server Example (Traditional Java "KeyStore" Style)
	
		SSLServer server = new SSLServer();
	
		//	 Server needs some key material.   We'll use a Java Keystore (.jks) or Netscape
		//	 PKCS12 (.pfx or .p12) file.  Commons-ssl automatically detects the type.
		String pathToKeyMaterial = "/path/to/.keystore";
		char[] password = "changeit".toCharArray();
		KeyMaterial km = new KeyMaterial( pathToKeyMaterial, password ); 
	
		server.setKeyMaterial( km );
	
		//	 This server trusts all client certificates presented (usually people won't present
		//	 client certs, but if they do, we'll give them a socket at the very least).
		server.addTrustMaterial( TrustMaterial.TRUST_ALL );
		SSLServerSocket ss = (SSLServerSocket) server.createServerSocket( 7443 );
		SSLSocket socket = (SSLSocket) ss.accept();
		
		return socket;
	}
}
