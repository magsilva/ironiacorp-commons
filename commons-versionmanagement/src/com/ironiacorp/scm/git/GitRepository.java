package com.ironiacorp.scm.git;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepository;

import com.ironiacorp.credentials.Credential;
import com.ironiacorp.scm.AbstractRepository;
import com.ironiacorp.scm.WorkingCopy;

public class GitRepository extends AbstractRepository
{
	private Repository repository;
	
	protected Repository getRepository()
	{
		return repository;
	}
	
	@Override
	public WorkingCopy checkout(File workDir)
	{
		GitWorkingCopy wc;
		
		try {
			repository = new FileRepository(workDir);
			wc = new GitWorkingCopy(this);
		} catch (IOException ioe) {
			throw new IllegalArgumentException("Could not clone the repository", ioe);
		}
		
		return wc;
	}

	@Override
	public void init()
	{
		File repoDir = new File(getLocation());
		try {
			if (repoDir.createNewFile()) {
				repoDir.delete();
			} else {
				throw new IllegalArgumentException("Invalid location for the creation of a repository");
			}
		} catch (IOException e) {
			if (repoDir.exists()) {
				throw new IllegalArgumentException("Invalid location for the creation of a repository: directory already exists");
			} else {
				throw new IllegalArgumentException("Invalid location for the creation of a repository");
			}
		}
		
		try {
			repository = new FileRepository(getLocation());
			repository.create(true);
		} catch (IOException ioe) {
			throw new IllegalArgumentException("Could not clone the repository", ioe);
		}
	}


	@Override
	public String getShortName() {
		// TODO Auto-generated method stub
		return null;
	}
}
