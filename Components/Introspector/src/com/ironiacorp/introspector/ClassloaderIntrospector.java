/*
 * Copyright (C) 2007 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>
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

package com.ironiacorp.introspector;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * 
 * Based on work of Clarkware Consulting, Inc. (JWhich) and FullSpan Software
 * (JWhich).
 */
public class ClassloaderIntrospector
{
	public static final String LB = System.getProperty("line.separator");

	private String classpath;
	
	private ClassLoader loader;
	
	public ClassloaderIntrospector()
	{
		this(System.getProperty("java.class.path"), ClassloaderIntrospector.class.getClassLoader());
	}
	
	public ClassloaderIntrospector(String classpath, ClassLoader loader)
	{
		this.classpath = classpath;
		this.loader = loader;
	}
	
	public Set<String> getResourceLocations(String resourceName)
	{
		Enumeration<URL> en = null;
		Set<String> locations = new HashSet<String>();

		if (!resourceName.startsWith("/")) {
			resourceName = "/" + resourceName;
		}
		resourceName = resourceName.replace('.', '/');

		try {
			en = loader.getResources(resourceName);
		} catch (IOException e) {
		}

		if (en == null) {
			return locations;
		}

		while (en.hasMoreElements()) {
			URL url = en.nextElement();
			locations.add(url.toString());
		}

		return locations;
	}

	public Set<String> getClassLocations(String className)
	{
		return getResourceLocations(className + ".class");
	}

	/**
	 * Validates the class path and reports any non-existent or invalid class
	 * path entries. <p> Valid class path entries include directories,
	 * <code>.zip</code> files, and <code>.jar</code> files.
	 */
	public Map<File, Set<String>> validateClasspath()
	{
		Map<File, Set<String>> result = new HashMap<File, Set<String>>();
		StringTokenizer tokenizer = new StringTokenizer(classpath, File.pathSeparator);
		
		while (tokenizer.hasMoreTokens()) {
			String element = tokenizer.nextToken();
			File f = new File(element);
			Set<String> err = new HashSet<String>();
			result.put(f, err);
			if (! f.exists()) {
				err.add("File not found");
			} else {
				 if (! f.isDirectory() && ! element.toLowerCase().endsWith(".jar") &&  ! element.toLowerCase().endsWith(".zip")) {
					err.add("File is not a directory or a Java package");
				 }
			}
		}
		
		return result;
	}
}
