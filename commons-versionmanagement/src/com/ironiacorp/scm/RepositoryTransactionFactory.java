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

import com.ironiacorp.scm.subversion.SubversionRepository;
import com.ironiacorp.scm.subversion.SubversionRepositoryTransaction;


/**
 * The RepositoryTransactionFactory builds RepositoryTransactions for the
 * underlying software configuration management system for a given project.
 * Currently, only SubVersion is supported, but this could be easyly extended
 * for CVS.
 * 
 * The real problem is not how the repository is accessed. This can be handled
 * without problems by the concrete classes, just requiring a filesystem path
 * to keep the workcopy files. However, authenticating the user can be
 * challenging: not all SCMs uses the famous user/password. For example, they
 * could use public/private keys exchanging. Such problem is delegated to the
 * concrete class, of course, but it will depend solely on the UserIF object
 * to get the required information.
 * 
 * @author Marco Aurélio Graciotto Silva
 */
public final class RepositoryTransactionFactory
{
	/**
	 * We really don't want an instance of this class, so we create this
	 * private constructor.
	 */
	private RepositoryTransactionFactory()
	{
	}
	
	/**
	 * Create a repository transaction.
	 * 
	 * @param project The project the transaction acts upon. The SCM type and
	 * access parameters (usually a URL) are provided by it.
	 * @param user The user the transaction is being created to. Any data needed
	 * for authentication, if any, will be obtained from it.
	 * 
	 * @throws RepositoryTransactionError If an error is detected when creating
	 * the transaction (repository type not supported or fatal error when
	 * initializing the transacton).
	 */
	public static RepositoryTransaction createRepositoryTransaction(Repository repository)
	{
		RepositoryTransaction tx = null;
		if ( repository == null ) {
			throw new RepositoryTransactionError( "exception.repositoryTransaction.projectWithoutRepository" );
		}
		try {
			if ( repository instanceof SubversionRepository) {
				tx = new SubversionRepositoryTransaction(repository);
			}
		} catch ( Exception e ) {
			throw new RepositoryTransactionError( "exception.repositoryTransaction.transactionInitialization", e );
		}
		
		// If no valid type was identified, throw an exception.
		if ( tx == null ) {
			throw new RepositoryTransactionError( "exception.repositoryTransaction.invalidRepositoryType" );
		}
		
		return tx;
	}
}