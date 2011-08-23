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

package com.ironiacorp.computer;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;

public class LibraryLoader
{
	/**
	 * Default path for GraphViz in Unix systems.
	 */
	public static final String[] DEFAULT_UNIX_LIBPATH = {
		"/lib",
		"/usr/lib",
		"/usr/local/lib",
	};

	public static final String DEFAULT_LIB_PREFIX = "lib";

	
	public static final String DEFAULT_LIB_EXTENSION = ".so";

	private Runtime runtime;
	
	private Set<File> libpath;
	
	public LibraryLoader()
	{
		runtime = Runtime.getRuntime();
		libpath = new LinkedHashSet<File>();
	}
	
	public void addDefaultLibraryPath()
	{
		String dataModel = System.getProperty("sun.arch.data.model");
		String arch = System.getProperty("os.arch");
		File file;
		for (String path : DEFAULT_UNIX_LIBPATH) {
			// Plain path
			file = new File(path);
			addLibraryPath(file);
			
			// Plain path + data model (32 or 64)
			file = new File(path + dataModel);
			addLibraryPath(file);
			
			// Plain path + os.arch and flavours
			file = new File(path + File.separator + arch + "-linux-gnu");
			addLibraryPath(file);
			if (arch.equals("amd64")) {
				file = new File(path + File.separator + "x86_64" + "-linux-gnu");
				addLibraryPath(file);
			}
			if (arch.equals("x86_64")) {
				file = new File(path + File.separator + "amd64" + "-linux-gnu");
				addLibraryPath(file);
			}

		}
	}
	
	public void addLibraryPath(File file)
	{
		if (file != null && file.exists() && file.isDirectory()) {
			libpath.add(file);
		}
	}
	
	public void reseLibraryPath()
	{
		libpath.clear();
	}

	/**
	 * Load a native system library.
	 * 
	 * @param library Full path of the native system library.
	 * 
	 * @return True if the library has been loaded, false otherwise.
	 */
	public boolean load(File library)
	{
		if (library == null || ! library.exists() || ! library.isFile()) {
			return false;
		}
		
		try {
			runtime.load(library.getAbsolutePath());
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Load a native system library.
	 * 
	 * @param library Library name (without any prefix or suffix). For instance,
	 * to load the library 'libudev.so', use 'udev' as parameter.
	 * 
	 * @return True if the library has been loaded, false otherwise.
	 */
	public boolean load(String library)
	{
		String libname = DEFAULT_LIB_PREFIX + library + DEFAULT_LIB_EXTENSION;
    	for (File dir : libpath) {
    		File file = new File(dir, libname);
   			if (load(file) == true) {
   				return true;
   			}
    	}
    	
    	return false;
	}
}
