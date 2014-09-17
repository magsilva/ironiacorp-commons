package com.ironiacorp.io;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;

public class IoUtilTest
{
	@Test
	public void testToFile_SingleArgument() throws Exception
	{
		File src = File.createTempFile("IoUtilTest", "");
		File dest = File.createTempFile("IoUtilTest", "");
		try (
				FileInputStream fis = new FileInputStream(src);
				FileOutputStream fos = new FileOutputStream(src);
		) {
			fos.write(1);
			fos.write(2);
			fos.write(3);
			fos.close();
			
			IoUtil.toFile(fis, dest);
			
			assertTrue(dest.exists());
			assertTrue(dest.length() > 0);
		}
		
		try (FileInputStream fis = new FileInputStream(dest)) {
			assertEquals(1, fis.read());
			assertEquals(2, fis.read());
			assertEquals(3, fis.read());
			src.delete();
			dest.delete();
		}
	}
	
	@Test
	public void testToFile() throws Exception
	{
		File src = File.createTempFile("IoUtilTest", "");
		File dest = File.createTempFile("IoUtilTest", "");
		try (
			FileInputStream fis = new FileInputStream(src);
			FileOutputStream fos = new FileOutputStream(src);
		) {
			fos.write(1);
			fos.write(2);
			fos.write(3);
			fos.close();
			File dest2 = IoUtil.toFile(fis, dest);
			assertSame(dest, dest2);
			assertTrue(dest.exists());
			assertTrue(dest.length() > 0);
		}
		
		try (FileInputStream fis = new FileInputStream(dest)) {
			assertEquals(1, fis.read());
			assertEquals(2, fis.read());
			assertEquals(3, fis.read());
			src.delete();
			dest.delete();
		}
	}
	
	@Test
	public void testToArray() throws Exception
	{
		File src = File.createTempFile("IoUtilTest", "");
		FileOutputStream fos = new FileOutputStream(src);
		fos.write(1);
		fos.write(2);
		fos.write(3);
		fos.close();
		
		FileInputStream fis = new FileInputStream(src);
		byte[] result = IoUtil.toByteArray(fis);
		
		assertNotNull(result);
		assertTrue(result.length > 0);
		ByteArrayInputStream bis = new ByteArrayInputStream(result);
		assertEquals(1, bis.read());
		assertEquals(2, bis.read());
		assertEquals(3, bis.read());
		
		src.delete();
	}
	
	@Test
	public void testDumpFile_FromFile() throws Exception
	{
		File tmpFile1 = File.createTempFile("IoUtilTest", "");
		File tmpFile2 = null;
		
		FileInputStream fis = new FileInputStream(tmpFile1);
		FileOutputStream fos = new FileOutputStream(tmpFile1);
		fos.write("Create the diff from the root of the MPLayer source tree: this makes the diff easier do apply.".getBytes());
		fos.flush();
		fos.close();
		
		File dest = File.createTempFile("IoUtilTest", "");
		tmpFile2 = IoUtil.toFile(fis, dest);
		
		assertEquals(tmpFile1.length(), tmpFile2.length());
	}
	
	@Test
	public void testToFile_FromClassLoader() throws Exception
	{
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("MplayerPatchingPolicy.txt");
		File dest = File.createTempFile("IoUtilTest", "");
		File tmpFile = IoUtil.toFile(is, dest);
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(tmpFile)))) {
			assertEquals(
				"Create the diff from the root of the MPLayer source tree: this makes the diff easier do apply.",
				reader.readLine());
		}
	}

}
