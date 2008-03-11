/*
This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA

Copyright (C) 2007 Marco Aurélio Graciotto Silva <magsilva@gmail.com>
*/

package com.ironiacorp.commons;

import java.util.ArrayList;
import java.util.HashSet;

public final class Statistics
{
	/**
	 * Remove duplicated entries from an array.
	 */
	public static Object[] unique(Object[] objects)
	{
		HashSet<Object> set = new HashSet<Object>(objects.length);
		for (Object o1 : objects) {
			if (! set.contains(o1)) {
				set.add(o1);
			}
		}
		return set.toArray();
	}
	
	/**
	 * Create a product of objects.
	 */
	public static ArrayList<Object[]> product(Object[] objects)
	{
		objects = Statistics.unique(objects);
		ArrayList<Object[]> permutation = new ArrayList<Object[]>(objects.length * objects.length);
		for (Object o1 : objects) {
			Object[] tuple = new Object[2];
			permutation.add(tuple);
			tuple[1] = o1;
			for (Object o2 : objects) {
				tuple[2] = o2;
			}
		}
		return permutation;
	}
}
