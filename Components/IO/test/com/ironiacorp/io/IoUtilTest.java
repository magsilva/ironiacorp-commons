package com.ironiacorp.io;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

public class IoUtilTest {

	private static String MIN_VALID_PREFIX = "   ";

	private static String MIN_VALID_SUFFIX = "   ";

	@Test
	public void testCreateTempDir() throws IOException {
		File dir = IoUtil.createTempDir();
		assertNotNull(dir);
		assertTrue(dir.isDirectory());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCreateTempDirPrefix_PrefixEmpty() {
		IoUtil.createTempDir("");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCreateTempDirPrefix_PrefixNull() {
		IoUtil.createTempDir(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateTempDirPrefix_PrefixShort1() {
		IoUtil.createTempDir(" ");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateTempDirPrefix_PrefixShort2() {
		IoUtil.createTempDir("  ");
	}
	
	@Test
	public void testCreateTempDirPrefix_PrefixShort3() {
		File dir = IoUtil.createTempDir(IoUtilTest.MIN_VALID_PREFIX);
		assertNotNull(dir);
		assertTrue(dir.isDirectory());
	}

	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateTempDirPrefixSuffix_PrefixEmptySuffixEmpty() {
		IoUtil.createTempDir("", "");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateTempDirPrefixSuffix_PrefixEmptySuffixNotEmpty() {
		IoUtil.createTempDir("", IoUtilTest.MIN_VALID_SUFFIX);
	}
	
	@Test
	public void testCreateTempDirPrefixSuffix_PrefixNotEmptySuffixEmpty() {
		File dir = IoUtil.createTempDir(IoUtilTest.MIN_VALID_PREFIX, "");
		assertNotNull(dir);
		assertTrue(dir.isDirectory());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateTempDirPrefixSuffix_PrefixNullSuffixNull() {
		IoUtil.createTempDir(null, null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCreateTempDirPrefixSuffix_PrefixNullSuffixNotNull() {
		IoUtil.createTempDir(null, IoUtilTest.MIN_VALID_SUFFIX);
	}

	@Test
	public void testCreateTempDirPrefixSuffix_PrefixNotNullSuffixNull() {
		File dir = IoUtil.createTempDir(IoUtilTest.MIN_VALID_PREFIX, null);
		assertNotNull(dir);
		assertTrue(dir.isDirectory());
	}

	@Test
	public void testCreateTempDirPrefixSuffix() {
		File dir = IoUtil.createTempDir(IoUtilTest.MIN_VALID_PREFIX, IoUtilTest.MIN_VALID_SUFFIX);
		assertNotNull(dir);
		assertTrue(dir.isDirectory());
	}

	@Test
	public void testCreateTempDirPrefixSuffixBasedir() {
		File tmpDir = IoUtil.createTempDir(IoUtilTest.MIN_VALID_PREFIX, IoUtilTest.MIN_VALID_SUFFIX);
		File dir = IoUtil.createTempDir(IoUtilTest.MIN_VALID_PREFIX, IoUtilTest.MIN_VALID_SUFFIX, tmpDir.getAbsolutePath());
		assertNotNull(dir);
		assertTrue(dir.isDirectory());
	}
	
	@Test
	public void testCreateTempDirPrefixSuffixBasedir_NullBasedir() {
		File dir = IoUtil.createTempDir(IoUtilTest.MIN_VALID_PREFIX, IoUtilTest.MIN_VALID_SUFFIX, null);
		assertNotNull(dir);
		assertTrue(dir.isDirectory());
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateTempDirPrefixSuffixBasedir_InvalidBasedir() {
		File tmpDir = IoUtil.createTempDir(IoUtilTest.MIN_VALID_PREFIX, IoUtilTest.MIN_VALID_SUFFIX);
		File dir = IoUtil.createTempDir(IoUtilTest.MIN_VALID_PREFIX, IoUtilTest.MIN_VALID_SUFFIX, tmpDir.getAbsolutePath() + "abc");
		assertNotNull(dir);
		assertTrue(dir.isDirectory());
	}

	@Test
	public void testCreateTempFile() {
		File file = IoUtil.createTempFile();
		assertNotNull(file);
		assertTrue(file.isFile());
	}

	@Test
	public void testCreateTempFilePrefix() {
		File file = IoUtil.createTempFile(IoUtilTest.MIN_VALID_PREFIX);
		assertNotNull(file);
		assertTrue(file.isFile());
	}
		
	@Test(expected=IllegalArgumentException.class)
	public void testCreateTempFilePrefix_Null() {
		IoUtil.createTempFile("");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateTempFilePrefix_Empty() {
		IoUtil.createTempFile("");
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCreateTempFilePrefix_Short() {
		IoUtil.createTempFile(" ");
	}

	@Test
	public void testCreateTempFilePrefixSuffix() {
		File file = IoUtil.createTempFile(IoUtilTest.MIN_VALID_PREFIX, IoUtilTest.MIN_VALID_SUFFIX);
		assertNotNull(file);
		assertTrue(file.isFile());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCreateTempFilePrefixSuffix_NullPrefixNullSuffix() {
		IoUtil.createTempFile(null, null);
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCreateTempFilePrefixSuffix_NullPrefixNotNullSuffix() {
		IoUtil.createTempFile(null, IoUtilTest.MIN_VALID_SUFFIX);
	}

	@Test
	public void testCreateTempFilePrefixSuffix_NotNullPrefixNullSuffix() {
		File file = IoUtil.createTempFile(IoUtilTest.MIN_VALID_PREFIX, null);
		assertNotNull(file);
		assertTrue(file.isFile());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCreateTempFilePrefixSuffix_EmptyPrefixNotEmptySuffix() {
		IoUtil.createTempFile("", IoUtilTest.MIN_VALID_SUFFIX);
	}

	@Test
	public void testCreateTempFilePrefixSuffix_NotEmptyPrefixEmptySuffix() {
		File file = IoUtil.createTempFile(IoUtilTest.MIN_VALID_PREFIX, "");
		assertNotNull(file);
		assertTrue(file.isFile());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCreateTempFilePrefixSuffix_EmptyPrefixEmptySuffix() {
		IoUtil.createTempFile("", "");
	}

	@Test
	public void testCreateTempFilePrefixSuffixBasedir() {
		File tmpDir = IoUtil.createTempDir(IoUtilTest.MIN_VALID_PREFIX, IoUtilTest.MIN_VALID_SUFFIX);
		File file = IoUtil.createTempFile(IoUtilTest.MIN_VALID_PREFIX, IoUtilTest.MIN_VALID_SUFFIX, tmpDir.getAbsolutePath());
		assertNotNull(file);
		assertTrue(file.isFile());
	}

	@Test(expected=IllegalArgumentException.class)
	public void testCreateTempFilePrefixSuffixBasedir_InvalidBasedir() {
		File tmpDir = IoUtil.createTempFile(IoUtilTest.MIN_VALID_PREFIX, IoUtilTest.MIN_VALID_SUFFIX);
		IoUtil.createTempDir(IoUtilTest.MIN_VALID_PREFIX, IoUtilTest.MIN_VALID_SUFFIX, tmpDir.getAbsolutePath() + "aaa");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateTempFilePrefixSuffixBasedir_NullBasedir() {
		IoUtil.createTempFile(IoUtilTest.MIN_VALID_PREFIX, IoUtilTest.MIN_VALID_SUFFIX, null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateTempFilePrefixSuffixBasedir_EmptyBasedir() {
		IoUtil.createTempFile(IoUtilTest.MIN_VALID_PREFIX, IoUtilTest.MIN_VALID_SUFFIX, "");
	}


}
