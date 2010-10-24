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

package net.sf.ideais.repository;

import java.util.ArrayList;

import net.sf.ideais.objects.SourceCodeRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The RepositoryFactory builds Repository to be used by projects. It does not
 * create the repository in fact, it just create the Repository object, that
 * encapsulates the configuration data used to access the real repository.
 * Setting up the repository is outside Wiki/RE scope.
 * 
 * @author Marco Aurélio Graciotto Silva
 */
public final class RepositoryFactory
{
	public final static String FAILSAFE_REPOSITORY_TYPE = SubversionRepository.TYPE;

	/**
	* Commons Logging instance.
	*/
	private static Log log = LogFactory.getLog( RepositoryFactory.class );
	
	private static String defaultRepositoryType;
	
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
	 * @param name A name for the humble repository to be born
	 * @param type The repository type
	 * @param location The repository location
	 * @param username The username used to connect to the repository
	 * @param password The password used to connect to the repository
	 * 
	 * @throws RepositoryError If an error is detected when creating
	 * the transaction (repository type not supported or fatal error when
	 * initializing the transacton).
	 */
	public synchronized static SourceCodeRepository createRepository( String name, String type,
			String location, String username, String password )
	{
		SourceCodeRepository repository = null;
		if ( type == null ) {
			type = defaultRepositoryType;
		}
		
		if ( type == null ) {
			throw new IllegalArgumentException( "exception.repository.unknownRepositoryType" );
		} else if ( type.equals( SubversionRepository.TYPE ) ) {
			repository = new SubversionRepository();
		} else {
			throw new IllegalArgumentException( "exception.repository.unknownRepositoryType" );
		}
		
		repository.setName( name );
		repository.setLocation( location );
		repository.setUsername( username );
		repository.setPassword( password );

		return repository;
	}
	
	/**
	 * Get the supported repository types.
	 * 
	 * @return The supported repository types.
	 */
	public static String[] getSupportedTypes()
	{
		ArrayList<String> types = new ArrayList<String>();
		types.add( SubversionRepository.TYPE );
		
		return types.toArray( new String[ 0 ] );
	}

	/**
	 * @return Returns the defaultRepositoryType.
	 */
	public synchronized static String getDefaultRepositoryType()
	{
		return defaultRepositoryType;
	}

	/**
	 * @param The defaultRepositoryType to set.
	 */
	public synchronized static void setDefaultRepositoryType( String defaultRepositoryType )
	{
		RepositoryFactory.defaultRepositoryType = defaultRepositoryType;
	}
	
	/**
	 * Check repositories setup (if the required classes and libraries are available).
	 * Actually this delegates the function for each RepositoryIF implementation
	 * registered in the factory.
	 * 
	 * @return True if ok, False or an exception if not ready.
	 * @throws RepositoryError If not ready.
	 */
	public static boolean isReady()
	{
		return RepositoryFactory.isReady( false );
	}
	
	/**
	 * Check repositories setup (if the required classes and libraries are available).
	 * Actually this delegates the function for each RepositoryIF implementation
	 * registered in the factory.
	 * 
	 * @param force Try to force repository's setup. This is not recommended.
	 * 
	 * @return True if ok, False or an exception if not ready.
	 * 
	 * @throws RepositoryError If not ready.
	 */
	public static boolean isReady( boolean force )
	{
		boolean ready = true;
		
		try {
			SubversionRepository.isReady( force );
			log.debug( "Subversion repository is ready" );
		} catch ( Throwable e ) {
			throw new RepositoryError( "exception.repository.notReady", e );
		}
		
		return ready;
	} 
}