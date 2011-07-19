/*
 * Copyright (C) 2011 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ironiacorp.systeminfo;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

// TODO: use the list at http://lopica.sourceforge.net/os.html
public class OperationalSystemDetectorTest
{
	private OperationalSystemDetector detector;

	@Before
	public void setUp() throws Exception
	{
		detector = new OperationalSystemDetector();
	}

	@Test
	public void testDetectCurrentOS()
	{
		assertEquals(OperationalSystem.Linux, detector.detectCurrentOS());
	}
	
	@Test
	public void testDetectWindows95()
	{
		assertEquals(OperationalSystem.Windows, detector.detectOS("Windows 95"));
	}
	
	@Test
	public void testDetectWindowsNT()
	{
		assertEquals(OperationalSystem.Windows, detector.detectOS("Windows NT"));
	}

	@Test
	public void testDetectWindowsCE()
	{
		assertEquals(OperationalSystem.Windows, detector.detectOS("Windows CE"));
	}

	@Test
	public void testDetectMacOS()
	{
		assertEquals(OperationalSystem.MacOS, detector.detectOS("Mac OS"));
	}

	@Test
	public void testDetectMacOSX()
	{
		assertEquals(OperationalSystem.MacOS, detector.detectOS("Mac OS X"));
	}

	@Test
	public void testDetectDarwin()
	{
		assertEquals(OperationalSystem.MacOS, detector.detectOS("Darwin"));
	}

	
	@Test
	public void testDetectLinux()
	{
		assertEquals(OperationalSystem.Linux, detector.detectOS("Linux"));
	}

	@Test
	public void testDetectSolaris()
	{
		assertEquals(OperationalSystem.Solaris, detector.detectOS("Solaris"));
	}
}

