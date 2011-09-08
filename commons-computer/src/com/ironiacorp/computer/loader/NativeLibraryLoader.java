package com.ironiacorp.computer.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ironiacorp.computer.OperationalSystemType;
import com.ironiacorp.computer.OperationalSystemDetector;
import com.ironiacorp.computer.environment.PathEnvironmentVariable;
import com.ironiacorp.computer.environment.PathJVMEnvironmentVariable;
import com.ironiacorp.computer.environment.PathSystemEnvironmentVariable;

public class NativeLibraryLoader
{
	private String[] getJavaLibraryLocationProperties()
	{
		String[] properties = {
				"java.class.path",
				"java.endorsed.dirs",
				"java.ext.dirs"
		};
		
		return properties;
	}
	
	private String[] parseLinuxLDConf()
	{
		return parseLinuxLDConf("/etc/ld.so.conf");
	}
	
	private String[] parseLinuxLDConf(String filename)
	{
		List<String> directories = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader("/etc/ld.so.conf"));
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.startsWith("include")) {
					String pattern = line.replaceFirst("include ", "");
//					directories.addAll(c);
				}
			}
		} catch (IOException e) {
		}
		
		return directories.toArray(new String[0]);
	}
		
	private String[] getLibraryPath()
	{
		List<String> path = new ArrayList<String>();
		OperationalSystemDetector osDetector = new OperationalSystemDetector();
		
		if (osDetector.detectCurrentOS().unixCompatible) {
			PathEnvironmentVariable libraryPathEnvVar = new PathSystemEnvironmentVariable("LD_LIBRARY_PATH");
			for (String s : libraryPathEnvVar.getValue()) {
				path.add(s);
			}
		}
		
		if (osDetector.detectCurrentOS() == OperationalSystemType.Linux) {
			path.add("/lib");
			path.add("/usr/lib");
			path.add("/usr/local/lib");
			
			for (String s : parseLinuxLDConf()) {
				path.add(s);
			}
		}
		
		if (osDetector.detectCurrentOS() == OperationalSystemType.Windows) {
			path.add(System.getenv("windir"));
			path.add(System.getenv("windir") + File.separator + "system");
			path.add(System.getenv("windir") + File.separator + "system32");
		}
		
		for (String property : getJavaLibraryLocationProperties()) {
			PathEnvironmentVariable libraryPathJava = new PathJVMEnvironmentVariable(property);
			for (String s : libraryPathJava.getValue()) {
				path.add(s);
			}
		}
		
		return path.toArray(new String[0]);
	}
	
	/**
	 * Load the library (lib*.so).
	 */
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
}
