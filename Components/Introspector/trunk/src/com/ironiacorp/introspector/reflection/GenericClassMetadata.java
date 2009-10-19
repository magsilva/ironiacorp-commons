package com.ironiacorp.introspector.reflection;

/**
 * Despite this identical bytecode, the type parameter information is recorded in the class format
 * using a new signature attribute. The JVM records this signature information when loading a class
 * and makes it available at run time using reflection
 */
public class GenericClassMetadata // extends ClassMetadata
{
	private Class<?> clazz;
	
	
	
}
