package com.ironiacorp.introspector.reflection;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TypeParameterMetadata
{
	private String name;
	
	private List<Type> bounds;
	
	private List<Type> arguments;
	
	protected TypeParameterMetadata(String name)
	{
		this.name = name;
		this.bounds = new ArrayList<Type>();
		this.arguments = new ArrayList<Type>();
	}
	
	public String getName()
	{
		return name;
	}
	
	public Type[] getBounds()
	{
		return (Type[]) bounds.toArray(new Type[0]);	
	}
	
	public Type[] getArguments()
	{
		return (Type[]) arguments.toArray(new Type[0]);	
	}
	
	protected void addBound(Type type)
	{
		bounds.add(type);
	}
	
	protected void addArgument(Type type)
	{
		arguments.add(type);
	}
}
