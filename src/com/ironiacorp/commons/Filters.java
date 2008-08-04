package com.ironiacorp.commons;

import java.io.File;
import java.io.FileFilter;

public final class Filters
{
	public static final class ClassFileFilter extends javax.swing.filechooser.FileFilter implements FileFilter
	{
		public boolean accept(File filename)
		{
			return filename.getName().endsWith(ReflectionUtil.CLASS_FILE_EXTENSION);
		}

		public String getDescription()
		{
			return "Java bytecode (.class) files";
		}
	
	}

	public static final class DirectoryFilter extends javax.swing.filechooser.FileFilter implements FileFilter
	{
		public boolean accept(File filename)
		{
			return filename.isDirectory();
		}

		public String getDescription()
		{
			return "Directory";
		}
	}

	public static final class JarFilter extends javax.swing.filechooser.FileFilter implements FileFilter
	{
		public boolean accept(File f)
		{
			if (f.isDirectory()) {
				return true;
			}
			return f.getName().toLowerCase().endsWith(".jar") || f.getName().toLowerCase().endsWith(".zip");
		}

		public String getDescription()
		{
			return "Java package (JAR or ZIP archive or a directory)";
		}

	}
}
