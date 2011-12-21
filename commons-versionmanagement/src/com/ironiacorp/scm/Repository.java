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

import java.io.File;

import com.ironiacorp.credentials.Credential;


/**
 * Interface for repository access.
 * 
 * @author Marco Aurélio Graciotto Silva
 */
public interface Repository
{
	/**
	 * Get the location (address) for this repository (an URL, e.g.).
	 * 
	 * @return The repository address.
	 */
	String getLocation();

	/**
	 * Set the location of the repository.
	 * 
	 * @param location
	 */
	void setLocation(String location);

	
	/**
	 * Set credential required to access the repository.
	 */
	void setCredential(Credential<?> credential);
	
	
	/**
	 * Get credential used to access the repository.
	 * @return
	 */
	Credential<?> getCredential();


	/**
	 * Get a working copy for the given repository 

	 * @return Working copy.
	 */
	WorkingCopy checkout(File workDir);
	
	/**
	 * Get a working copy for the given repository 
	 *
	 * @return Working copy.
	 */
	void init();

}