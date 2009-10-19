package com.ironiacorp.introspector.reflection;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class TypeArgumentMetadata
{
	private String name;
	
	private Class[] bounds;
	
	public TypeArgumentMetadata(TypeVariable tvar)
	{
		this.name = tvar.getName();
		
		Type[] btypes = tvar.getBounds();
		bounds = new Class[btypes.length];
		for (Type type : btypes) {
			// bounds
		}
	}
}
