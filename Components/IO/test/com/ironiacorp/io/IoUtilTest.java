package com.ironiacorp.io;

import static org.junit.Assert.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

public class IoUtilTest
{
	private static String MIN_VALID_PREFIX = "   ";

	private static String MIN_VALID_SUFFIX = "   ";

	@Test
	public void testCreateTempDir() throws IOException
	{
		File dir = IoUtil.createTempDir();
		assertNotNull(dir);
		assertTrue(dir.isDirectory());
		dir.delete();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempDirPrefix_PrefixEmpty()
	{
		IoUtil.createTempDir("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempDirPrefix_PrefixNull()
	{
		IoUtil.createTempDir(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempDirPrefix_PrefixShort1()
	{
		IoUtil.createTempDir(" ");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempDirPrefix_PrefixShort2()
	{
		IoUtil.createTempDir("  ");
	}

	@Test
	public void testCreateTempDirPrefix_PrefixShort3()
	{
		File dir = IoUtil.createTempDir(IoUtilTest.MIN_VALID_PREFIX);
		assertNotNull(dir);
		assertTrue(dir.isDirectory());
		dir.delete();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempDirPrefixSuffix_PrefixEmptySuffixEmpty()
	{
		IoUtil.createTempDir("", "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempDirPrefixSuffix_PrefixEmptySuffixNotEmpty()
	{
		IoUtil.createTempDir("", IoUtilTest.MIN_VALID_SUFFIX);
	}

	@Test
	public void testCreateTempDirPrefixSuffix_PrefixNotEmptySuffixEmpty()
	{
		File dir = IoUtil.createTempDir(IoUtilTest.MIN_VALID_PREFIX, "");
		assertNotNull(dir);
		assertTrue(dir.isDirectory());
		dir.delete();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempDirPrefixSuffix_PrefixNullSuffixNull()
	{
		IoUtil.createTempDir(null, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempDirPrefixSuffix_PrefixNullSuffixNotNull()
	{
		IoUtil.createTempDir(null, IoUtilTest.MIN_VALID_SUFFIX);
	}

	@Test
	public void testCreateTempDirPrefixSuffix_PrefixNotNullSuffixNull()
	{
		File dir = IoUtil.createTempDir(IoUtilTest.MIN_VALID_PREFIX, null);
		assertNotNull(dir);
		assertTrue(dir.isDirectory());
		dir.delete();
	}

	@Test
	public void testCreateTempDirPrefixSuffix()
	{
		File dir = IoUtil.createTempDir(IoUtilTest.MIN_VALID_PREFIX, IoUtilTest.MIN_VALID_SUFFIX);
		assertNotNull(dir);
		assertTrue(dir.isDirectory());
		dir.delete();
	}

	@Test
	public void testCreateTempDirPrefixSuffixBasedir()
	{
		File tmpDir = IoUtil
						.createTempDir(IoUtilTest.MIN_VALID_PREFIX, IoUtilTest.MIN_VALID_SUFFIX);
		File dir = IoUtil.createTempDir(IoUtilTest.MIN_VALID_PREFIX, IoUtilTest.MIN_VALID_SUFFIX,
						tmpDir.getAbsolutePath());
		assertNotNull(dir);
		assertTrue(dir.isDirectory());
		dir.delete();
		tmpDir.delete();
	}

	@Test
	public void testCreateTempDirPrefixSuffixBasedir_NullBasedir()
	{
		File dir = IoUtil.createTempDir(IoUtilTest.MIN_VALID_PREFIX, IoUtilTest.MIN_VALID_SUFFIX,
						null);
		assertNotNull(dir);
		assertTrue(dir.isDirectory());
		dir.delete();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempDirPrefixSuffixBasedir_InvalidBasedir()
	{
		File tmpDir = null;
		File dir = null;
		
		try {
			tmpDir = IoUtil.createTempDir(IoUtilTest.MIN_VALID_PREFIX, IoUtilTest.MIN_VALID_SUFFIX);
			dir = IoUtil.createTempDir(IoUtilTest.MIN_VALID_PREFIX, IoUtilTest.MIN_VALID_SUFFIX, tmpDir.getAbsolutePath() + "abc");
		} catch (IllegalArgumentException iae) {
			if (dir != null) {
				dir.delete();
			}
			if (tmpDir != null) {
				tmpDir.delete();
			}
			throw iae;
		}
	}

	@Test
	public void testCreateTempFile()
	{
		File file = IoUtil.createTempFile();
		assertNotNull(file);
		assertTrue(file.isFile());
		file.delete();
	}

	@Test
	public void testCreateTempFilePrefix()
	{
		File file = IoUtil.createTempFile(IoUtilTest.MIN_VALID_PREFIX);
		assertNotNull(file);
		assertTrue(file.isFile());
		file.delete();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempFilePrefix_Null()
	{
		IoUtil.createTempFile("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempFilePrefix_Empty()
	{
		IoUtil.createTempFile("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempFilePrefix_Short()
	{
		IoUtil.createTempFile(" ");
	}

	@Test
	public void testCreateTempFilePrefixSuffix()
	{
		File file = IoUtil.createTempFile(IoUtilTest.MIN_VALID_PREFIX, IoUtilTest.MIN_VALID_SUFFIX);
		assertNotNull(file);
		assertTrue(file.isFile());
		file.delete();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempFilePrefixSuffix_NullPrefixNullSuffix()
	{
		IoUtil.createTempFile(null, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempFilePrefixSuffix_NullPrefixNotNullSuffix()
	{
		IoUtil.createTempFile(null, IoUtilTest.MIN_VALID_SUFFIX);
	}

	@Test
	public void testCreateTempFilePrefixSuffix_NotNullPrefixNullSuffix()
	{
		File file = IoUtil.createTempFile(IoUtilTest.MIN_VALID_PREFIX, null);
		assertNotNull(file);
		assertTrue(file.isFile());
		file.delete();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempFilePrefixSuffix_EmptyPrefixNotEmptySuffix()
	{
		IoUtil.createTempFile("", IoUtilTest.MIN_VALID_SUFFIX);
	}

	@Test
	public void testCreateTempFilePrefixSuffix_NotEmptyPrefixEmptySuffix()
	{
		File file = IoUtil.createTempFile(IoUtilTest.MIN_VALID_PREFIX, "");
		assertNotNull(file);
		assertTrue(file.isFile());
		file.delete();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempFilePrefixSuffix_EmptyPrefixEmptySuffix()
	{
		IoUtil.createTempFile("", "");
	}

	@Test
	public void testCreateTempFilePrefixSuffixBasedir()
	{
		File tmpDir = IoUtil
						.createTempDir(IoUtilTest.MIN_VALID_PREFIX, IoUtilTest.MIN_VALID_SUFFIX);
		File file = IoUtil.createTempFile(IoUtilTest.MIN_VALID_PREFIX, IoUtilTest.MIN_VALID_SUFFIX,
						tmpDir.getAbsolutePath());
		assertNotNull(file);
		assertTrue(file.isFile());
		file.delete();
		tmpDir.delete();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempFilePrefixSuffixBasedir_InvalidBasedir()
	{
		File tmpDir = IoUtil.createTempFile(IoUtilTest.MIN_VALID_PREFIX,
						IoUtilTest.MIN_VALID_SUFFIX);
		try {
			IoUtil.createTempDir(IoUtilTest.MIN_VALID_PREFIX, IoUtilTest.MIN_VALID_SUFFIX,
						tmpDir.getAbsolutePath() + "aaa");
		} catch (IllegalArgumentException e) {
			tmpDir.delete();
			throw e;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempFilePrefixSuffixBasedir_NullBasedir()
	{
		IoUtil.createTempFile(IoUtilTest.MIN_VALID_PREFIX, IoUtilTest.MIN_VALID_SUFFIX, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempFilePrefixSuffixBasedir_EmptyBasedir()
	{
		IoUtil.createTempFile(IoUtilTest.MIN_VALID_PREFIX, IoUtilTest.MIN_VALID_SUFFIX, "");
	}

	@Test
	public void testGetExtension_Valid_Single()
	{
		String extension = IoUtil.getExtension("test.txt");
		assertEquals("txt", extension);
	}

	@Test
	public void testGetExtension_Valid_Multiple()
	{
		String extension = IoUtil.getExtension("test.txt.txt");
		assertEquals("txt", extension);
	}

	@Test
	public void testGetExtension_Valid_Empty()
	{
		String extension = IoUtil.getExtension("test.");
		assertEquals("", extension);
	}

	@Test
	public void testGetExtension_Valid_Empty_Multiple()
	{
		String extension = IoUtil.getExtension("test..");
		assertEquals("", extension);
	}

	@Test
	public void testGetExtension_Valid_Empty_Inexistent()
	{
		String extension = IoUtil.getExtension("test");
		assertEquals("", extension);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGetExtension_Invalid()
	{
		IoUtil.getExtension(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateDir_Invalid_Null()
	{
		IoUtil.createDir((String) null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateDir_Invalid_Empty()
	{
		IoUtil.createDir("");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCreateDir_Invalid_NullFile()
	{
		IoUtil.createDir((File) null);
	}
	
	@Test
	public void testCreateDir_Valid()
	{
		File dir = IoUtil.createDir("/tmp/1");
		assertTrue(dir.isDirectory());
		dir.delete();
	}

	@Test
	public void testCreateDir_Valid_AlreadyExists()
	{
		File dirBefore = IoUtil.createDir("/tmp/1");
		File dirAfter = IoUtil.createDir("/tmp/1");
		assertTrue(dirBefore.isDirectory());
		assertTrue(dirAfter.isDirectory());
		dirBefore.delete();
		dirAfter.delete();
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testCreateDir_Invalid_IsFile()
	{
		IoUtil.createDir("/proc/uptime");
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testCreateDir_Invalid_HasNoPermission()
	{
		IoUtil.createDir("/etc/abc");
	}
	
	@Test
	public void testCreateFileInDir_Valid() throws Exception
	{
		File file = IoUtil.createFile("/tmp", "abc");
		assertNotNull(file);
		assertTrue(file.exists());
		assertEquals("/tmp/abc", file.getAbsolutePath());
		file.delete();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateFileInDir_InvalidDir() throws Exception
	{
		IoUtil.createFile((String) null, "abc");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCreateFileInDir_InvalidDir_Null() throws Exception
	{
		IoUtil.createFile((File) null, "abc");
	}

	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateFileInDir_InvalidFile() throws Exception
	{
		IoUtil.createFile("/tmp", null);
	}
	
	@Test
	public void testMoveFile_StringString_Valid() throws Exception
	{
		File in = IoUtil.createTempFile();
		File out = IoUtil.createTempFile("abc");
		out.delete();
		IoUtil.moveFile(in.getAbsolutePath(), out.getAbsolutePath());
		assertFalse(in.exists());
		assertTrue(out.exists());
		out.delete();
	}

	@Test(expected=IllegalArgumentException.class)
	public void testMoveFile_StringString_Invalid_NullNull() throws Exception
	{
		IoUtil.moveFile((String) null, (String) null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testMoveFile_StringString_Invalid_Empty() throws Exception
	{
		IoUtil.moveFile("", "");
	}

	
	@Test
	public void testMoveFile_FileFile_Valid() throws Exception
	{
		File in = IoUtil.createTempFile();
		File out = IoUtil.createTempFile("abc");
		out.delete();
		IoUtil.moveFile(in, out);
		assertFalse(in.exists());
		assertTrue(out.exists());
		out.delete();
	}

	@Test(expected=IllegalArgumentException.class)
	public void testMoveFile_FileFile_Invalid_NullNull() throws Exception
	{
		IoUtil.moveFile((File) null, (File) null);
	}

	@Test
	public void testMoveFile_FileFile_Valid_TargetExists() throws Exception
	{
		File in = IoUtil.createTempFile();
		File out = IoUtil.createTempFile(); 
		out.createNewFile();
		IoUtil.moveFile(in, out);
		assertFalse(in.exists());
		assertTrue(out.exists());
		out.delete();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testMoveFile_FileFile_Invalid_Source() throws Exception
	{
		File in = new File("/tmp/sfldkjlçasjl");
		File out = IoUtil.createTempFile();
		try {
			IoUtil.moveFile(in, out);
		} catch (IllegalArgumentException iae) {
			out.delete();
			throw iae;
		}
	}


	@Test(expected=IllegalArgumentException.class)
	public void testMoveFile_FileFile_Invalid_SourceDestinationAreSame() throws Exception
	{
		File in = IoUtil.createTempFile();
		File out = in;
		try {
			IoUtil.moveFile(in, out);
		} catch (IllegalArgumentException iae) {
			in.delete();
			throw iae;
		}
	}
	
	@Test
	public void testSyncFile_Valid() throws Exception
	{
		File file = IoUtil.createTempFile();
		IoUtil.syncFile(new FileOutputStream(file));
		file.delete();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSyncFile_Invalid_Null() throws Exception
	{
		IoUtil.syncFile(null);
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testSyncFile_Invalid_ClosedStream() throws Exception
	{
		File file = IoUtil.createTempFile();
		FileOutputStream fos = new FileOutputStream(file);
		fos.close();
		IoUtil.syncFile(fos);
		file.delete();
	}
	
	@Test
	public void testToFile_SingleArgument() throws Exception
	{
		File src = IoUtil.createTempFile();
		FileOutputStream fos = new FileOutputStream(src);
		fos.write(1);
		fos.write(2);
		fos.write(3);
		fos.close();
		
		FileInputStream fis = new FileInputStream(src);
		File dest = IoUtil.toFile(fis);
		
		assertTrue(dest.exists());
		assertTrue(dest.length() > 0);
		fis = new FileInputStream(dest);
		assertEquals(1, fis.read());
		assertEquals(2, fis.read());
		assertEquals(3, fis.read());
		
		src.delete();
		dest.delete();
	}
	
	@Test
	public void testToFile() throws Exception
	{
		File src = IoUtil.createTempFile();
		FileOutputStream fos = new FileOutputStream(src);
		fos.write(1);
		fos.write(2);
		fos.write(3);
		fos.close();
		
		FileInputStream fis = new FileInputStream(src);
		File dest = IoUtil.createTempFile();
		File dest2 = IoUtil.toFile(fis, dest);
		
		assertSame(dest, dest2);
		assertTrue(dest.exists());
		assertTrue(dest.length() > 0);
		fis = new FileInputStream(dest);
		assertEquals(1, fis.read());
		assertEquals(2, fis.read());
		assertEquals(3, fis.read());
		
		src.delete();
		dest.delete();
	}
	
	@Test
	public void testToArray() throws Exception
	{
		File src = IoUtil.createTempFile();
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
	public void testReplaceExtension()
	{
		String filename = "teste.abc";
		assertEquals("teste.exe", IoUtil.replaceExtension(filename, "exe"));
	}
	
	@Test
	public void testReplaceExtension_ExtensionWithDot()
	{
		String filename = "teste.abc";
		assertEquals("teste.exe", IoUtil.replaceExtension(filename, ".exe"));
	}
	
	@Test
	public void testReplaceExtension_EmptyExtension()
	{
		String filename = "teste.abc";
		assertEquals("teste", IoUtil.replaceExtension(filename, ""));
	}
	@Test
	public void testReplaceExtension_NullExtension()
	{
		String filename = "teste.abc";
		assertEquals("teste", IoUtil.replaceExtension(filename, null));
	}
	
	@Test
	public void testReplaceExtension_FileWithouExtension()
	{
		String filename = "teste";
		assertEquals("teste.exe", IoUtil.replaceExtension(filename, "exe"));
	}
	
	@Test
	public void testReplaceExtension_FileWithouExtension_NullExtension()
	{
		String filename = "teste";
		assertEquals("teste", IoUtil.replaceExtension(filename, null));
	}
}
