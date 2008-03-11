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
 
Copyright (C) 2005 Marco Aurelio Graciotto Silva <magsilva@gmail.com>
*/

package com.ironiacorp.commons;

import java.util.Locale;

/**
* Utilities to manipulate Locale objects.
*/
public final class LocaleUtil
{
	/**
	 * We really don't want an instance of this class, so we create this
	 * private constructor.
	 */
	private LocaleUtil()
	{
	}

	/**
	 * Check if the given locale is one recognizable (known) by Java.
	 *  
	 * @param locale The locale to be tested.
	 * 
	 * @return True if the locale is known, false otherwise.
	 */
	public static boolean isValid(Locale locale)
	{
		// No locale is always valid, will use the system's default.
		if (locale == null) {
			return true;
		}
		
		Locale[] availableLocales = Locale.getAvailableLocales();
		boolean found = false;
		for (Locale i : availableLocales) {
			if (i.equals(locale)) {
				found = true;
				break;
			}
		}
		return found;
	}
}
