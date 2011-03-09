/*
 * Copyright (C) 2010 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>
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

package com.ironiacorp.http;

/**
 * Header options for a HTTP response.
 */
public enum HttpResponseHeader
{
	// General header
	CACHE_CONTROL("Cache-Control"),
	CONNECTION("Connection"),
	DATE("Date"),
	PRAGMA("Pragma"),
	TRAILER("Trailer"),
	TRAILER_ENCODING("Transfer-Encoding"),
	UPGRADE("Upgrade"),
	VIA("Via"),
	WARNING("Warning"),
	
	// Response header
	ACCEPT_RANGES("Accept-Ranges"),
	AGE("Age"),
	ETAG("ETag"),
	LOCATION("Location"),
	PROXY_AUTHENTICATE("Proxy-Authenticate"),
	RETRY_AFTER("Retry-After"),
	SERVER("Server"),
	VARY("Vary"),
	WWW_AUTHENTICATE("WWW-Authenticate");
    
	/**
	 * Pretty name for the response header.
	 */
    public final String name;
    
    private HttpResponseHeader(String name)
    {
    	this.name = name;
    }
}
