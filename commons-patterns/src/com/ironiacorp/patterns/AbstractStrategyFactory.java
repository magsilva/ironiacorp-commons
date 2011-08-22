package com.ironiacorp.patterns;


public abstract class AbstractStrategyFactory<T, U extends Strategy> implements Factory<T, U>
{
	public abstract U create(T rawMaterial);
}
