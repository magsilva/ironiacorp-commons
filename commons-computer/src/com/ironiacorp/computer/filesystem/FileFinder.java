package com.ironiacorp.computer.filesystem;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;


public class FileFinder
{
	private List<Filter<File>> filters;
	
	public FileFinder()
	{
		filters = new ArrayList<Filter<File>>();
	}
	
	public void reset()
	{
		filters.clear();
	}
	
	public List<File> find(String pwd, String regex)
	{
		return find(new File(pwd), Pattern.compile(regex));
	}

	public List<File> find(File pwd, Pattern regexp)
	{
		filters.add(new FilenamePatternFilter(regexp));
		return find(pwd);
	}

	public List<File> find(File pwd)
	{
		if (! pwd.isDirectory()) {
			throw new IllegalArgumentException("Invalid base directory");
		}
		
		return analyzeDir(pwd);
	}
	
	private List<File> analyzeDir(File dir)
	{
		List<File> files = new ArrayList<File>();
		File[] candidates = dir.listFiles();

		for (File file : candidates) {
			if (file.isFile()) {
				boolean mustAdd = true;
				Iterator<Filter<File>> i = filters.iterator();
				while (i.hasNext()) {
					Filter<File> filter = i.next();
					mustAdd &= filter.accept(file);
				}
				
				if (mustAdd) {
					files.add(file);
				}
			} else if (file.isDirectory()) {
				files.addAll(analyzeDir(file));
			}
		}
		
		return files;
	}
}
