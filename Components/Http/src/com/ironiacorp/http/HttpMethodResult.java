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

Copyright (C) 2010 Marco Aurélio Graciotto Silva <magsilva@icmc.usp.br>
*/

package com.ironiacorp.http;

import java.io.OutputStream;

public class HttpMethodResult
{
	private int statusCode;
	
	private OutputStream content;

	public int getStatusCode()
	{
		return statusCode;
	}

	public void setStatusCode(int statusCode)
	{
		this.statusCode = statusCode;
	}

	public OutputStream getContent()
	{
		return content;
	}

	public void setContent(OutputStream content)
	{
		this.content = content;
	}

	
	
}
