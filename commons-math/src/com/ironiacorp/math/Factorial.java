package com.ironiacorp.math;

import java.util.ArrayList;

public class Factorial
{
	private ArrayList<Long> cache;

	public synchronized long factorial(int n)
	{
		if (n < 0) {
			throw new IllegalArgumentException("There is no factorial of a negative number");
		}

		long result;

		if (cache == null) {
			cache = new ArrayList<Long>(n);
			cache.add(1L);
		}

		if (n < cache.size()) {
			result = cache.get(n);
		} else {
			int i = cache.size() - 1;
			result = cache.get(i);
			for (int j = (i + 1); j <= n; j++) {
				result = j * cache.get(j - 1);
				cache.add(result);
			}
		}

		return result;
	}


}
