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

public class LibraryLoaderTest
{
	private OperationalSystem os;
	
	
	@Before
	public void setUp() throws Exception
	{
		os = new Unix();
	}

	@Test
	public void testLoadFile_AlreadyLoaded()
	{
		assertNotNull(os.findLibrary("udev"));
	}

	@Test
	public void testLoadFile_InvalidLibrary()
	{
		assertNull(os.findLibrary("abcdefgxyz"));
	}
	
	@Test
	public void testLoadFile_ValidLibrary_SpecificPath()
	{
		os.addLibrarySearchPath(new File(getClass().getProtectionDomain().getCodeSource().getLocation().getFile() + "com/ironiacorp/computer/loader"));
		File library = os.findLibrary("shellsort");
		assertNotNull(library);
		os.loadLibrary(library);
	}

}
