/*
Copyright (C) 2008 Marco Aurélio Graciotto Silva <magsilva@gmail.com>

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.ironiacorp.commons.jndi;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.StringTokenizer;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import com.ironiacorp.commons.IoUtil;

public class FilesystemContext implements Context
{
	private Context ctx;
	
	private File baseDir;

	private final String cutName(String name)
	{
		StringTokenizer st = new StringTokenizer(name, "/");
		StringBuffer sb = new StringBuffer();
		int i = st.countTokens() - 1;
		for (int j = 0; j < i; j++) {
			sb.append(st.nextToken());
		}
		return sb.toString();
	}
	
	public final void prepareDir(String name)
	{
		String dir = cutName(name);
		String path = baseDir.getAbsolutePath() + "/" + dir;
		File jndiDir = new File(path);
		jndiDir.mkdirs();
	}
	
	public FilesystemContext(Hashtable<?, ?> environment) throws NamingException
	{
		try {
			baseDir = IoUtil.createTempDir();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		// Create initial context
		Hashtable<String, String> env = new Hashtable<String, String>();
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.fscontext.RefFSContextFactory");
		env.put(Context.PROVIDER_URL, "file://" + baseDir.getAbsolutePath());
		ctx = new InitialContext(env);
	}
	
	public Object addToEnvironment(String propName, Object propVal) throws NamingException
	{
		return ctx.addToEnvironment(propName, propVal);
	}

	public void bind(Name name, Object obj) throws NamingException
	{
		// ctx.bind(name, obj);
		throw new UnsupportedOperationException();
	}

	public void bind(String name, Object obj) throws NamingException
	{
		prepareDir(name);
		ctx.bind(name, obj);
	}

	public void close() throws NamingException
	{
		ctx.close();
	}

	public Name composeName(Name name, Name prefix) throws NamingException
	{
		return ctx.composeName(name, prefix);
	}

	public String composeName(String name, String prefix) throws NamingException
	{
		return ctx.composeName(name, prefix);
	}

	public Context createSubcontext(Name name) throws NamingException
	{
		return ctx.createSubcontext(name);
	}

	public Context createSubcontext(String name) throws NamingException
	{
		return ctx.createSubcontext(name);
	}

	public void destroySubcontext(Name name) throws NamingException
	{
		ctx.destroySubcontext(name);
	}

	public void destroySubcontext(String name) throws NamingException
	{
		ctx.destroySubcontext(name);
	}

	public Hashtable<?, ?> getEnvironment() throws NamingException
	{
		return ctx.getEnvironment();
	}

	public String getNameInNamespace() throws NamingException
	{
		return ctx.getNameInNamespace();
	}

	public NameParser getNameParser(Name name) throws NamingException
	{
		return ctx.getNameParser(name);
	}

	public NameParser getNameParser(String name) throws NamingException
	{
		return ctx.getNameParser(name);
	}

	public NamingEnumeration<NameClassPair> list(Name name) throws NamingException
	{
		return ctx.list(name);
	}

	public NamingEnumeration<NameClassPair> list(String name) throws NamingException
	{
		return ctx.list(name);
	}

	public NamingEnumeration<Binding> listBindings(Name name) throws NamingException
	{
		return ctx.listBindings(name);
	}

	public NamingEnumeration<Binding> listBindings(String name) throws NamingException
	{
		return ctx.listBindings(name);
	}

	public Object lookup(Name name) throws NamingException
	{
		return ctx.lookup(name);
	}

	public Object lookup(String name) throws NamingException
	{
		return ctx.lookup(name);
	}

	public Object lookupLink(Name name) throws NamingException
	{
		return ctx.lookupLink(name);
	}

	public Object lookupLink(String name) throws NamingException
	{
		return ctx.lookupLink(name);
	}

	public void rebind(Name name, Object obj) throws NamingException
	{
		throw new UnsupportedOperationException();
		//ctx.rebind(name, obj);
	}

	public void rebind(String name, Object obj) throws NamingException
	{
		prepareDir(name);
		ctx.rebind(name, obj);
	}

	public Object removeFromEnvironment(String propName) throws NamingException
	{
		return ctx.removeFromEnvironment(propName);
	}

	public void rename(Name oldName, Name newName) throws NamingException
	{
		ctx.rename(oldName, newName);
	}

	public void rename(String oldName, String newName) throws NamingException
	{
		ctx.rename(oldName, newName);
	}

	public void unbind(Name name) throws NamingException
	{
		ctx.unbind(name);
	}

	public void unbind(String name) throws NamingException
	{
		ctx.unbind(name);
	}
}
