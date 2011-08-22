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

package com.ironiacorp.nativelib;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

public class LibraryLoaderTest
{
	private LibraryLoader loader;
	
	
	@Before
	public void setUp() throws Exception
	{
		loader = new LibraryLoader();
	}

	@Test
	public void testLoadFile_AlreadLoaded()
	{
		loader.addDefaultLibraryPath();
		assertTrue(loader.load("udev"));
	}

	@Test
	public void testLoadFile_InvalidLibrary()
	{
		assertFalse(loader.load("abcdefgxyz"));
	}
	
	@Test
	public void testLoadFile_ValidLibrary_SpecificPath()
	{
		loader.addLibraryPath(new File("/usr/lib/R/site-library/rJava/jri"));
		assertTrue(loader.load("jri"));
	}

}
