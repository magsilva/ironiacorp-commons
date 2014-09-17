package com.ironiacorp.java;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedHashSet;

import com.ironiacorp.computer.ComputerSystem;
import com.ironiacorp.computer.OperationalSystem;
import com.ironiacorp.computer.Filesystem;
import com.ironiacorp.io.IoUtil;

public class Classpath
{
	private LinkedHashSet<File> classpath;
	
	private boolean mustVerifyClasspath;
	
	public Classpath()
	{
		classpath = new LinkedHashSet<File>();
		mustVerifyClasspath = true;
	}
	
	public int size()
	{
		return classpath.size();
	}
	
	public void add(File path)
	{
		String fileExtension;
		boolean isValidPath = false;

		if (path.isDirectory()) {
			isValidPath = true;
		} else if (path.isFile()) {
			OperationalSystem os = ComputerSystem.getCurrentOperationalSystem();
			Filesystem fs = os.getFilesystem();
			fileExtension = fs.getExtension(path.getName());
			if (fileExtension.equals(Java.JAR_PACKAGE_EXTENSION) || fileExtension.equals(Java.ZIP_PACKAGE_EXTENSION)) {
				isValidPath = true;
			}
		}
		
		if (mustVerifyClasspath) {
			if (! isValidPath) {
				throw new IllegalArgumentException("Invalid classpath: " + path);
			}
		}
		
		classpath.add(path);
	}

	public void remove(File path)
	{
		if (classpath.contains(path)) {
			classpath.remove(path);
		}
	}
	
	public String getClasspath(OperationalSystem os)
	{
		StringBuilder sb = new StringBuilder();
		Iterator<File> i = classpath.iterator();
		File currentFile;
		
		while (i.hasNext()) {
			currentFile = i.next();
			sb.append(currentFile);
			if (i.hasNext()) {
				sb.append(os.getPathSeparator());
			}
		}
		
		return sb.toString();
	}
}

