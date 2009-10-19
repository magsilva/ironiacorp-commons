package com.ironiacorp.license;
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

Copyright (C) 2007 Marco Aurelio Graciotto Silva <magsilva@gmail.com>
*/



import java.util.NoSuchElementException;
import java.util.StringTokenizer;

/**
 * Utilities to compare version numbers or strings.
 */
public final class VersionUtil
{
	/**
	 * We really don't want an instance of this class, so we create this
	 * private constructor.
	 */
	private VersionUtil()
	{
	}
	
	/**
	 * Default version delimiter.
	 */
	public static String VERSION_DELIMITER = ".";

	/**
	 * Compare two version strings. Returns a negative integer, zero, or a positive integer
	 * as first version string is less than, equal to, or greater than the second version
	 * string.
	 * 
	 * The version string must be of numbers separated by dots (e.g., 2.0.0). Each number from
	 * version1 is compared against the one in version2. So:
	 * 
	 *  - 2.0.0 < 2.1.0
	 *  - 2.0.0.1 > 2.0.0
	 * 
	 * @param version1 First version string.
	 * @param version2 Second version string.
	 * 
	 * @return A negative integer, zero, or a positive integer as the first version string
	 * is less than, equal to, or greater than the second version string.
	 */
	public static int compare(String version1, String version2)
	{
		return compare(version1, version2, VERSION_DELIMITER);
	}

	/**
	 * Compare two version strings. Returns a negative integer, zero, or a positive integer
	 * as first version string is less than, equal to, or greater than the second version
	 * string.
	 * 
	 * The version string must be of numbers separated by the delimiter given as parameter
	 * (e.g., 2.0.0). Each number from version1 is compared against the one in version2. So:
	 * 
	 *  - 2.0.0 < 2.1.0
	 *  - 2.0.0.1 > 2.0.0
	 * 
	 * @param version1 First version string.
	 * @param version2 Second version string.
	 * @param delimiter Version's number delimiter.
	 * 
	 * @return A negative integer, zero, or a positive integer as the first version string
	 * is less than, equal to, or greater than the second version string.
	 */
	public static int compare(String version1, String version2, String delimiter)
	{
		StringTokenizer st1 = new StringTokenizer(version1, delimiter);
		StringTokenizer st2 = new StringTokenizer(version2, delimiter);
		
		if (st1.countTokens() == 0) {
			throw new IllegalArgumentException("The version (1) isn't a valid version string");		
		}
		if (st2.countTokens() == 0) {
			throw new IllegalArgumentException("The version (2) isn't a valid version string");		
		}

		while (st1.hasMoreTokens()) {
			int v1 = 0;
			int v2 = 0;
			
			try {
				v1 = Integer.parseInt(st1.nextToken());
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException("The version (1) isn't a valid version string");
			}
						
			try {
				v2 = Integer.parseInt(st2.nextToken());
			} catch (NoSuchElementException nsee) {
				return 1;
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException("The version (2) isn't a valid version string");
			}
			
			if (v1 < v2) {
				return -1;
			}
			if (v1 > v2) {
				return 1;
			}
		}
		
		if (st2.hasMoreElements()) {
			try {
				Integer.parseInt(st2.nextToken());
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException("The version (2) isn't a valid version string");
			}
			return -1;
		}
		
		return 0;
	}
	
}