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

import java.util.Set;

import com.ironiacorp.ws.discovery.ServiceDiscoverer;

public interface SlpServiceDiscoverer extends ServiceDiscoverer
{
	static final int DEFAULT_PORT = 427;
	
	static final String DEFAULT_SCOPE = "default";
	
	void addScope(String scope);
	
	void removeScope(String scope);
	
	Set<String> getScopes();
	
	void setPort(int port);
	
	int getPort();
}
