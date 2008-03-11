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

import net.sf.ideais.util.conf.HardCodedConfiguration;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class HardCodedConfigurationTest extends ConfigurationTest
{
	private HardCodedConfiguration hconf;

	
	@Before
	public void setUp() throws Exception
	{
		conf = new HardCodedConfiguration();
		hconf = (HardCodedConfiguration)conf;
	}

	@Test
	public void testGetPropertySet()
	{
		hconf.setProperty(tuple1[0], tuple1[1]);
		Object o = conf.getProperty(tuple1[0]);
		assertEquals(o, tuple1[1]);
	}

	@Test
	public void testSetPropertyNotSet()
	{
		Object o = hconf.setProperty(tuple1[0], tuple1[1]);
		assertNull(o);
	}

	@Test
	public void testSetPropertySet()
	{
		Object o = null;	
		hconf.setProperty(tuple1[0], tuple1[1]);
		o = hconf.setProperty(tuple1[0], tuple2[1]);
		
		assertEquals(o, tuple1[1]);
	}
}
