package com.ironiacorp.computer;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;

import com.ironiacorp.computer.Filesystem;

public class FilesystemTest
{
	private static String MIN_VALID_PREFIX = "   ";

	private static String MIN_VALID_SUFFIX = "   ";
	
	private Filesystem fs;
	
	@Before
	public void setUp() throws Exception
	{
		OperationalSystem os = ComputerSystem.getCurrentOperationalSystem();
		fs = os.getFilesystem();
	}

	@Test
	public void testFindFile()
	{
		List<File> files = fs.find(new File("/tmp"));
		assertNotNull(files);
	}

	@Test
	public void testFindFileIntPattern()
	{
		List<File> files = fs.find(new File("/tmp"), Pattern.compile("\\.pdf$"));
		assertNotNull(files);
	}
	
	@Test
	public void testCreateTempDir() throws IOException
	{
		File dir = fs.createTempDir();
		assertNotNull(dir);
		assertTrue(dir.isDirectory());
		dir.delete();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempDirPrefix_PrefixEmpty()
	{
		fs.createTempDir("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempDirPrefix_PrefixNull()
	{
		fs.createTempDir(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempDirPrefix_PrefixShort1()
	{
		fs.createTempDir(" ");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempDirPrefix_PrefixShort2()
	{
		fs.createTempDir("  ");
	}

	@Test
	public void testCreateTempDirPrefix_PrefixShort3()
	{
		File dir = fs.createTempDir(MIN_VALID_PREFIX);
		assertNotNull(dir);
		assertTrue(dir.isDirectory());
		dir.delete();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempDirPrefixSuffix_PrefixEmptySuffixEmpty()
	{
		fs.createTempDir("", "");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempDirPrefixSuffix_PrefixEmptySuffixNotEmpty()
	{
		fs.createTempDir("", MIN_VALID_SUFFIX);
	}

	@Test
	public void testCreateTempDirPrefixSuffix_PrefixNotEmptySuffixEmpty()
	{
		File dir = fs.createTempDir(MIN_VALID_PREFIX, "");
		assertNotNull(dir);
		assertTrue(dir.isDirectory());
		dir.delete();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempDirPrefixSuffix_PrefixNullSuffixNull()
	{
		fs.createTempDir(null, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempDirPrefixSuffix_PrefixNullSuffixNotNull()
	{
		fs.createTempDir(null, MIN_VALID_SUFFIX);
	}

	@Test
	public void testCreateTempDirPrefixSuffix_PrefixNotNullSuffixNull()
	{
		File dir = fs.createTempDir(MIN_VALID_PREFIX, null);
		assertNotNull(dir);
		assertTrue(dir.isDirectory());
		dir.delete();
	}

	@Test
	public void testCreateTempDirPrefixSuffix()
	{
		File dir = fs.createTempDir(MIN_VALID_PREFIX, MIN_VALID_SUFFIX);
		assertNotNull(dir);
		assertTrue(dir.isDirectory());
		dir.delete();
	}

	@Test
	public void testCreateTempDirPrefixSuffixBasedir()
	{
		File tmpDir = fs
						.createTempDir(MIN_VALID_PREFIX, MIN_VALID_SUFFIX);
		File dir = fs.createTempDir(MIN_VALID_PREFIX, MIN_VALID_SUFFIX,
						tmpDir.getAbsolutePath());
		assertNotNull(dir);
		assertTrue(dir.isDirectory());
		dir.delete();
		tmpDir.delete();
	}

	@Test
	public void testCreateTempDirPrefixSuffixBasedir_NullBasedir()
	{
		File dir = fs.createTempDir(MIN_VALID_PREFIX, MIN_VALID_SUFFIX,
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
			tmpDir = fs.createTempDir(MIN_VALID_PREFIX, MIN_VALID_SUFFIX);
			dir = fs.createTempDir(MIN_VALID_PREFIX, MIN_VALID_SUFFIX, tmpDir.getAbsolutePath() + "abc");
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
		File file = fs.createTempFile();
		assertNotNull(file);
		assertTrue(file.isFile());
		file.delete();
	}

	@Test
	public void testCreateTempFilePrefix()
	{
		File file = fs.createTempFile(MIN_VALID_PREFIX);
		assertNotNull(file);
		assertTrue(file.isFile());
		file.delete();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempFilePrefix_Null()
	{
		fs.createTempFile("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempFilePrefix_Empty()
	{
		fs.createTempFile("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempFilePrefix_Short()
	{
		fs.createTempFile(" ");
	}

	@Test
	public void testCreateTempFilePrefixSuffix()
	{
		File file = fs.createTempFile(MIN_VALID_PREFIX, MIN_VALID_SUFFIX);
		assertNotNull(file);
		assertTrue(file.isFile());
		file.delete();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempFilePrefixSuffix_NullPrefixNullSuffix()
	{
		fs.createTempFile(null, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempFilePrefixSuffix_NullPrefixNotNullSuffix()
	{
		fs.createTempFile(null, MIN_VALID_SUFFIX);
	}

	@Test
	public void testCreateTempFilePrefixSuffix_NotNullPrefixNullSuffix()
	{
		File file = fs.createTempFile(MIN_VALID_PREFIX, null);
		assertNotNull(file);
		assertTrue(file.isFile());
		file.delete();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempFilePrefixSuffix_EmptyPrefixNotEmptySuffix()
	{
		fs.createTempFile("", MIN_VALID_SUFFIX);
	}

	@Test
	public void testCreateTempFilePrefixSuffix_NotEmptyPrefixEmptySuffix()
	{
		File file = fs.createTempFile(MIN_VALID_PREFIX, "");
		assertNotNull(file);
		assertTrue(file.isFile());
		file.delete();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempFilePrefixSuffix_EmptyPrefixEmptySuffix()
	{
		fs.createTempFile("", "");
	}

	@Test
	public void testCreateTempFilePrefixSuffixBasedir()
	{
		File tmpDir = fs
						.createTempDir(MIN_VALID_PREFIX, MIN_VALID_SUFFIX);
		File file = fs.createTempFile(MIN_VALID_PREFIX, MIN_VALID_SUFFIX,
						tmpDir.getAbsolutePath());
		assertNotNull(file);
		assertTrue(file.isFile());
		file.delete();
		tmpDir.delete();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempFilePrefixSuffixBasedir_InvalidBasedir()
	{
		File tmpDir = fs.createTempFile(MIN_VALID_PREFIX,
						MIN_VALID_SUFFIX);
		try {
			fs.createTempDir(MIN_VALID_PREFIX, MIN_VALID_SUFFIX,
						tmpDir.getAbsolutePath() + "aaa");
		} catch (IllegalArgumentException e) {
			tmpDir.delete();
			throw e;
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempFilePrefixSuffixBasedir_NullBasedir()
	{
		fs.createTempFile(MIN_VALID_PREFIX, MIN_VALID_SUFFIX, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testCreateTempFilePrefixSuffixBasedir_EmptyBasedir()
	{
		fs.createTempFile(MIN_VALID_PREFIX, MIN_VALID_SUFFIX, "");
	}

	@Test
	public void testGetExtension_Valid_Single()
	{
		String extension = fs.getExtension("test.txt");
		assertEquals("txt", extension);
	}

	@Test
	public void testGetExtension_Valid_Multiple()
	{
		String extension = fs.getExtension("test.txt.txt");
		assertEquals("txt", extension);
	}

	@Test
	public void testGetExtension_Valid_Empty()
	{
		String extension = fs.getExtension("test.");
		assertEquals("", extension);
	}

	@Test
	public void testGetExtension_Valid_Empty_Multiple()
	{
		String extension = fs.getExtension("test..");
		assertEquals("", extension);
	}

	@Test
	public void testGetExtension_Valid_Empty_Inexistent()
	{
		String extension = fs.getExtension("test");
		assertEquals("", extension);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testGetExtension_Invalid()
	{
		fs.getExtension( (File) null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateDir_Invalid_Null()
	{
		fs.createDir((String) null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateDir_Invalid_Empty()
	{
		fs.createDir("");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCreateDir_Invalid_NullFile()
	{
		fs.createDir((File) null);
	}
	
	@Test
	public void testCreateDir_Valid()
	{
		File dir = fs.createDir("/tmp/1");
		assertTrue(dir.isDirectory());
		dir.delete();
	}

	@Test
	public void testCreateDir_Valid_AlreadyExists()
	{
		File dirBefore = fs.createDir("/tmp/1");
		File dirAfter = fs.createDir("/tmp/1");
		assertTrue(dirBefore.isDirectory());
		assertTrue(dirAfter.isDirectory());
		dirBefore.delete();
		dirAfter.delete();
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testCreateDir_Invalid_IsFile()
	{
		fs.createDir("/proc/uptime");
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testCreateDir_Invalid_HasNoPermission()
	{
		fs.createDir("/etc/abc");
	}
	
	@Test
	public void testCreateFileInDir_Valid() throws Exception
	{
		File file = fs.createFile("/tmp", "abc");
		assertNotNull(file);
		assertTrue(file.exists());
		assertEquals("/tmp/abc", file.getAbsolutePath());
		file.delete();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateFileInDir_InvalidDir() throws Exception
	{
		fs.createFile((String) null, "abc");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCreateFileInDir_InvalidDir_Null() throws Exception
	{
		fs.createFile((File) null, "abc");
	}

	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateFileInDir_InvalidFile() throws Exception
	{
		fs.createFile("/tmp", null);
	}
	
	@Test
	public void testMoveFile_StringString_Valid() throws Exception
	{
		File in = fs.createTempFile();
		File out = fs.createTempFile("abc");
		out.delete();
		fs.moveFile(in.getAbsolutePath(), out.getAbsolutePath());
		assertFalse(in.exists());
		assertTrue(out.exists());
		out.delete();
	}

	@Test(expected=IllegalArgumentException.class)
	public void testMoveFile_StringString_Invalid_NullNull() throws Exception
	{
		fs.moveFile((String) null, (String) null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testMoveFile_StringString_Invalid_Empty() throws Exception
	{
		fs.moveFile("", "");
	}

	
	@Test
	public void testMoveFile_FileFile_Valid() throws Exception
	{
		File in = fs.createTempFile();
		File out = fs.createTempFile("abc");
		out.delete();
		fs.moveFile(in, out);
		assertFalse(in.exists());
		assertTrue(out.exists());
		out.delete();
	}

	@Test(expected=IllegalArgumentException.class)
	public void testMoveFile_FileFile_Invalid_NullNull() throws Exception
	{
		fs.moveFile((File) null, (File) null);
	}

	@Test
	public void testMoveFile_FileFile_Valid_TargetExists() throws Exception
	{
		File in = fs.createTempFile();
		File out = fs.createTempFile(); 
		out.createNewFile();
		fs.moveFile(in, out);
		assertFalse(in.exists());
		assertTrue(out.exists());
		out.delete();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testMoveFile_FileFile_Invalid_Source() throws Exception
	{
		File in = new File("/tmp/sfldkjlçasjl");
		File out = fs.createTempFile();
		try {
			fs.moveFile(in, out);
		} catch (IllegalArgumentException iae) {
			out.delete();
			throw iae;
		}
	}


	@Test(expected=IllegalArgumentException.class)
	public void testMoveFile_FileFile_Invalid_SourceDestinationAreSame() throws Exception
	{
		File in = fs.createTempFile();
		File out = in;
		try {
			fs.moveFile(in, out);
		} catch (IllegalArgumentException iae) {
			in.delete();
			throw iae;
		}
	}
	
	@Test
	public void testSyncFile_Valid() throws Exception
	{
		File file = fs.createTempFile();
		fs.syncFile(new FileOutputStream(file));
		file.delete();
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testSyncFile_Invalid_Null() throws Exception
	{
		fs.syncFile(null);
	}

	@Test(expected=UnsupportedOperationException.class)
	public void testSyncFile_Invalid_ClosedStream() throws Exception
	{
		File file = fs.createTempFile();
		FileOutputStream fos = new FileOutputStream(file);
		fos.close();
		fs.syncFile(fos);
		file.delete();
	}

	
	@Test
	public void testReplaceExtension()
	{
		String filename = "teste.abc";
		assertEquals("teste.exe", fs.replaceExtension(filename, "exe"));
	}
	
	@Test
	public void testReplaceExtension_ExtensionWithDot()
	{
		String filename = "teste.abc";
		assertEquals("teste.exe", fs.replaceExtension(filename, ".exe"));
	}
	
	@Test
	public void testReplaceExtension_EmptyExtension()
	{
		String filename = "teste.abc";
		assertEquals("teste", fs.replaceExtension(filename, ""));
	}
	@Test
	public void testReplaceExtension_NullExtension()
	{
		String filename = "teste.abc";
		assertEquals("teste", fs.replaceExtension(filename, null));
	}
	
	@Test
	public void testReplaceExtension_FileWithouExtension()
	{
		String filename = "teste";
		assertEquals("teste.exe", fs.replaceExtension(filename, "exe"));
	}
	
	@Test
	public void testReplaceExtension_FileWithouExtension_NullExtension()
	{
		String filename = "teste";
		assertEquals("teste", fs.replaceExtension(filename, null));
	}
}
