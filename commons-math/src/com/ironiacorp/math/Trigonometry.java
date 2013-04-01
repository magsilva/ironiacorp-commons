package com.ironiacorp.math;

public class Trigonometry
{

	private Factorial fact = new Factorial();

	/**
	 * Calculate the sine of a number.
	 *
	 * @param number Number (in radian)
	 * @param precision Precision to calculate this number.
	 * @return The sine of the number.
	 */
	public double sin(double number, int precision)
	{
		double result = 0;
		for (int i = 0; i < precision; i += 2) {
			result += Math.pow(-1, i) * (Math.pow(number, (2 * i) + 1) / fact.factorial((2 * i) + 1));
		}

		return result;
	}

	/**
	 * Calculate the cosine of a number.
	 *
	 * @param number Number (in radian)
	 * @param precision Precision to calculate this number.
	 * @return The cosine of the number.
	 */
	public double cos(double number, int precision)
	{
		Factorial fact = new Factorial();
		double result = 0;
		for (int i = 0; i < precision; i++) {
			result += Math.pow(-1, i) * (Math.pow(number, 2 * i) / fact.factorial(2 * i));
		}

		return result;
	}
}
