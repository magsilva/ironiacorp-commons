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

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ironiacorp.io.IoUtil;

/**
 * Abstract class for a repository transaction. A repository transaction is an
 * abstraction to encapsulate the access to a project's repository (usually held
 * within a software configuration management system like CVS or Subversion).
 */
public abstract class RepositoryTransaction extends AbstractTransaction
{
	/**
	 * Commons Logging instance.
	 */
	private static Logger log = LoggerFactory.getLogger(RepositoryTransaction.class);

	protected Repository repository;
	
	protected File workdir;
	
	protected boolean completed;

	/**
	 * The directory where the local workcopy's file will be saved.
	 */
	protected WorkingCopy wc;
	
	/**
	 * The changelog.
	 */
	protected String changelog;
	
	/**
	* Create the repository transaction.
	* 
	* @param project ProjectIF to use to retrieve the configuration items.
	* @param user UserIF to be used to access the repository.
	* 
	* @throws RepositoryTransactionError If the URL is invalid or the user's
	* configuration directory could not be created.
	*/
	public RepositoryTransaction(Repository repository)
	{
		super();
		log.debug( "Initialization" );
		this.repository = repository;
		String tmpdir = System.getProperty("java.io.tmpdir");
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
	public File getWorkdir()
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
		this.workdir = new File(dir.getAbsolutePath() + File.separator);
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

	public void add(String path) {
		// TODO Auto-generated method stub
		
	}

	public void branch(String srcPath, String destPath) {
		// TODO Auto-generated method stub
		
	}

	public String diff(String srcPath, String version1, String destPath, String version2) {
		return null;
		
	}

	public String diff(String path, String version1, String version2) {
		return null;
		// TODO Auto-generated method stub
		
	}

	public String diff(String srcPath, String destPath) {
		// TODO Auto-generated method stub
		return null;
	}

	public void remove(String path) {
		// TODO Auto-generated method stub
		
	}

	public void revert(String path) {
		// TODO Auto-generated method stub
		
	}

	public void update(String path, String version) {
		// TODO Auto-generated method stub
		
	}

	public void update(String path) {
		// TODO Auto-generated method stub
		
	}

	public ConfigurationItem[] info(String path) {
		// TODO Auto-generated method stub
		return null;
	}
}