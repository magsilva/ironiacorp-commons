package com.ironiacorp.spelling;

public interface Factory<T, U>
{
	public abstract U create(T rawMaterial);
}
