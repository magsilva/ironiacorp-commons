package com.ironiacorp.computer;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.Ostermiller.util.RandPass;
import com.ironiacorp.string.StringUtil;

public class Filesystem
{
	private OperationalSystem os;
	
	protected Filesystem(OperationalSystem os)
	{
		this.os = os;
	}
	
	/**
	 * Get all the files within the directory.
	 */
	public List<File> find(File baseDir)
	{
		return find(baseDir, -1, null);
	}

	/**
	 * Get all the files within the directory.
	 */
	public List<File> find(File baseDir, Pattern pattern)
	{
		return find(baseDir, -1, pattern);
	}
	
	
	/**
	 * Get all the files within the directory.
	 */
	public List<File> find(File baseDir, int depth, Pattern pattern)
	{
		if (baseDir == null || ! baseDir.isDirectory() || ! baseDir.canRead()) {
			throw new IllegalArgumentException("A valid initial directory must be provided");
		}

		
		List<File> files = new ArrayList<File>();
		for (File file : baseDir.listFiles()) {
			if (file.isFile()) {
				if (pattern == null) { 
					files.add(file);
				} else {
					Matcher matcher = pattern.matcher(file.getName());
					if (matcher.find()) {
						files.add(file);
					}
				}
			} else if (depth != 0 && file.isDirectory() && file.canRead()) {
				files.addAll(find(file, depth - 1, pattern));
			}
		}
		return files;
	}
	
	public String getDefaultTempBasedir()
	{
		return System.getProperty("java.io.tmpdir");
	}

	
	public String getExtension(File file)
	{
		if (file == null) {
			throw new IllegalArgumentException(new NullPointerException());
		}
		return getExtension(file.getName());
	}
	
	public String getExtension(String filename)
	{
		if (filename == null) {
			throw new IllegalArgumentException(new NullPointerException());
		}
		
		int index = filename.lastIndexOf('.');

		if (index == -1) {
			return "";
		}

		return filename.substring(index + 1);
	}

	/**
	 * Replace the extension of a file for a new one.
	 * @param filename File name.
	 * @param extension New extension.
	 * @return File name with new extension.
	 */
	public String replaceExtension(String filename, String extension)
	{
		if (filename == null) {
			throw new IllegalArgumentException(new NullPointerException());
		}
			
		if (extension == null) {
			extension = "";
		} else if (extension.length() > 0 && extension.charAt(0) != '.') {
			extension = "." + extension;
		}
		
		int index = filename.lastIndexOf('.');
		if (index == -1) {
			return filename + extension;
		} else {
			return filename.substring(0, index) + extension;
		}
	}
	

	/**
	 * Create a directory (any missing parent directory is created too).
	 * 
	 * @param dir
	 *            The directory to be created.
	 */
	public File createDir(String dir)
	{
		if (dir == null) {
			throw new IllegalArgumentException(new NullPointerException());
		}
		if (dir.isEmpty()) {
			throw new IllegalArgumentException("Cannot create a directory with no name");
		}
		
		File file = new File(dir);
		return createDir(file);
	}


	/**
	 * Create a directory (any missing parent directory is created too).
	 * 
	 * @param dir
	 *            The directory to be created.
	 */
	public File createDir(File file)
	{
		if (file == null) {
			throw new IllegalArgumentException(new NullPointerException());
		}

		if (file.isDirectory()) {
			return file;
		}
		boolean result = file.mkdirs();
		if (result == false) {
			throw new UnsupportedOperationException("Error creating directory");
		}

		
		return file;
	}
	
	/**
	 * Create a a file.
	 * 
	 * @param dirname
	 *            The directory where the file must reside.
	 * @param filename
	 *            The file to be created.
	 */
	public File createFile(String dirname, String filename) throws IOException
	{
		File dir = createDir(dirname);
		return createFile(dir, filename);
	}

	/**
	 * Create a a file.
	 * 
	 * @param dirname
	 *            The directory where the file must reside.
	 * @param filename
	 *            The file to be created.
	 */
	public File createFile(File dir, String filename) throws IOException
	{
		if (dir == null) {
			throw new IllegalArgumentException("Invalid directory");
		}
		if (filename == null) {
			throw new IllegalArgumentException("Invalid filename");
		}

		dir.mkdirs();
		File file = new File(dir, filename);
		file.createNewFile();
		return file;
	}

	/**
	 * Move a file.
	 * 
	 * @param src
	 *            Source file.
	 * @param dest
	 *            Destination file.
	 */
	public void moveFile(String src, String dest) throws IOException
	{
		if (src == null || dest == null) {
			throw new IllegalArgumentException(new NullPointerException());
		}
		if (src.isEmpty() || dest.isEmpty()) {
			throw new IllegalArgumentException();
		}

		File srcFile = new File(src);
		File destFile = new File(dest);
		moveFile(srcFile, destFile);
	}

	/**
	 * Move a file.
	 * 
	 * @param src
	 *            Source file.
	 * @param dest
	 *            Destination file.
	 */
	public void moveFile(File src, File dest) throws IOException
	{
		if (src == null || dest == null) {
			throw new IllegalArgumentException(new NullPointerException());
		}

		if (src.equals(dest)) {
			throw new IllegalArgumentException("Destination is the same file as the target");
		}
		
		if (! src.exists()) {
			throw new IllegalArgumentException("Source file does not exist");
		}
		
		boolean result = src.renameTo(dest);
		if (! result) {
			copyFile(src.getAbsolutePath(), dest.getAbsolutePath());
			src.delete();
		}
	}

	/**
	 * Sync a file stream to disk.
	 * 
	 * @param fileStream
	 *            The file stream to be synchronized to disk.
	 */
	public void syncFile(FileOutputStream fileStream)
	{
		if (fileStream == null) {
			throw new IllegalArgumentException(new NullPointerException());
		}
		try {
			FileDescriptor fd = fileStream.getFD();
			fileStream.flush();
			// Block until the system buffers have been written to disk.
			fd.sync();
		} catch (IOException e) {
			throw new UnsupportedOperationException(e);
		}
	}

	/**
	 * Copy the source file to the destination file.
	 * 
	 * @param srcFilename
	 *            The source filename.
	 * @param destFilename
	 *            The destination filename.
	 */
	public void copyFile(File srcFile, File destFile) throws IOException
	{
		copyFile(srcFile.getAbsolutePath(), destFile.getAbsolutePath());
	}
	

	/**
	 * Copy the source file to the destination file (using Java NIO).
	 * 
	 * @param srcFilename
	 *            The source filename.
	 * @param destFilename
	 *            The destination filename.
	 */
	public void copyFile(String srcFilename, String destFilename) throws IOException
	{
		if (! new File(srcFilename).exists()) {
			throw new IOException();
		}

		FileInputStream srcFileStream = new FileInputStream(srcFilename);
		FileOutputStream destFileStream = new FileOutputStream(destFilename);
		FileChannel srcChannel = srcFileStream.getChannel();
		FileChannel destChannel = destFileStream.getChannel();
		srcChannel.transferTo(0, srcChannel.size(), destChannel);
		srcFileStream.close();
		destFileStream.close();
	}
	
	/**
	 * Copy the files in the source directory to the destination directory.
	 * 
	 * @param srcDir
	 *            The source directory.
	 * @param destDir
	 *            The destination directory.
	 */
	public void copyDir(String srcDirName, String destDirName) throws IOException
	{
		copyDir(srcDirName, destDirName, false);
	}

	/**
	 * Copy the files in the source directory to the destination directory.
	 * 
	 * @param srcDir
	 *            The source directory.
	 * @param destDir
	 *            The destination directory.
	 * @param recurse
	 *            Enable the recursive copy of directories.
	 */
	public void copyDir(String srcDirName, String destDirName, boolean recurse) throws IOException
	{
		File srcDir = new File(srcDirName);
		File destDir = new File(destDirName);

		copyDir(srcDir, destDir, recurse);
	}

	/**
	 * Copy the files in the source directory to the destination directory.
	 * 
	 * @param srcDir
	 *            The source directory.
	 * @param destDir
	 *            The destination directory.
	 * @param recurse
	 *            Enable the recursive copy of directories.
	 */
	public void copyDir(File srcDir, File destDir, boolean recurse) throws IOException
	{
		if (! srcDir.isDirectory()) {
			throw new IllegalArgumentException("Source directory isn't a directory");
		}
		
		if (destDir.exists()) {
			if (! destDir.isDirectory()) {
				throw new IllegalArgumentException("Destination isn't a directory");
			}
		} else {
			boolean result = destDir.mkdirs();
			if (result == false) {
				throw new IllegalArgumentException("Destination directory could not be created");
			}
		}
		
		File[] files = srcDir.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				copyDir(file, new File(destDir.getAbsolutePath() + File.separator + file.getName()), recurse);
			} else {
				copyFile(file, new File(destDir.getAbsolutePath() + File.separator + file.getName()));
			}
		}
	}

	/**
	 * Remove a file or a directory.
	 */
	public void remove(String path)
	{
		File file = new File(path);
		if (file.isDirectory()) {
			removeDir(path);
		} else if (file.isFile()) {
			removeFile(path);
		}
	}

	/**
	 * Remove a file.
	 * 
	 * @param filename
	 *            The file to be removed
	 */
	public void removeFile(String filename)
	{
		File file = new File(filename);
		if (file.isFile()) {
			file.delete();
		}
	}

	/**
	 * Remove a directory and all of it's content.
	 * 
	 * @param dirname
	 *            The directory to be removed
	 */
	public void removeDir(String dirname)
	{
		File dir = new File(dirname);
		removeDir(dir);
	}

	/**
	 * Remove a directory and all of it's content.
	 * 
	 * @param dirname
	 *            The directory to be removed
	 */
	public void removeDir(File dir)
	{
		if (dir.isDirectory()) {
			File[] listing = dir.listFiles();
			for (File file : listing) {
				if (file.isDirectory()) {
					removeDir(file.getAbsolutePath());
				}
				file.delete();
			}
			dir.delete();
		}
	}
	
	public boolean isAbsoluteFilename(String filename)
	{
		if (filename.substring(0, 1).equals(File.separator)) {
			return true;
		}

		if (os.getType() == OperationalSystemType.Windows) {
			if (filename.substring(0, 3).matches("[a-zA-Z]:" + File.separator)) {
				return true;
			}
		}

		return false;
	}
	
	/**
	 * Create a temporary directory.
	 * 
	 * @return Temporary directory or null if could not create one.
	 */
	public File createTempDir() 
	{
		String randomPrefix = new RandPass(RandPass.LOWERCASE_LETTERS_AND_NUMBERS_ALPHABET).getPass(8);
		return createTempDir(randomPrefix);
	}

	/**
	 * Create a temporary directory.
	 * 
	 * @param prefix Prefix for the directory to be created.
	 *
	 * @return Temporary directory or null if could not create one.
	 */
	public File createTempDir(String prefix)
	{
		return createTempDir(prefix, "");
	}

	/**
	 * Create a temporary directory.
	 * 
	 * @param prefix Prefix for the directory to be created.
	 * @param suffix Suffix for the directory to be created.
	 * 
	 * @return Temporary directory or null if could not create one.
	 */
	public File createTempDir(String prefix, String suffix)
	{
		return createTempDir(prefix, suffix, null);
	}

	/**
	 * Create a temporary directory.
	 * 
	 * @param prefix Prefix for the directory to be created.
	 * @param suffix Suffix for the directory to be created.
	 * @param directory Directory where the temporary directory should be created into.
	 * 
	 * @return Temporary directory or null if could not create one.
	 */
	public File createTempDir(String prefix, String suffix, String baseDirName)
	{
		final int MAX_ATTEMPTS = 50;

		if (prefix == null) {
			throw new IllegalArgumentException(new NullPointerException());
		}
		
		if (baseDirName == null) {
			baseDirName = getDefaultTempBasedir();
		}
		
		if (StringUtil.isEmpty(baseDirName)) {
			throw new RuntimeException("Could not create a temporary directory.");
		}
		if (! baseDirName.endsWith(File.separator)) {
			baseDirName += File.separator;
		}
		File baseDir = new File(baseDirName);
		if (! baseDir.exists()) {
			throw new IllegalArgumentException("Invalid base dir");
		}

		for (int i = 0; i < MAX_ATTEMPTS; i++) {
			try {
				File file = File.createTempFile(prefix, suffix, baseDir);
				String name = file.getAbsolutePath();
				file.delete();
				file = new File(name);
				file.mkdirs();
				return file;
			} catch (IOException e) {
			}
		}

		return null;
	}

	/**
	 * Create a temporary file with a random name in the default TMP directory.
	 * 
	 * @return Temporary file or null if could not create it.
	 */
	public File createTempFile()
	{
		String randomPrefix = new RandPass(RandPass.LOWERCASE_LETTERS_AND_NUMBERS_ALPHABET).getPass(8);
		return createTempFile(randomPrefix);
	}
	
	/**
	 * Create a temporary file with the given prefix within the name, and in the default TMP directory.
	 * 
	 * @param prefix Prefix for the directory to be created.
	 * 
	 * @return Temporary file or null if could not create it.
	 */
	public File createTempFile(String filePrefix)
	{
		return createTempFile(filePrefix, null);
	}

	/**
	 * Create a temporary file with the given prefix and suffix within the name, and in the default TMP directory.
	 * 
	 * @param prefix Prefix for the directory to be created.
	 * @param suffix Suffix for the directory to be created.
	 * 
	 * @return Temporary file or null if could not create it.
	 */
	public File createTempFile(String filePrefix, String fileSuffix)
	{
		final int MAX_ATTEMPTS = 50;
		
		if (filePrefix == null) {
			throw new IllegalArgumentException(new NullPointerException());
		}

		for (int i = 0; i < MAX_ATTEMPTS; i++) {
			try {
				File tempFile = File.createTempFile(filePrefix, fileSuffix);
				return tempFile;
			} catch (IOException e) {
			}
		}

		return null;
	}

	/**
	 * Create a temporary file with the given prefix and suffix within the name, and in a given directory.
	 * 
	 * @param prefix Prefix for the directory to be created.
	 * @param suffix Suffix for the directory to be created.
	 * @param dir Directory to host the temporary file.
	 * 
	 * @return Temporary file or null if could not create it.
	 */
	public File createTempFile(String filePrefix, String fileSuffix, String dirPrefix)
	{
		final int MAX_ATTEMPTS = 50;

		if (dirPrefix == null) {
			throw new IllegalArgumentException(new NullPointerException("Invalid base dir"));
		}
		
		File baseDir = new File(dirPrefix);
		if (! baseDir.exists()) {
			throw new IllegalArgumentException("Invalid base dir");
		}

		for (int i = 0; i < MAX_ATTEMPTS; i++) {
			try {
				File tempFile = File.createTempFile(filePrefix, fileSuffix, baseDir);
				return tempFile;
			} catch (IOException e) {
			}
		}

		return null;
	}
	
	public String join(String... pathComponents)
	{
		StringBuilder sb = new StringBuilder();
		int i = 0;
		while (i < (pathComponents.length - 1)) {
			sb.append(pathComponents[i]);
			sb.append(os.getDirectorySeparator());
			i++;
		}
		sb.append(pathComponents[i]);
		return sb.toString();
	}
}
