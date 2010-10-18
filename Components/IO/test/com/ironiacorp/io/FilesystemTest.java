package com.ironiacorp.io;

import static org.junit.Assert.*;

import java.io.File;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

public class FilesystemTest
{
	private Filesystem filesystem;
	
	@Before
	public void setUp() throws Exception
	{
		filesystem = new Filesystem();
	}

	@Test
	public void testFindFile()
	{
		List<File> files = filesystem.find(new File("/tmp"));
		assertNotNull(files);
	}

	@Test
	public void testFindFileIntPattern()
	{
		List<File> files = filesystem.find(new File("/tmp"), Pattern.compile("\\.pdf$"));
		assertNotNull(files);
	}
}
