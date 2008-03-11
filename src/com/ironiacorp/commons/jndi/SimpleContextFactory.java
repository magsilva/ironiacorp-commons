/*
Copyright (C) 2002 Carl Trusiak, Sheriff

You are welcome to do whatever you want to with this source file provided
that you maintain this comment fragment (between the dashed lines).
Modify it, change the package name, change the class name ... personal or business
use ...  sell it, share it ... add a copyright for the portions you add ...

My goal in giving this away and maintaining the copyright is to hopefully direct
developers back to JavaRanch.

The original source can be found at <a href="http://www.javaranch.com">JavaRanch</a>.
 */

package com.ironiacorp.commons.jndi;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

/**
 * A very SimpleContextFactory to assist in JNDI lookups of SimpleDataSource.
 */
public class SimpleContextFactory implements InitialContextFactory
{

	private static SimpleContext instance;

	/**
	 * Method getInitialContext Returns the SimpleContext for use.
	 */
	public Context getInitialContext(Hashtable<?, ?> environment) throws NamingException
	{
		if (instance == null) {
			instance = new SimpleContext();
		}
		return instance;
	}
}
