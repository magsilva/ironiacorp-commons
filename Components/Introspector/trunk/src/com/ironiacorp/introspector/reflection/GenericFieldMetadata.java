package com.ironiacorp.introspector.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

public class GenericFieldMetadata
{
	private Field field;

	public GenericFieldMetadata(Field field)
	{
		Type type = field.getGenericType();
		if (!(type instanceof ParameterizedType)) {
			throw new IllegalArgumentException("Field is not generic");
		}

		this.field = field;
	}

	public ClassMetadata getType()
	{
		ParameterizedType ptype = (ParameterizedType) field.getGenericType();
		Type rtype = ptype.getRawType();
		return new ClassMetadata(rtype.getClass());
	}

	public List<ClassMetadata> getArguments1()
	{
		List<ClassMetadata> args = new ArrayList<ClassMetadata>();
		ParameterizedType ptype = (ParameterizedType) field.getGenericType();
		for (Type type : ptype.getActualTypeArguments()) {
			args.add(new ClassMetadata(type));
		}

		return args;
	}

	public List<TypeArgumentMetadata> getArguments2()
	{
		List<TypeArgumentMetadata> args = new ArrayList<TypeArgumentMetadata>();
		
		ParameterizedType ptype = (ParameterizedType) field.getGenericType();
		Class rclas = (Class) ptype.getRawType();

		// list the type variables of the base class
		for (TypeVariable tvar : rclas.getTypeParameters()) {
			TypeArgumentMetadata typeArgMetadata = new TypeArgumentMetadata(tvar);
		}

		// list the actual type arguments
		for (Type targ : ptype.getActualTypeArguments()) {
			Class tclas = (Class) targ;
			System.out.print(tclas.getName());
		}
		
		return args;
	}
}
