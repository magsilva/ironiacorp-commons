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

import com.ironiacorp.computer.filesystem.JavaResourceFinder;

import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.Pointer;

public class Windows extends AbstractOperationalSystem
{
	public static final OperationalSystemType type = OperationalSystemType.Windows;
	
	public static final char UNIT_NAME_BEGIN = 'c';
    
	public static final char UNIT_NAME_END = 'z';
	
	public static final String DEFAULT_EXECUTABLE_EXTENSION = ".exe";

	public static final String DEFAULT_LIBRARY_EXTENSION = ".dll";

	public static final String DEFAULT_LIBRARY_PREFIX = "";
	
	public static final String PATH_SEPARATOR = ";";
	
	public static final String DIRECTORY_SEPARATOR = "\\";

	public final String[] DEFAULT_SYSTEM_DIRS = {
		"system32",
		"System32",
		"SYSTEM32",
		"system",
		"System",
		"SYSTEM",
	};

	public boolean isSystemRoot(File file)
	{
		if (file == null) {
			return false;
		}
		
		if (file.exists() && file.isDirectory()) {
			for (String dirname : DEFAULT_SYSTEM_DIRS) {
				File systemRoot = new File(file, dirname);
				if (systemRoot.exists() && systemRoot.isDirectory()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public File findSystemRoot()
	{
		final String[] defaultSystemRoot = {
				"WINDOWS",
				"Windows",
				"windows",
				"WINNT",
				"Winnt",
				"winnt",
		};
		String rootFilename = System.getenv("SYSTEMROOT");
		File root;
		
		// Try some lucky shoot
		if (rootFilename != null) {
			root = new File(rootFilename);
			return root;
		}

		// Search for the system root in the available units
	    for (char drive = UNIT_NAME_BEGIN; drive < UNIT_NAME_END; drive++) {
	    	for (String dirname : defaultSystemRoot) {
	    		root = new File(drive + ":\\" + dirname);
	    		if (isSystemRoot(root)) {
	    			return root;	
	    		}
	    	}
	    }

	    return null;
	}

	@Override
	public String getFullExecutableName(String execName)
	{
		return execName + DEFAULT_EXECUTABLE_EXTENSION;
	}

	@Override
	public String getFullLibraryName(String libName)
	{
		return libName + DEFAULT_LIBRARY_EXTENSION;
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
		
		return result;
	}
	
	
	@Override
	protected List<File> getSystemLibrarySearchPath()
	{
		List<File> result = new ArrayList<File>();
		File systemRoot = findSystemRoot();
		for (String dirname : DEFAULT_SYSTEM_DIRS) {
			File dir = new File(systemRoot, dirname);
			if (isValidPath(dir)) {
				result.add(dir);
			}
		}
		
		return result;
	}
	
	@Override
	public File findExecutable(String exec)
	{
		Filesystem fs = getFilesystem();
		String fileExtension = fs.getExtension(exec);
		if (fileExtension == null || fileExtension.isEmpty()) {
			exec = exec + DEFAULT_EXECUTABLE_EXTENSION;
		}
		
		return super.findExecutable(exec);
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
						library = filesystem.createTempFile("tmp" + DEFAULT_LIBRARY_PREFIX, DEFAULT_LIBRARY_EXTENSION);
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
			Field field = process.getClass().getDeclaredField("handle");
	        field.setAccessible(true);              
	        long processIdHandle = field.getLong(process);
	        Kernel32 kernel = Kernel32.INSTANCE;
	        WinNT.HANDLE handle = new WinNT.HANDLE();
	        handle.setPointer(Pointer.createConstant(processIdHandle));
	        long pid = kernel.GetProcessId(handle);
	        field.setAccessible(false);
	        return pid;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}
}
