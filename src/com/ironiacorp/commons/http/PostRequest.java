/*
Licensed to the Apache Software Foundation (ASF) under one or more
contributor license agreements.  See the NOTICE file distributed with
this work for additional information regarding copyright ownership.
The ASF licenses this file to You under the Apache License, Version 2.0
(the "License"); you may not use this file except in compliance with
the License.  You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

Copyright (C) 2007 Apache Software Foundation (ASF).
 */

package com.ironiacorp.commons.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.concurrent.Callable;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import com.ironiacorp.commons.IoUtil;

/**
 * How to send a request via proxy using {@link HttpClient HttpClient}.
 * 
 * @author Roland Weber
 */
public class PostRequest implements Callable<File>, Method
{
	private URI uri;
	
	private HttpClient client;
	
	private NameValuePair[] data;
	
	public PostRequest(URI uri, NameValuePair[] data, HttpClient client)
	{
		this.uri = uri;
		this.client = client;
		this.data = data;
	}
	
	public File call()
	{
		PostMethod postMethod = new PostMethod(uri.toString());
		postMethod.setRequestBody(data);
	
		File result = null;

		try {
			int statusCode = client.executeMethod(postMethod);
			
			if (statusCode != HttpStatus.SC_OK) {
				System.err.println("Method failed: " + postMethod.getStatusLine());
			}
			
			InputStream inputStream = postMethod.getResponseBodyAsStream();
			
			if (inputStream != null) {
				FileOutputStream outputStream = null;  

				try {
					result = IoUtil.createTempFile("sysrev-get-", ".html");
					int readBytes = 0;
					
					outputStream = new FileOutputStream(result);
					byte[] buffer = new byte[IoUtil.BUFFER_SIZE];
					while ((readBytes = inputStream.read(buffer, 0, buffer.length)) != -1) {
						outputStream.write(buffer, 0, readBytes);
					}
				} finally {
					try {
						outputStream.close();
						inputStream.close();
						postMethod.releaseConnection();
					} catch (Exception e) {
					}
				}
			}
		} catch (HttpException e) {
		} catch (IOException e) {
			// In case of an IOException the connection will be released
			// back to the connection manager automatically
		} catch (RuntimeException ex) {
			// In case of an unexpected exception you may want to abort
			// the HTTP request in order to shut down the underlying
			// connection and release it back to the connection manager.
			postMethod.abort();
		}
		
		return result;
	}
}
