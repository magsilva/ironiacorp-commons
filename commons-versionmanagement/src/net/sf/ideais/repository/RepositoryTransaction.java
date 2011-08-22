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

import java.io.File;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ironiacorp.io.IoUtil;

/**
 * Abstract class for a repository transaction. A repository transaction is an
 * abstraction to encapsulate the access to a project's repository (usually held
 * within a software configuration management system like CVS or Subversion).
 */
public abstract class RepositoryTransaction extends AbstractTransaction
{
	protected SourceCodeRepository repository;
	
	protected boolean completed;

	/**
	 * The directory where the local workcopy's file will be saved.
	 */
	protected String workdir;
	
	/**
	 * The changelog.
	 */
	protected String changelog;
	
	/**
	* Commons Logging instance.
	*/
	private static Log log = LogFactory.getLog( RepositoryTransaction.class );

	/**
	* Create the repository transaction.
	* 
	* @param project ProjectIF to use to retrieve the configuration items.
	* @param user UserIF to be used to access the repository.
	* 
	* @throws RepositoryTransactionError If the URL is invalid or the user's
	* configuration directory could not be created.
	*/
	public RepositoryTransaction(SourceCodeRepository repository)
	{
		super();
		log.debug( "Initialization" );
		this.repository = repository;
		String tmpdir = System.getProperty( "java.io.tmpdir" );
		if ( tmpdir != null ) {
			tmpdir += File.separator;
		}
		setWorkdir( tmpdir + repository.getShortName() + File.separator + getId() );
	}

	/**
	 * Get the workdir.
	 * 
	 * @return The directory where the local workcopy files are saved.
	 */
	public String getWorkdir()
	{
		return this.workdir;
	}

	
	/**
	 * Set the workdir. The directory will be created if it does not exist.
	 * 
	 * @param workdir Directory name to be used as work directory.
	 * 
	 * @throws RepositoryTransactionError If the directory cannot be used.
	 */
	protected void setWorkdir( String workdir )
	{
		File dir = new File( workdir );
		if ( dir.exists() && ! dir.isDirectory() ) {
			throw new RepositoryTransactionError( "exception.repositoryTransaction.invalidWorkdir" );	
		}
		try {
			dir.mkdirs();
			dir.canWrite();
		} catch ( SecurityException se ) {
			throw new RepositoryTransactionError( "exception.repositoryTransaction.invalidWorkdir" );
		}
		this.workdir = dir.getAbsolutePath() + File.separator;
	}

	/**
	 * Check if an action can be made upon the repository by this transaction.
	 * 
	 * @throws RepositoryTransactionError If the transaction has been completed (either
	 * by commiting or rolling it back).
	 */
	private void _check()
	{
		if ( completed ) {
			throw new RepositoryTransactionError( "exception.repositoryTransaction.transactionCompleted" );
		}
	}
	
	
	/**
	 * Checkout a recent (HEAD) copy of the project's repository.
	 * 
	 * @throws RepositoryTransactionError If an error occurred when checking out
	 * the files.
	 */
	public void checkout()
	{
		_check();
		log.debug( "Checkout" );
	}
	
	/**
	 * Checkout a copy of the project's repository at the given version.
	 * 
	 * @param version The version (the Subversion revision) to be retrieved.
	 * 
	 * @throws RepositoryTransactionError If an error occurred when checking out
	 * the files.
	 */
	public void checkout( String version )
	{
		_check();
		log.debug( "Checkout " + version );
	}

	/**
	 * Add a file or directory to the project's repository. If a directory is
	 * used as parameter, all the files and subdirs are added. 
	 * 
	 * @param path The file or directory to be added.
	 * 
	 * @throws RepositoryTransactionError If an error occurred when adding the
	 * file(s).
	 */
	public void add( String path )
	{
		_check();
		log.debug( "Add " + path );
	}
	
	/**
	 * Add a file or directory to the project's repository. If a directory is
	 * used as parameter and "recurse" is true, all the files and subdirs will
	 * be added. 
	 * 
	 * @param path The file or directory to be added.
	 * @param recurse If the files and subdirectories within the directory
	 * must be added.
	 * 
	 * @throws RepositoryTransactionError If an error occurred when adding the
	 * file(s).
	 */
	public void add( String path, boolean recurse )
	{
		_check();
		log.debug( "Recursive add " + path );
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
		_check();
		log.debug( "Remove " + path );
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
		_check();
		log.debug( "Revert " + path );
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
		_check();
		log.debug( "Info " + path );
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
		_check();
		log.debug( "Update " + path );
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
		_check();
		log.debug( "Update " + path + " to version " + version );
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
		_check();
		log.debug( "Branch " + srcPath + " -> " + destPath );
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
		_check();
		log.debug( "Diff " + srcPath + " and " + destPath );
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
		_check();
		log.debug( "Diff " + path + " (" + version1 + " -> " + version2 + ")" );
		return null;
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
		_check();
		log.debug( "Diff " + srcPath + " (" + version1 + ") -> " + destPath + " (" + version2 + ")" );
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
		_check();
		addChangelog( changelog );
		log.debug( "Commit " + this.changelog );
		if ( workdir != null ) {
			IoUtil.removeDir(workdir);
		}
		completed = true;
	}
	
	/**
	 * Commit the modifications made to the workcopy.
	 * 
	 * @param changelog Description of the changes made to the repository.
	 * 
	 * @throws RepositoryTransactionError If an error occurred when commiting
	 * the files.
	 */	
	public void commit()
	{
		_check();
		log.debug( "Commit" );
		commit( null );
	}
	
	
	/**
	 * Abort (rollback) the modifications made to the workcopy.
	 */	
	public void abort()
	{
		_check();
		log.debug( "Rollback" );
		if ( workdir != null ) {
			IoUtil.removeDir(workdir);
		}
		completed = true;
	}

	/**
	 * Check if the transaction has already been completed.
	 * 
	 * @return True if the transaction has completed, false otherwise.
	 */
	public boolean isCompleted()
	{
		return this.completed;
	}

	/**
	 * Get the current changelog for this transaction.
	 * 
	 * @return The current changelog.
	 */
	public String getChangelog()
	{
		_check();
		return ( changelog == null ) ? "" : changelog;
	}

	/**
	 * Set a new changelog for this transaction.
	 * 
	 * @param changelog The new changelog.
	 */
	public void setChangelog( String changelog )
	{
		_check();
		log.debug( "Set changelog to " + changelog );
		this.changelog = changelog;
	}

	/**
	 * Add a new changelog line for this transaction.
	 * 
	 * @param changelog The new changelog line.
	 */
	public void addChangelog( String changelog )
	{
		_check();
		log.debug( "Added the following message to the changelog: " + changelog );
		if ( this.changelog == null || this.changelog.length() == 0 ) {
			this.changelog = changelog;
		} else  {
			this.changelog += "\n" + changelog;
		}
	}
}