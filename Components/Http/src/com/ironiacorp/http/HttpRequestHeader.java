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
 * Header options for a HTTP request.
 */
public enum HttpRequestHeader
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
	
	// Request header
	ACCEPT("Accept"),
	ACCEPT_CHARSET("Accept-Charset"),
	ACCEPT_ENCODING("Accept-Encoding"),
	ACCEPT_LANGUAGE("Accept-Language"),
	AUTHORIZATION("Authorization"),
	EXCEPT("Expect"),
	FROM("From"),
	HOST("Host"),
	IF_MATCH("If-Match"),
	IF_MODIFIED_SINCE("If-Modified-Since"),
	IF_NONE_MATCH("If-None-Match"),
	IF_RANGE("If-Range"),
	IF_UNMODIFIED_SINCE("If-Unmodified-Since"),
	MAX_FORWARD("Max-Forwards"),
	PROXY_AUTHORIZATION("Proxy-Authorization"),
	RANGE("Range"),
	REFERER("Referer"),
	TE("TE"),
	USER_AGENT("User-Agent");
    
	/**
	 * Pretty name for the request header.
	 */
    public final String name;
    
    private HttpRequestHeader(String name)
    {
    	this.name = name;
    }
}
