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
import java.util.List;
import java.util.Map;

public interface OperationalSystem
{
	void addExecutableSearchPath(File dir);

	void removeExecutableSearchPath(File dir);

	List<File> getExecutableSearchPath();

	File findExecutable(String execName);
	
	String getFullExecutableName(String execName);
	
	boolean isExecutable(File execFile);

	ProcessBuilder exec(File execFile);
	
	ProcessBuilder exec(File execFile, List<String> parameters);
	
	ProcessBuilder exec(File execFile, List<String> parameters, Map<String, String> env);
	
	long getPid();
	
	void addLibrarySearchPath(File dir);
	
	void removeLibrarySearchPath(File dir);

	List<File> getLibrarySearchPath();
	
	/**
	 * Find a native system library.
	 * 
	 * @param library Library name (without any prefix or suffix). For instance,
	 * to load the library 'libudev.so', use 'udev' as parameter.
	 * 
	 * @return Null if the library could not be found, otherwise it is the file
	 * that matches the requested library.
	 */
	File findLibrary(String libName);
	
	String getDefaultLibraryPrefix();
	
	String getDefaultLibrarySuffix();
	
	String getFullLibraryName(String libName);
	
	boolean isLoadable(File libFile);

	/**
	 * Load a native system library.
	 * 
	 * @param libFile File for the native system library.
	 * 
	 * @throws IllegalArgumentException if library could not be loaded.
	 */
	void loadLibrary(File libFile);
	
	OperationalSystemType getType();
	
	String getNickname();
	
	String getNickname(ComputerArchitecture arch);
	
	String getPathSeparator();
	
	String getDirectorySeparator();
	
	Filesystem getFilesystem();
	
	void setEnvironmentVariable(String name, String value);
	
	String getEnvironmentVariable(String name);
}
