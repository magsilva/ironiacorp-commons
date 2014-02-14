package com.ironiacorp.java;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ironiacorp.computer.OperationalSystem;

public abstract class Java {

	public static final String JAR_PACKAGE_EXTENSION = ".jar";
	
	public static final String ZIP_PACKAGE_EXTENSION = ".zip";
	
	public static final String DEFAULT_JAVA_EXEC = "java";
	
	protected OperationalSystem os;
	
	protected Classpath classpath;
	
	protected File java;
	
	protected Map<String, String> properties;
	
	boolean enableAssertions;
	
	boolean enableSystemAssertions;

	boolean verboseClasses;

	boolean verboseGarbageCollection;

	boolean verboseJNICalls;

	public Java() {
		super();
	}

	public void setProperty(String name, int value) {
		properties.put(name, Integer.toString(value));
	}

	public void setProperty(String name, double value) {
		properties.put(name, Double.toString(value));
	}

	public void setProperty(String name, String value) {
		properties.put(name, value);
	}

	public abstract List<String> getJvmParameters();
	
	/**
	 * Run a Java application.
	 * 
	 * Options are used as documented at:
	 * http://docs.oracle.com/javase/7/docs/technotes/tools/solaris/java.html
	 */
	public void run(String classname) {
		List<String> parameters = new ArrayList<String>();
		List<String> specificParameters = getJvmParameters();
		
		// Classpath
		parameters.add("-cp");
		parameters.add(classpath.getClasspath(os));
		
		// Properties
		for (String name : properties.keySet()) {
			parameters.add("-D" + name + "=" + properties.get(name));
		}

		// Assertions
		if (enableAssertions) {
			parameters.add("-enableassertions");
		}
		if (enableSystemAssertions) {
			parameters.add("-enablesystemassertions");
		}
		
		// Verbosity
		if (verboseClasses) {
			parameters.add("verbose:class");
		}
		if (verboseGarbageCollection) {
			parameters.add("verbose:gc");
		}
		if (verboseJNICalls) {
			parameters.add("verbose:jni");
		}
		
		parameters.addAll(specificParameters);
		
		os.exec(java, parameters);
	}

}