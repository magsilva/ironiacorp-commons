package com.ironiacorp.math;

import java.lang.Math;

public class Exponential {

	public double exp(int number, int precision)
	{
		Factorial fact = new Factorial();
		double result = 0;

		for (int i = 0; i < precision; i++) {
			result += Math.pow(number, i) / fact.factorial(i);
		}

		return result;
	}

}
