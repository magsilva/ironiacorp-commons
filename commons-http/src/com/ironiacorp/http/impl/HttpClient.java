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

package com.ironiacorp.http.impl;

import java.util.ArrayList;
import java.util.List;

import com.ironiacorp.http.HttpJob;

public class HttpClient
{
	protected boolean concurrency = true;
	
	protected List<HttpJob> jobs;

	public HttpClient() {
		jobs = new ArrayList<HttpJob>();
	}

	public boolean isConcurrencyEnabled() {
		return concurrency;
	}

	public void setConcurrency(boolean concurrency) {
		this.concurrency = concurrency;
	}

	
	public void addJob(HttpJob job) {
		boolean added = false;
		
		added = jobs.add(job);
		
		if (! added) {
			throw new IllegalArgumentException("Invalid job");
		}
	}
}