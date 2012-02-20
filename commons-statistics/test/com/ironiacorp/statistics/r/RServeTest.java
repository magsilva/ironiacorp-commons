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

package com.ironiacorp.statistics.r;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import com.ironiacorp.computer.ComputerSystem;
import com.ironiacorp.computer.OperationalSystem;

public class RServeTest
{
	private RServe rserve;

	@Before
	public void setUp() throws Exception
	{
		ComputerSystem.reset();
		OperationalSystem os = ComputerSystem.getCurrentOperationalSystem();
		rserve = new RServe();
	}
	
	@Test
	public void testGetRServeExecutable()
	{
		assertNull(rserve.getRServeExecutable());
	}

	@Test
	public void testGetRServeExecutable_WithRHome()
	{
		OperationalSystem os = ComputerSystem.getCurrentOperationalSystem();
		os.addExecutableSearchPath(new File("/usr/lib/R/site-library/Rserve"));
		os.addExecutableSearchPath(new File("/usr/lib64/R/library/Rserve"));
		os.addExecutableSearchPath(new File("/usr/lib/R/site-library/Rserve"));
		os.addExecutableSearchPath(new File("/usr/lib64/R/library/Rserve"));
		assertNotNull(rserve.getRServeExecutable());
	}

	
	@Test
	public void testGetRExecutable()
	{
		assertNotNull(rserve.getRExecutable());
	}

	@Test
	public void testStart_R() throws Exception
	{
		Process process = rserve.start();
		int result = process.waitFor();
		assertEquals(0, result);
	}

	
	@Test
	public void testStart_RServe() throws Exception
	{
		OperationalSystem os = ComputerSystem.getCurrentOperationalSystem();
		os.addExecutableSearchPath(new File("/usr/lib/R/site-library/Rserve"));
		rserve.setHome("/usr/lib/R");
		Process process = rserve.start();
		int result = process.waitFor();
		assertEquals(0, result);
	}

}
