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

package com.ironiacorp.computer;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.ironiacorp.computer.OperationalSystemType;
import com.ironiacorp.computer.OperationalSystemDetector;

// TODO: use the list at http://lopica.sourceforge.net/os.html
public class OperationalSystemTypeDetectorTest
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
		assertEquals(OperationalSystemType.Linux, detector.detectCurrentOS());
	}
	
	@Test
	public void testDetectOSfromFilenameWindows()
	{
		assertEquals(OperationalSystemType.Windows, detector.detectOSfromFilename("test\\Test123.txt"));
	}

	@Test
	public void testDetectOSfromFilenameLinux()
	{
		assertEquals(OperationalSystemType.Linux, detector.detectOSfromFilename("test/Test123.txt"));
	}

	
	@Test
	public void testDetectWindows95()
	{
		assertEquals(OperationalSystemType.Windows, detector.detectOSfromOSName("Windows 95"));
	}
	
	@Test
	public void testDetectWindowsNT()
	{
		assertEquals(OperationalSystemType.Windows, detector.detectOSfromOSName("Windows NT"));
	}

	@Test
	public void testDetectWindowsCE()
	{
		assertEquals(OperationalSystemType.Windows, detector.detectOSfromOSName("Windows CE"));
	}

	@Test
	public void testDetectMacOS()
	{
		assertEquals(OperationalSystemType.MacOS, detector.detectOSfromOSName("Mac OS"));
	}

	@Test
	public void testDetectMacOSX()
	{
		assertEquals(OperationalSystemType.MacOS, detector.detectOSfromOSName("Mac OS X"));
	}

	@Test
	public void testDetectDarwin()
	{
		assertEquals(OperationalSystemType.MacOS, detector.detectOSfromOSName("Darwin"));
	}

	
	@Test
	public void testDetectLinux()
	{
		assertEquals(OperationalSystemType.Linux, detector.detectOSfromOSName("Linux"));
	}

	@Test
	public void testDetectSolaris()
	{
		assertEquals(OperationalSystemType.Solaris, detector.detectOSfromOSName("Solaris"));
	}
}

