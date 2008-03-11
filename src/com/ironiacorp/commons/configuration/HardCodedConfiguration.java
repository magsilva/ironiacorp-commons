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

package com.ironiacorp.commons.configuration;

import java.util.HashMap;



/**
 * Hard-coded (aka hand crafted) configuration.
 * 
 * This is the simplest Configuration you may use. It works like a Map
 * whose keys are Strings and values Objects.
 */
public class HardCodedConfiguration implements ConfigurationMap
{
	/**
	 * Holds the configuration data.
	 */
	private HashMap<String, Object> properties;
	
	public HardCodedConfiguration()
	{
		properties = new HashMap<String, Object>();
	}	

	/**
	 * Get a configuration value.
	 * 
	 * @param name The key for the configuration item.
	 * @return The configuration value for the given key or NULL if
	 * there's nothing set or the key doesn't exist.  
	 */
	public Object getProperty(String name)
	{
		return properties.get(name);
	}

	/**
	 * Set a configuration value.
	 * 
	 * @param name The key for the configuration item.
	 * @param value The value for the configuration item.
	 * @return The previous value for the configuration item or NULL if
	 * it is a new configuration item.
	 */
	public Object setProperty(String name, Object value)
	{
		return properties.put(name, value);
	}
}
