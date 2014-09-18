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
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.ironiacorp.computer.environment.PathEnvironmentVariable;
import com.ironiacorp.computer.environment.PathJVMEnvironmentVariable;

public abstract class AbstractOperationalSystem implements OperationalSystem 
{
	protected List<File> extraExecutableSearchPaths;
	
	protected List<File> bogusExecutableSearchPaths;

	protected List<File> extraLibrarySearchPaths;
	
	protected List<File> bogusLibrarySearchPaths;

	private static final String[] JAVA_LIBRARY_PATHS = {
			"java.class.path",
			"java.endorsed.dirs",
			"java.ext.dirs"
	};

	
	public AbstractOperationalSystem()
	{
		extraExecutableSearchPaths = new LinkedList<File>();
		bogusExecutableSearchPaths = new LinkedList<File>();
		
		extraLibrarySearchPaths = new LinkedList<File>();
		bogusLibrarySearchPaths = new LinkedList<File>();
	}
	
	protected abstract List<File> getSystemExecutableSearchPath();

	
	@Override
	public void addExecutableSearchPath(File dir)
	{
		if (isValidPath(dir)) {
			extraExecutableSearchPaths.add(dir);
		}
	}

	@Override
	public void removeExecutableSearchPath(File dir)
	{
		if (isValidPath(dir)) {
			bogusExecutableSearchPaths.add(dir);
		}
	}
	
	@Override
	public boolean isExecutable(File exec)
	{
		if (exec == null || ! exec.exists() || ! exec.isFile() || ! exec.canExecute()) {
			return false;
		}
		
		return true;
	}
	
	@Override
	public File findExecutable(String exec)
	{
		String fullname = getFullExecutableName(exec);
    	for (File dir : getExecutableSearchPath()) {
    		File file = new File(dir, fullname);
   			if (isExecutable(file)) {
   				return file;
   			}
    	}
    	
    	return null;
	}

	@Override
	public List<File> getExecutableSearchPath()
	{
		List<File> searchPath = getSystemExecutableSearchPath();
		searchPath.addAll(extraExecutableSearchPaths);
		searchPath.removeAll(bogusExecutableSearchPaths);
		
		return searchPath;
	}

	
	@Override
	public ProcessBuilder exec(File execFile)
	{
		return exec(execFile, null);
	}

	@Override
	public ProcessBuilder exec(File execFile, List<String> parameters)
	{
		return exec(execFile, parameters, null);
	}
	
	@Override
	public ProcessBuilder exec(File execFile, List<String> parameters, Map<String, String> env)
	{
		
		if (! isExecutable(execFile)) {
			throw new IllegalArgumentException("Invalid executable file");
		}
		
		ProcessBuilder pb;
		if (parameters != null) {
			parameters.add(0, execFile.getAbsolutePath());
			pb = new ProcessBuilder(parameters.toArray(new String[parameters.size()]));
		} else {
			pb = new ProcessBuilder(execFile.getAbsolutePath());
		}
		
		if (env != null) {
			pb.environment().clear();
			pb.environment().putAll(env);
		}
		
		return pb;
	}
	
	
	protected boolean isValidPath(File dir)
	{
		return (dir != null && dir.exists() && dir.isDirectory());
	}
	
	@Override
	public void addLibrarySearchPath(File dir)
	{
		if (isValidPath(dir)) {
			extraLibrarySearchPaths.add(dir);
		}
	}

	@Override
	public void removeLibrarySearchPath(File dir)
	{
		if (isValidPath(dir)) {
			bogusLibrarySearchPaths.add(dir);
		}
	}
	
	protected abstract List<File> getSystemLibrarySearchPath();

	@Override
	public List<File> getLibrarySearchPath()
	{
		List<File> searchPath = getSystemLibrarySearchPath();
		
		for (String property : JAVA_LIBRARY_PATHS) {
			PathEnvironmentVariable libraryPathJava = new PathJVMEnvironmentVariable(property);
			for (String s : libraryPathJava.getValue()) {
				File file = new File(s);
				if (file.exists()) {
					if (file.isDirectory()) {
						searchPath.add(file);
					} else {
						searchPath.add(file.getParentFile());
					}
				}
			}
		}
		
		searchPath.addAll(extraLibrarySearchPaths);
		searchPath.removeAll(bogusLibrarySearchPaths);
		
		return searchPath;
	}
	
	@Override
	public boolean isLoadable(File libFile)
	{
		if (libFile == null || ! libFile.exists() || ! libFile.isFile()) {
			return false;
		}
		return true;
	}
	

	@Override
	public void loadLibrary(File library)
	{
		if (! isLoadable(library)) {
			throw new IllegalArgumentException("Invalid file: " + library);
		}
		try {
			Runtime runtime = Runtime.getRuntime();
			runtime.load(library.getAbsolutePath());
		} catch (Throwable e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	
	/**
	 * Load the library (lib*.so).
	public void loadLibrary(String name)
	{
		try {
			Runtime.getRuntime().loadLibrary(name);
		} catch (UnsatisfiedLinkError ule) {
			findAndLoadLibrary(name);
		}
	}
	
	public void findAndLoadLibrary(String name)
	{
		String libname = System.mapLibraryName(name);
		
		// Try to find the library in the library path.
		for (String path : getLibraryPath()) {
			try {
				System.load(path + File.separator + libname);
				return;
			} catch (UnsatisfiedLinkError enf) {
			}
		}
		
		throw new UnsatisfiedLinkError("Library '" + name + "' not found");
	}
	*/
	
	@Override
	public File findLibrary(String libName)
	{
		String fullname = getFullLibraryName(libName);
		Filesystem fs = getFilesystem();
    	for (File dir : getLibrarySearchPath()) { // getLibraryPath
    		List<File> files = fs.find(dir, 0, Pattern.compile(fullname + "(\\.(\\d+))?"));
    		for (File file : files) {
	    		if (isLoadable(file)) {
	    			return file;
	   			}
    		}
    		
    		// Also try with short name
    		files = fs.find(dir, 0, Pattern.compile(libName + "(\\.(\\d+))?" + getDefaultLibrarySuffix()));
    		for (File file : files) {
	    		if (isLoadable(file)) {
	    			return file;
	   			}
    		}
    	}

    	return null;
	}
	
	public String getNickname()
	{
		return getType().nickname;
	}
	
	public String getNickname(ComputerArchitecture arch)
	{
		return getType().nickname + arch.width;
	}
	
	public Filesystem getFilesystem() {
		return new Filesystem(this);
	}

	@Override
	public void setEnvironmentVariable(String name, String value) {
		try {
			// Solution from http://stackoverflow.com/a/7201825
			Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
	        Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
	        theEnvironmentField.setAccessible(true);
	        Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
	        env.put(name, value);
	        Field theCaseInsensitiveEnvironmentField = processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
	        theCaseInsensitiveEnvironmentField.setAccessible(true);
	        Map<String, String> cienv = (Map<String, String>) theCaseInsensitiveEnvironmentField.get(null);
	        env.put(name, value);
	    } catch (Exception e1) {
			// Solution from http://stackoverflow.com/a/496849
			Class<?>[] classes = Collections.class.getDeclaredClasses();
		    Map<String, String> env = System.getenv();
		    for (Class<?> cl : classes) {
		        if("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
		        	try {
			            Field field = cl.getDeclaredField("m");
			            field.setAccessible(true);
			            Object obj = field.get(env);
			            Map<String, String> map = (Map<String, String>) obj;
			            map.put(name, value);
		        	} catch (Exception e2) {
		        		throw new UnsupportedOperationException(e2); 
		        	}
		        }
		    }
	    }
		
   }

	@Override
	public String getEnvironmentVariable(String name) {
		String value = System.getenv(name);
		if (value == null) {
			throw new IllegalArgumentException("Environment variable not defined: " + name);
		}
		return value;
	}

	
	
}

