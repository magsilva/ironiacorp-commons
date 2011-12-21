package com.ironiacorp.scm.git;

import org.eclipse.jgit.api.Git;

import com.ironiacorp.scm.WorkingCopy;

public class GitWorkingCopy extends WorkingCopy
{
	private Git wc;
	
	public GitWorkingCopy(GitRepository repository)
	{
		wc = new Git(repository.getRepository());
	}
}
