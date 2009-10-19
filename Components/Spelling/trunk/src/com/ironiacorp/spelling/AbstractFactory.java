package com.ironiacorp.spelling;

public abstract class AbstractFactory<T, U> implements Factory<T, U>
{
	public abstract U create(T rawMaterial);
}
