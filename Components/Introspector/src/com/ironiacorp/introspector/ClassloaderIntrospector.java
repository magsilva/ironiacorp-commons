/*
This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.
 
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
 
You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 
Copyright (C) 2009 Marco Aurelio Graciotto Silva <magsilva@ironiacorp.com>
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
