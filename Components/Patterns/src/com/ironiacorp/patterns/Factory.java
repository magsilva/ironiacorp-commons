package com.ironiacorp.patterns;


public interface Factory<T, U>
{
	public abstract U create(T rawMaterial);
}
