/*
This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

Copyright (C) 2005 Marco Aurélio Graciotto Silva <magsilva@gmail.com>
*/


package com.ironiacorp.scm;

/**
 * Common data format for configuration item descriptions. Every SCM employ
 * a different mechanism to describe the files they store. It would be a
 * nightmare (if not impossible) to the application get hold of that. This
 * class is (supposed to be) the minimum common denominator about the files
 * under version control.
 */
public class ConfigurationItem
{
	/**
	 * The file's author name.
	 */
	private String author;
	
	/**
	 * The file's version.
	 */
	private String version;
	
	/**
	 * File or directory status:
	 * <ul>
	 * 	<li>ADDED: File or directory marked as added.</li>
	 * 	<li>CONFLICTED: File or directory is conflicting with the repository.</li>
	 * 	<li>EXTERNAL: File or directory is external to this repository.</li>
	 * 	<li>IGNORED: File is not under version control and it's ignored.</li>
	 * 	<li>INCOMPLETE: Directory is missing some files.</li>
	 * 	<li>MERGED: File or directory has been merged.</li>
	 * 	<li>MISSING: File or directory is missing.</li>
	 * 	<li>MODIFIED: File or directory has been modified.</li>
	 * 	<li>NORMAL: The file has not been changed.</li>
	 * 	<li>REMOVED: The file has been removed.</li>
	 * 	<li>REPLACED: The file has been removed and then a new file was added
	 *  with the same name.</li>
	 * 	<li>UNVERSIONED: The file is not under version control.</li>
	 * </ul>
	 */
	public enum Status {
		ADDED,
		CONFLICTED,
		EXTERNAL,
		IGNORED,
		INCOMPLETE,
		MERGED,
		MISSING,
		MODIFIED,
		NORMAL,
		REMOVED,
		REPLACED,
		UNVERSIONED
	}
	
	private Status status;
	
	
	/**
	 * The file's name.
	 */
	private String filename;
	
	/**
	 * Create a configuration item description.
	 */
	public ConfigurationItem( String filename )
	{
		this.filename = filename;
	}
	
	public String getAuthor()
	{
		return this.author;
	}
	
	public String getFilename()
	{
		return this.filename;
	}
	
	public String getVersion()
	{
		return this.version;
	}

	public Status getStatus()
	{
		return this.status;
	}
	

	public void setStatus( Status status )
	{
		this.status = status;
	}

	public void setAuthor( String author )
	{
		this.author = author;
	}
	

	public void setVersion( String version )
	{
		this.version = version;
	}
}