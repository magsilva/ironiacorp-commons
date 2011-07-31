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

package com.ironiacorp.email;

public enum EmailServerConnectionType
{
	PLAIN(25, "smtp"),
	TLS(587, "smtp"),
	SSL(465, "smtps");
	
	public final int port;
	
	public final String transportProtocol;
	
	private EmailServerConnectionType(int port, String transportProtocol)
	{
		this.port = port;
		this.transportProtocol = transportProtocol;
	}
}
