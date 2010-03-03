package com.ironiacorp.introspector.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

/**
 * http://www.jroller.com/alessiopace/entry/java_generics_reflection
 * 
 * 
 */
public class FieldMetadata
{
	private Field field;

	public FieldMetadata(final Field field)
	{
		this.field = field;
	}

	public boolean isGeneric()
	{
		Type gtype = field.getGenericType();
		if (gtype instanceof ParameterizedType) {
			return true;
		} else {
			return false;
		}
	}

	public Type getType()
	{
		// get the basic information
		ParameterizedType ptype = (ParameterizedType) field.getGenericType();
		Class rclas = (Class) ptype.getRawType();
		return rclas;
	}

	public TypeParameterMetadata[] getTypeParameters()
	{
		TypeParameterMetadata[] typeParameters;

		ParameterizedType ptype = (ParameterizedType) field.getGenericType();
		Type[] targs = ptype.getActualTypeArguments();
		Class rclas = (Class) ptype.getRawType();
		TypeVariable[] parameters = rclas.getTypeParameters();

		typeParameters = new TypeParameterMetadata[parameters.length];

		for (int i = 0; i < parameters.length; i++) {
			TypeVariable t = parameters[i];
			String name = t.getName();
			typeParameters[i] = new TypeParameterMetadata(name);
			
			for (Type bound : t.getBounds()) {
				typeParameters[i].addBound(bound);
			}
			
			if (targs[i] instanceof Class) {
				
			} else {
				typeParameters[i].addArgument(targs[i]);
			}
		}
		
		return typeParameters;
	}
}
