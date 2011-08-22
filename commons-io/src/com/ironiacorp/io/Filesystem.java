package com.ironiacorp.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Filesystem
{
	/**
	 * Get all the files within the directory.
	 */
	public List<File> find(File baseDir)
	{
		return find(baseDir, -1, null);
	}

	/**
	 * Get all the files within the directory.
	 */
	public List<File> find(File baseDir, Pattern pattern)
	{
		return find(baseDir, -1, pattern);
	}
	
	
	/**
	 * Get all the files within the directory.
	 */
	public List<File> find(File baseDir, int depth, Pattern pattern)
	{
		if (baseDir == null || ! baseDir.isDirectory() || ! baseDir.canRead()) {
			throw new IllegalArgumentException("A valid initial directory must be provided");
		}

		
		List<File> files = new ArrayList<File>();
		for (File file : baseDir.listFiles()) {
			if (file.isFile()) {
				if (pattern == null) { 
					files.add(file);
				} else {
					Matcher matcher = pattern.matcher(file.getName());
					if (matcher.find()) {
						files.add(file);
					}
				}
			} else if (depth != 0 && file.isDirectory() && file.canRead()) {
				files.addAll(find(file, depth - 1, pattern));
			}
		}
		return files;
	}
	
}
