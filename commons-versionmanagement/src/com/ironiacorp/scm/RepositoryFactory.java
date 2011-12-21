/*
Wiki/RE - A requirements engineering wiki
Copyright (C) 2005 Marco Aurélio Graciotto Silva

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
*/

package com.ironiacorp.scm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ironiacorp.scm.subversion.SubversionRepository;

/**
 * The RepositoryFactory builds Repository to be used by projects. It does not
 * create the repository in fact, it just create the Repository object, that
 * encapsulates the configuration data used to access the real repository.
 */
public final class RepositoryFactory
{
	private static Logger log = LoggerFactory.getLogger(RepositoryFactory.class);
	
	public final static Class<? extends Repository> FAILSAFE_REPOSITORY_TYPE = SubversionRepository.class;

	public static Class<? extends Repository> defaultRepositoryType = SubversionRepository.class;
	
	/**
	 * We really don't want an instance of this class, so we create this
	 * private constructor.
	 */
	private RepositoryFactory()
	{
	}
	
	/**
	 * Create a repository.
	 * 
	 * @param type The repository type
	 * @param location The repository location
	 * @param username The username used to connect to the repository
	 * @param password The password used to connect to the repository
	 * 
	 * @throws IllegalArgumentException If an error is detected when
	 * instantiating the repository.
	 */
	public synchronized static Repository createRepository(Class<? extends Repository> type, String location, String username, String password)
	{
		Repository repository = null;
		if (type == null) {
			type = defaultRepositoryType;
		}
		
		try {
			repository = type.newInstance();
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid repository type: " + type.getName());
		}
		
		repository.setLocation(location);

		return repository;
	}
	
	/**
	 * @return Returns the defaultRepositoryType.
	 */
	public synchronized Class<? extends Repository> getDefaultRepositoryType()
	{
		return defaultRepositoryType;
	}

	/**
	 * @param The defaultRepositoryType to set.
	 */
	public synchronized static void setDefaultRepositoryType(Class<? extends Repository> defaultRepositoryType)
	{
		RepositoryFactory.defaultRepositoryType = defaultRepositoryType;
	}
}