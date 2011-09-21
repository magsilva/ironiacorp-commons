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

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class FileExecutionTest
{
	private OperationalSystem os;
	
	
	@Before
	public void setUp() throws Exception
	{
		os = new Unix();
	}

	@Test
	public void testFind()
	{
		assertNotNull(os.findExecutable("cal"));
	}

	@Test
	public void testExec() throws Exception
	{
		File file = os.findExecutable("cal");
		ProcessBuilder pb = os.exec(file);
		Process process = pb.start();
		int result = process.waitFor();
		assertEquals(0, result);
		assertEquals(0, process.exitValue());
	}

	
	@Test
	public void testLoadFile_InvalidLibrary()
	{
		assertNull(os.findExecutable("abcdefgxyz"));
	}
	
	@Test
	public void testLoadFile_ValidLibrary_SpecificPath()
	{
		os.addExecutableSearchPath(new File("/usr/lib/R/site-library/rJava/jri"));
		File file = os.findExecutable("run");
		assertNotNull(file);
		os.exec(file, null);
	}

}