package com.ironiacorp.ui.gui;


import java.io.File;
import java.io.FileFilter;


public final class Filters
{
	public static final class FileExtensionFilter extends javax.swing.filechooser.FileFilter implements FileFilter
	{
		private String extension;
		
		private String description;
		
		public String getExtension()
		{
			return extension;
		}

		public void setExtension(String extension)
		{
			this.extension = extension;
		}

		public void setDescription(String description)
		{
			this.description = description;
		}

		public boolean accept(File filename)
		{
			return filename.getName().endsWith(extension);
		}

		public String getDescription()
		{
			return description;
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
