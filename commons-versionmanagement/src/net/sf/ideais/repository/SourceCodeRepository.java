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

package net.sf.ideais.repository;

import java.io.File;

import net.sf.ideais.repository.RepositoryError;


/**
 * Interface for repository access.
 * 
 * @author Marco Aurélio Graciotto Silva
 */
public interface SourceCodeRepository
{

	String DUMP_SUFFIX = ".dump";

	/**
	 * Get a descriptive name for this repository.
	 * 
	 * @return The repository name.
	 */
	String getName();

	void setName( String name );

	/**
	 * Get the location (address) for this repository (an URL, e.g.).
	 * 
	 * @return The repository address.
	 */
	String getLocation();

	void setLocation( String location );

	/**
	 * Get the repository type (svn, cvs, etc). The repository type must
	 * be supported/registered at RepositoryTransactionFactory.
	 * 
	 * @return The repository type.
	 */
	String getType();

	/**
	 * Get the password used to authenticate against the repository.
	 * 
	 * @return The password (cleartext).
	 */
	String getPassword();

	/**
	 * Set the password used to authenticate against the repository.
	 * 
	 * @param password The password (cleartext).
	 */
	void setPassword( String password );

	/**
	 * Get the username used to authenticate against the repository.
	 * 
	 * @return The login username.
	 */
	String getUsername();

	/**
	 * Set the username used to authenticate against the repository.
	 * 
	 * @param username The new login username to be used.
	 */
	void setUsername( String username );

	/**
	 * Initialize a repository (if possible).
	 * 
	 * @throws RepositoryError If an error occurs while creating the repository.
	 */
	void init();

	/**
	 * Dump the repository to a file.
	 * 
	 * @return The file with the dump.
	 * 
	 * @throws RepositoryError If an error occurs while dumping the repository.
	 */
	File dump();

	/**
	 * Load a dump in the repository. Use this with caution!
	 * 
	 * @throws RepositoryError If an error occurs while loading the dump.
	 */
	void load( File dump );

	/**
	 * Check if the repository is internal (managed by the application and held within under
	 * its tree).
	 * 
	 * @return Returns the internal.
	 */
	boolean isInternal();

	/**
	 * Set the repository as internal. Internal repositories are managed by Wiki/RE.
	 * 
	 * @param internal The internal to set.
	 */
	void setInternal( boolean internal );

	String getShortName();

	/**
	 * Check if the repository is ready for use.
	 * 
	 * @return True if ready, false otherwise.
	 */
	// static boolean isReady();
}