package com.ironiacorp.computer.filesystem;

public interface Filter<T>
{
	boolean accept(T subject);
}
