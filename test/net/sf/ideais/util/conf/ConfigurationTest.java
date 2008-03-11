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

package tests.net.sf.ideais.util.conf;

import net.sf.ideais.util.conf.ConfigurationMap;

import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 * Generic test cases for concrete Configuration implementations.
 */
public abstract class ConfigurationTest
{
	protected ConfigurationMap conf;

	// key, value
	protected final String[] tuple1 = {"test", "123"};
	protected final String[] tuple2 = {"Damn", "Rain"};

	@Test
	public void testGetPropertyNotSet()
	{
		Object o = conf.getProperty(tuple1[0]);
		assertNull(o);
	}

}