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
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.ironiacorp.computer.environment.PathEnvironmentVariable;
import com.ironiacorp.computer.environment.PathSystemEnvironmentVariable;
import com.ironiacorp.computer.filesystem.JavaResourceFinder;
import com.ironiacorp.computer.loader.LDConfigParser;

public class Unix extends AbstractOperationalSystem
{
	public static final OperationalSystemType type = OperationalSystemType.Linux;
		
	public static final String DEFAULT_EXEC_EXTENSION = "";

	public static final String DEFAULT_LIBRARY_EXTENSION = ".so";

	public static final String DEFAULT_LIBRARY_PREFIX = "lib";
	
	public static final String PATH_SEPARATOR = ":";
	
	public static final String DIRECTORY_SEPARATOR = "/";
	
	/**
	 * Default path for GraphViz in Unix systems.
	 */
	public static final String[] DEFAULT_EXEC_PATH = {
		"/bin",
		"/sbin",
		"/usr/bin",
		"/usr/sbin",
		"/usr/local/bin",
		"/usr/local/sbin",
	};
	
	/**
	 * Default path for GraphViz in Unix systems.
	 */
	public static final String[] DEFAULT_LIBRARY_PATH = {
		"/lib",
		"/usr/lib",
		"/usr/local/lib",
	};

	@Override
	public String getFullLibraryName(String libName)
	{
		return DEFAULT_LIBRARY_PREFIX + libName + DEFAULT_LIBRARY_EXTENSION;	
	}
	
	// x86_64-linux-gnu
	@Override
	protected List<File> getSystemLibrarySearchPath()
	{
		ComputerArchitectureDetector archDetector = new ComputerArchitectureDetector();
		ComputerArchitecture arch = archDetector.detectCurrentArchitecture();
		String dataModel = System.getProperty("sun.arch.data.model");
		List<String> rawResult = new ArrayList<String>();
		List<File> result = new ArrayList<File>();

		for (String path : DEFAULT_LIBRARY_PATH) {
			File dir = new File(path);
			if (isValidPath(dir)) {
				rawResult.add(path);
			}
		}

		// Add paths from LD_LIBRARY_PATH
		try {
			PathEnvironmentVariable libraryPathEnvVar = new PathSystemEnvironmentVariable("LD_LIBRARY_PATH");
			for (String path : libraryPathEnvVar.getValue()) {
				File dir = new File(path);
				if (isValidPath(dir)) {
					rawResult.add(path);
				}
			}
		} catch (IllegalArgumentException e) {}

		LDConfigParser ldParser = new LDConfigParser();
		for (String path : ldParser.parse()) {
			File dir = new File(path);
			if (isValidPath(dir)) {
				rawResult.add(path);
			}
		}

		for (String dirname : rawResult) {
			// Plain path + data model (32 or 64)
			File dir = new File(dirname + dataModel);
			if (isValidPath(dir)) {
				result.add(dir);
			}
			
			// Plain path + os.arch and flavours
			dir = new File(dirname + File.separator + arch.toString() + "-linux-gnu");
			if (isValidPath(dir)) {
				result.add(dir);
			}
			for (String acronym : arch.acronyms) {
				dir = new File(dirname + File.separator + acronym + "-linux-gnu");
				if (isValidPath(dir)) {
					result.add(dir);
				}
			}
		}
		
		return result;
	}
	

	@Override
	public String getFullExecutableName(String execName)
	{
		return execName;
	}
	
	@Override
	protected List<File> getSystemExecutableSearchPath()
	{
		String currentSearchPath = System.getenv("PATH");
		List<File> result = new ArrayList<File>();
		for (String dirname : currentSearchPath.split(File.pathSeparator)) {
			File dir = new File(dirname);
			if (isValidPath(dir)) {
				result.add(dir);
			}
		}

		for (String path : DEFAULT_EXEC_PATH) {
			File dir = new File(path);
			if (isValidPath(dir)) {
				result.add(dir);
			}
		}

		return result;
	}
	
	@Override
	public OperationalSystemType getType() {
		return type;
	}
	
	@Override
	public String getDirectorySeparator() {
		return DIRECTORY_SEPARATOR;
	}
	
	@Override
	public String getPathSeparator() {
		return PATH_SEPARATOR;
	}
	
	
	@Override
	public File findLibrary(String libName)
	{
		File library = super.findLibrary(libName);
		if (library != null) {
			return library;
		}
		
		// Try to find library in the classpath
		JavaResourceFinder finder = new JavaResourceFinder();
		ComputerArchitectureDetector archDetector = new ComputerArchitectureDetector();
		ComputerArchitecture arch = archDetector.detectCurrentArchitecture();
		for (String acronym : arch.acronyms) {
			String libnameWithArch = DEFAULT_LIBRARY_PREFIX + libName + "." + acronym + DEFAULT_LIBRARY_EXTENSION;
			List<URL> results = finder.find(libnameWithArch);
			Iterator<URL> i = results.iterator();
			while (i.hasNext()) {
				String uriPath = i.next().toString();
				try {
					URI uri = new URI(uriPath.replace("%20", " "));
					library = new File(uri.getSchemeSpecificPart());
					if (! library.exists()) {
						InputStream is = Unix.class.getResourceAsStream("/" + libnameWithArch);
						OperationalSystem os = ComputerSystem.getCurrentOperationalSystem();
						Filesystem filesystem = os.getFilesystem();
						Path libraryPath;
						library = filesystem.createTempFile(DEFAULT_LIBRARY_PREFIX, DEFAULT_LIBRARY_EXTENSION);
						libraryPath = library.toPath();
						library.delete();
						Files.copy(is, libraryPath);
						library.deleteOnExit();

					}
					return library;
				} catch (Exception e) {
					System.out.println("Error loading library: " + library);
					System.out.println(e.getMessage());
				}
			}
		}
		
	    	return null;
	}

	@Override
	public String getDefaultLibraryPrefix() {
		return DEFAULT_LIBRARY_PREFIX;
	}

	@Override
	public String getDefaultLibrarySuffix() {
		return DEFAULT_LIBRARY_EXTENSION;
	}

	@Override
	public long getPid(Process process) {
		try {
			Field field = process.getClass().getDeclaredField("pid");
			field.setAccessible(true);
			long pid = field.getLong(process);
			field.setAccessible(false);
			return pid;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	/* Old code, for Java 8
	@Override
	public long getPid() {
		byte[] bo = new byte[256];
		try (InputStream is = new FileInputStream("/proc/self/stat")) {
			is.read(bo);
			for (int i = 0; i < bo.length; i++) {
				if ((bo[i] < '0') || (bo[i] > '9')) {
					return Integer.parseInt(new String(bo, 0, i));
				}
			}
		} catch (IOException e) {
			return -1;
		}
		
		return 0;
	}
	*/
}
