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


package com.ironiacorp.scm.fs;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ironiacorp.io.IoUtil;
import com.ironiacorp.scm.ConfigurationItem;
import com.ironiacorp.scm.RepositoryTransaction;
import com.ironiacorp.scm.RepositoryTransactionError;
import com.ironiacorp.scm.Repository;
import com.ironiacorp.scm.TransactionStatus;

/**
 * Dummy software configuration just save the files. Actually, there's no
 * software configuration at all.
 * 
 * TODO: Create a FilesystemClient and delegate to it most of the repository actions,
 * pretty much like the SVNClient. Maybe this could be even a fork of a RCSClient (jCVS?).
 * 
 * @author Marco Aurélio Graciotto Silva
 */
public class FilesystemRepositoryTransaction extends RepositoryTransaction
{
	/**
	 * The directory where the project's files, shared by all users, is saved.
	 */
	private String repdir;
	

	/**
	 * Counter used by createTempFile.
	 */
	private static AtomicLong counter = new AtomicLong( Double.doubleToLongBits( Math.random() ) );

	/**
	* Commons Logging instance.
	*/
	private static Log log = LogFactory.getLog( FilesystemRepositoryTransaction.class );

	/**
	* Create the repository transaction to access the Subversion.
	* 
	* @param project ProjectIF to use to retrieve the configuration items.
	* @param user UserIF to be used to access the repository.
	* 
	* @throws RepositoryTransactionError If the URL is invalid or the user's
	* configuration directory could not be created.
	*/
	public FilesystemRepositoryTransaction(Repository repository)
	{
		super(repository);
		setRepdir(repository.getLocation());
	}

	/**
	 * Set the repository directory. The directory will be created if it does
	 * not exist.
	 * 
	 * @param repdir Directory that holds the project's files.
	 * 
	 * @throws RepositoryTransactionError If the directory cannot be used.
	 */
	protected void setRepdir( String repdir )
	{
		File dir = new File( repdir );
		if ( dir.exists() && ! dir.isDirectory() ) {
			throw new RepositoryTransactionError( "exception.repositoryTransaction.invalidWorkdir" );	
		}
		try {
			dir.mkdirs();
			dir.canWrite();
		} catch ( SecurityException se ) {
			throw new RepositoryTransactionError( "exception.repositoryTransaction.invalidWorkdir" );
		}
		this.repdir = repdir;
	}
	
	/**
	 * Checkout a copy of the project's repository.
	 * 
	 * @throws RepositoryTransactionError If an error occurred when checking out
	 * the files.
	 */
	public void checkout()
	{
		_checkout();
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
		_checkout();
	}

	/**
	 * Checkout a copy of the project's repository.
	 * 
	 * @throws RepositoryTransactionError If an error occurred when checking out
	 * the files.
	 */
	private void _checkout()
	{
		try {
			IoUtil.copyDir(repdir, workdir, true );
		} catch ( IOException e ) {
			throw new RepositoryTransactionError( "exception.repositoryTransaction.checkout", e );
		}
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
		File file = new File( path );
		try {
			if ( file.isDirectory() ) {
				IoUtil.copyDir( repdir + File.separator + path,
					workdir + File.separator + path );
			} else {
				IoUtil.copyFile( repdir + File.separator + path,
					workdir + File.separator + path );
			}
		} catch ( IOException e ) {
			throw new RepositoryTransactionError( "exception.repositoryTransaction.revert", e );
		}
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
		/*
		try {
			ArrayList<ConfigurationItem> items = new ArrayList<ConfigurationItem>();
			for ( File file : Io.find( path ) ) {
				ConfigurationItem ci = new ConfigurationItem( file.getName() );
				ci.setVersion( "" );
				ci.setAuthor( "" );
				// ci.setStatus( ConfigurationItem.Status.ADDED );
				// ci.setStatus( ConfigurationItem.Status.REMOVED );
				// ci.setStatus( ConfigurationItem.Status.IGNORED );
				// ci.setStatus( ConfigurationItem.Status.INCOMPLETE );
				// ci.setStatus( ConfigurationItem.Status.MISSING );
				// ci.setStatus( ConfigurationItem.Status.MODIFIED );
				// ci.setStatus( ConfigurationItem.Status.NORMAL );
				items.add( ci );
			}
			return (ConfigurationItem[])items.toArray();
		} catch ( IOException e ) {
			throw new RepositoryTransactionError( "exception.repositoryTransaction.info", e );
		}
		*/
		return null;
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
		_update( path );
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
		_update( path );
	}
	
	/**
	 * Update a file or directory to the requested revision. If a directory is
	 * used as parameter, all the files and subdirs are updated. 
	 * 
	 * @param path The file or directory to be updated.
	 * @param revision The revision to update to.
	 * 
	 * @throws RepositoryTransactionError If an error occurred when updating
	 * the file(s).
	 */
	private void _update( String path )
	{
		/*
		try {
		} catch ( IOException e ) {
			throw new RepositoryTransactionError( "exception.repositoryTransaction.update", e );
		}
		*/
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
		/*
		try {
		} catch ( IOException e ) {
			throw new RepositoryTransactionError( "exception.repositoryTransaction.branch", e );
		}
		*/
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
		/*
		try {
		} catch ( IOException e ) {
			throw new RepositoryTransactionError( "exception.repositoryTransaction.diff", e );
		}
		*/
		return null;
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
		return diff( path, version1, path, version2 );
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
		/*
		try {
		} catch ( IOException e ) {
			throw new RepositoryTransactionError( "exception.repositoryTransaction.diff", e );
		}
		*/
		return null;
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
		/*
		try {
		} catch ( IOException e ) {
			throw new RepositoryTransactionError( "exception.repositoryTransaction.commit", e );
		}
		*/
	}
	
	/**
	 * Abort (rollback) the modifications made to the workcopy.
	 */	
	public void abort()
	{
	}

	public TransactionStatus getStatus()
	{
		// TODO Auto-generated method stub
		return null;
	}
}