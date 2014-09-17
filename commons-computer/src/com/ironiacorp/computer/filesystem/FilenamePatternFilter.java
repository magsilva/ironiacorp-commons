package com.ironiacorp.computer.filesystem;

import java.io.File;
import java.util.regex.Pattern;

public class FilenamePatternFilter implements Filter<File>
{
	private Pattern pattern;
	
	private boolean absolutePathnameEnabled = true;
	
	public FilenamePatternFilter(String regex)
	{
		this(Pattern.compile(regex));
	}
	
	public FilenamePatternFilter(Pattern pattern)
	{
		if (pattern == null) {
			throw new IllegalArgumentException(new NullPointerException());
		}
		this.pattern = pattern;
	}
	
	public boolean isAbsolutePathnameEnabled() {
		return absolutePathnameEnabled;
	}

	public void setAbsolutePathnameEnabled(boolean absolutePathnameEnabled) {
		this.absolutePathnameEnabled = absolutePathnameEnabled;
	}

	public boolean accept(File subject)
	{
		String filename = null;
		if (absolutePathnameEnabled) {
			filename = subject.getAbsolutePath();
		} else {
			filename = subject.getName();
		}
		
		return pattern.matcher(filename).matches();
	}
}
