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

import net.sf.ideais.objects.ConfigurationItem;
import net.sf.ideais.objects.SourceCodeRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Dummy software configuration that does absolutely nothing.
 * 
 * @author Marco Aurélio Graciotto Silva
 */
public class DummyRepositoryTransaction extends RepositoryTransaction
{
	/**
	* Commons Logging instance.
	*/
	private static Log log = LogFactory.getLog( DummyRepositoryTransaction.class );

	/**
	* Create the repository transaction to access the Subversion.
	* 
	* @param project ProjectIF to use to retrieve the configuration items.
	* @param user UserIF to be used to access the repository.
	* 
	* @throws RepositoryTransactionError If the URL is invalid or the user's
	* configuration directory could not be created.
	*/
	public DummyRepositoryTransaction(SourceCodeRepository repository)
	{
		super(repository);
		log.debug( "Initialization" );
	}

	
	/**
	 * Checkout a copy of the project's repository.
	 * 
	 * @throws RepositoryTransactionError If an error occurred when checking out
	 * the files.
	 */
	public void checkout()
	{
		super.checkout();
	}

	/**
	 * Checkout a copy of the project's repository. The version is ignored.
	 * 
	 * @param version This should be the version to retrieve. This repository
	 * does not support versions.
	 * 
	 * @throws RepositoryTransactionError If an error occurred when checking out
	 * the files.
	 */
	public void checkout( String version )
	{
		super.checkout( version );
	}


	/**
	 * Add a file or directory to the project's repository. If a directory is
	 * used as parameter, all the files and subdirs are added. Well, a dummy
	 * repository haven't much work to do about this.
	 * 
	 * @param path The file or directory to be added.
	 * 
	 * @throws RepositoryTransactionError If an error occurred when adding the
	 * file(s).
	 */
	public void add( String path )
	{
		super.add( path );
	}

	/**
	 * Remove a file or directory from the project's repository. If a directory
	 * is used as parameter, all the files and subdirs are removed. 
	 * 
	 * @param path The file or directory to be removed.
	 * 
	 * @throws RepositoryTransactionError If an error occurred when removing
	 * the file(s).
	 */
	public void remove( String path )
	{
		super.remove( path );
	}
	
	/**
	 * Revert a file or directory to its previous stable state in the project's
	 * repository. If a directory is used as parameter, all the files and
	 * subdirs are reverted. 
	 * 
	 * @param path The file or directory to be reverted.
	 * 
	 * @throws RepositoryTransactionError If an error occurred when reverting
	 * the file(s).
	 */
	public void revert( String path )
	{
		super.revert( path );
	}

	/**
	 * Get metadata for every file in the given path. If a directory is used as
	 * parameter, metadata for every files and subdirs is retrieved. 
	 * 
	 * @param path The file or directory to have its metadata retrieved.
	 * 
	 * @return The metadata for every file in the path.
	 * 
	 * @throws RepositoryTransactionError If an error occurred when retrieving
	 * the metadata.
	 */
	public ConfigurationItem[] info( String path )
	{
		return super.info( path );
	}

	/**
	 * Update a file or directory to its latest version (the HEAD from the
	 * project's repository). If a directory is used as parameter, all the
	 * files and subdirs are updated. 
	 * 
	 * @param path The file or directory to be updated.
	 * 
	 * @throws RepositoryTransactionError If an error occurred when updating
	 * the file(s).
	 */
	public void update( String path )
	{
		super.update( path );
	}

	/**
	 * Update a file or directory to the request version. If a directory is
	 * used as parameter, all the files and subdirs are updated. 
	 * 
	 * @param path The file or directory to be updated.
	 * @param version The version to update to.
	 * 
	 * @throws RepositoryTransactionError If an error occurred when updating
	 * the file(s).
	 */
	public void update( String path, String version )
	{
		super.update( path, version );
	}
	
	/**
	 * Create a branch. 
	 * 
	 * @param srcPath The source directory (the directory that will be
	 * branched).
	 * @param destPath The branch directory that will be created.
	 * 
	 * @throws RepositoryTransactionError If an error occurred when creating the
	 * branch.
	 */
	public void branch( String srcPath, String destPath )
	{
		super.branch( srcPath, destPath );
	}

	/**
	 * Diff two files or directories. 
	 * 
	 * @param srcPath The source directory.
	 * @param destPath The destination directory.
	 * 
	 * @return The name of the file holding the diff.
	 * 
	 * @throws RepositoryTransactionError If an error occurred when comparing
	 * the files.
	 */
	public String diff( String srcPath, String destPath )
	{
		return super.diff( srcPath, destPath );
	}
	
	/**
	 * Diff different versions of a files or directory.  
	 * 
	 * @param path The file or directory whose versions will be compared.
	 * @param version1 The source version.
	 * @param version2 The destination version.
	 * 
	 * @return The name of the file holding the diff.
	 * 
	 * @throws RepositoryTransactionError If an error occurred when comparing
	 * the files.
	 */	
	public String diff( String path, String version1, String version2 )
	{
		return super.diff( path, version1, version2 );
	}

	/**
	 * Diff different versions of different files or directory.  
	 * 
	 * @param srcPath The source file or directory whose versions will be
	 * compared.
	 * @param destPath The destination file or directory whose versions will be
	 * compared.
	 * @param version1 The source version.
	 * @param version2 The destination version.
	 * 
	 * @return The name of the file holding the diff.
	 * 
	 * @throws RepositoryTransactionError If an error occurred when comparing
	 * the files.
	 */	
	public String diff( String srcPath, String version1, String destPath, String version2 )
	{
		return super.diff( srcPath, version1, destPath, version2 );
	}

	/**
	 * Commit the modifications made to the workcopy.
	 * 
	 * @param changelog Description of the changes made to the repository.
	 * 
	 * @throws RepositoryTransactionError If an error occurred when commiting
	 * the files.
	 */	
	public void commit( String changelog )
	{
		super.commit( changelog );
	}
	
	/**
	 * Abort (rollback) the modifications made to the workcopy.
	 */	
	public void abort()
	{
		super.abort();
	}


	public TransactionStatus getStatus()
	{
		// TODO Auto-generated method stub
		return null;
	}
}