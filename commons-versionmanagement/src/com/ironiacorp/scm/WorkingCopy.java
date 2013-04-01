package com.ironiacorp.scm;

import java.io.File;

import org.slf4j.Logger;

public class WorkingCopy
{
	private static Logger log;
	
	private File workDir;

	public File getWorkDir() {
		return workDir;
	}

	public void setWorkDir(File workDir) {
		this.workDir = workDir;
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
		log.debug( "Diff " + srcPath + " (" + version1 + ") -> " + destPath + " (" + version2 + ")" );
		return null;
	}


}
