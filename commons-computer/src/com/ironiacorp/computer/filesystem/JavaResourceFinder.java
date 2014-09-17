/*
 * Copyright (c) 2005, FullSpan Software (www.fullspan.com)
 *
 * Licensed under the BSD License
 * OSI Certified Open Source Software (www.opensource.org)
 *
 * You may not use this file except in compliance with the License.  You should
 * have received a copy of the License with this distribution, or you can find
 * it at: http://www.fullspan.com/shared/license.html.
 *
 * NO WARRANTY - USE AT YOUR OWN RISK.  All software and other materials
 * distributed under the License are provided on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */

package com.ironiacorp.computer.filesystem;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

public class JavaResourceFinder {
	/**
	 * Line break ("LB").
	 */
	public static final String LB = System.getProperty("line.separator");

	private List<Filter<URL>> filters;

	public JavaResourceFinder()
	{
		filters = new ArrayList<Filter<URL>>();
	}
	
	public void reset()
	{
		filters.clear();
	}
	
	public List<URL> find(Class<?> c)
	{
		List<URL> result = new ArrayList<URL>();
		try {
			URL url = c.getProtectionDomain().getCodeSource().getLocation();
			if (url == null) {
				throw new NullPointerException();
			}
			result.add(url);
		} catch (Exception e) {
			String className = c.getCanonicalName();
			String classPath = className.replace('.', '/') + ".class";
			return find(classPath);
		}

		return result;
	}
	
	public List<URL> find(String resourceName)
	{
		List<URL> result = new ArrayList<URL>();
		ClassLoader cld = Thread.currentThread().getContextClassLoader();
		if (cld == null) {
			// Try bootstrap classloader (http://stackoverflow.com/a/19494116)
			cld = ClassLoader.getSystemClassLoader();
			while (cld != null & cld.getParent() != null) {
				cld = cld.getParent();
			}
		}
		try {
			Enumeration<URL> en = cld.getResources(resourceName);
			boolean mustAdd = true;
			Iterator<Filter<URL>> i = filters.iterator();
			
			while (en.hasMoreElements()) {
				URL url = en.nextElement();
				i = filters.iterator();
				while (i.hasNext()) {
					Filter<URL> filter = i.next();
					mustAdd &= filter.accept(url);
				}
				
				if (mustAdd) {
					result.add(url);
				}
			}
		} catch (IOException e) {}
		
		return result;
	}
}