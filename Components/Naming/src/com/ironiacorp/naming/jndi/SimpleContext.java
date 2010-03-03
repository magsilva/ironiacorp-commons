/*
You are welcome to do whatever you want to with this source file provided
that you maintain this comment fragment (between the dashed lines).
Modify it, change the package name, change the class name ... personal or business
use ...  sell it, share it ... add a copyright for the portions you add ...

My goal in giving this away and maintaining the copyright is to hopefully direct
developers back to JavaRanch.

The original source can be found at <a href=http://www.javaranch.com>JavaRanch</a>.

Copyright (C) 2002 Carl Trusiak, Sheriff
 */

package com.ironiacorp.naming.jndi;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameAlreadyBoundException;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.NameParser;
import java.util.Hashtable;

/**
 * A very thin Context for use by JNDIUnitTestHelper.
 */
public class SimpleContext implements Context
{
	private Hashtable<String, Object> table = new Hashtable<String, Object>();

	public SimpleContext()
	{
	}

	public SimpleContext(Hashtable<?, ?> environment)
	{
		this();
	}
	
	public Object lookup(Name name) throws NamingException
	{
		throw new UnsupportedOperationException("Method lookup() not yet implemented.");
	}

	/**
	 * Method lookup Returns the SimpleDataSource.
	 * 
	 * @param name
	 * @return A copy of the SimpleDataSource class
	 */
	public Object lookup(String name) throws NamingException
	{
		return table.get(name);
	}

	/**
	 * Method bind not yet implemented.
	 * 
	 * @param name
	 * @param obj
	 */
	public void bind(Name name, Object obj) throws NamingException
	{
		throw new UnsupportedOperationException("Method bind() not yet implemented.");
	}

	/**
	 * Method bind the SimpleDataSource for use.
	 * 
	 * @param name
	 * @param obj
	 */
	public void bind(String name, Object obj) throws NamingException
	{
		if (table.containsKey(name)) {
			throw new NameAlreadyBoundException();
		}

		table.put(name, obj);
	}

	/**
	 * Method rebind not yet implemented.
	 * 
	 * @param name
	 * @param obj
	 */
	public void rebind(Name name, Object obj) throws NamingException
	{
		throw new UnsupportedOperationException("Method rebind() not yet implemented.");
	}

	public void rebind(String name, Object obj) throws NamingException
	{
		table.put(name, obj);
	}

	public void unbind(Name name) throws NamingException
	{
		throw new UnsupportedOperationException("Method unbind() not yet implemented.");
	}

	public void unbind(String name) throws NamingException
	{
		table.remove(name);
	}

	public void rename(Name oldName, Name newName) throws NamingException
	{
		throw new UnsupportedOperationException("Method rename() not yet implemented.");
	}

	public void rename(String oldName, String newName) throws NamingException
	{
		Object o = table.remove(oldName);
		table.put(newName, o);
	}

	public NamingEnumeration<NameClassPair> list(Name name) throws NamingException
	{
		throw new UnsupportedOperationException("Method list() not yet implemented.");
	}

	public NamingEnumeration<NameClassPair> list(String name) throws NamingException
	{
		throw new UnsupportedOperationException("Method list() not yet implemented.");
	}

	public NamingEnumeration<Binding> listBindings(Name name) throws NamingException
	{
		throw new UnsupportedOperationException("Method listBindings() not yet implemented.");
	}

	public NamingEnumeration<Binding> listBindings(String name) throws NamingException
	{
		throw new UnsupportedOperationException("Method listBindings() not yet implemented.");
	}

	public void destroySubcontext(Name name) throws NamingException
	{
		throw new UnsupportedOperationException("Method destroySubcontext() not yet implemented.");
	}

	public void destroySubcontext(String name) throws NamingException
	{
		throw new UnsupportedOperationException("Method destroySubcontext() not yet implemented.");
	}

	public Context createSubcontext(Name name) throws NamingException
	{
		throw new UnsupportedOperationException("Method createSubcontext() not yet implemented.");
	}

	public Context createSubcontext(String name) throws NamingException
	{
		throw new UnsupportedOperationException("Method createSubcontext() not yet implemented.");
	}

	public Object lookupLink(Name name) throws NamingException
	{
		throw new UnsupportedOperationException("Method lookupLink() not yet implemented.");
	}

	public Object lookupLink(String name) throws NamingException
	{
		throw new UnsupportedOperationException("Method lookupLink() not yet implemented.");
	}

	public NameParser getNameParser(Name name) throws NamingException
	{
		throw new UnsupportedOperationException("Method getNameParser() not yet implemented.");
	}

	public NameParser getNameParser(String name) throws NamingException
	{
		throw new UnsupportedOperationException("Method getNameParser() not yet implemented.");
	}

	public Name composeName(Name name, Name prefix) throws NamingException
	{
		throw new UnsupportedOperationException("Method composeName() not yet implemented.");
	}

	public String composeName(String name, String prefix) throws NamingException
	{
		throw new UnsupportedOperationException("Method composeName() not yet implemented.");
	}

	public Object addToEnvironment(String propName, Object propVal) throws NamingException
	{
		throw new UnsupportedOperationException("Method addToEnvironment() not yet implemented.");
	}

	public Object removeFromEnvironment(String propName) throws NamingException
	{
		throw new UnsupportedOperationException("Method removeFromEnvironment() not yet implemented.");
	}

	public Hashtable<?, ?> getEnvironment() throws NamingException
	{
		throw new UnsupportedOperationException("Method getEnvironment() not yet implemented.");
	}

	public void close() throws NamingException
	{
		throw new UnsupportedOperationException("Method close() not yet implemented.");
	}

	public String getNameInNamespace() throws NamingException
	{
		throw new UnsupportedOperationException("Method getNameInNamespace() not yet implemented.");
	}
}
