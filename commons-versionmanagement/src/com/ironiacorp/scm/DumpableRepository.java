package com.ironiacorp.scm;

import java.io.File;

public interface DumpableRepository
{
	String DUMP_SUFFIX = ".dump";
	
	/**
	 * Initialize a repository (if possible).
	 * 
	 * @throws RepositoryError If an error occurs while creating the repository.
	 */
	void init();

	/**
	 * Dump the repository to a file.
	 * 
	 * @return The file with the dump.
	 * 
	 * @throws RepositoryError If an error occurs while dumping the repository.
	 */
	File dump();

	/**
	 * Load a dump in the repository. Use this with caution!
	 * 
	 * @throws RepositoryError If an error occurs while loading the dump.
	 */
	void load(File dump);
}
