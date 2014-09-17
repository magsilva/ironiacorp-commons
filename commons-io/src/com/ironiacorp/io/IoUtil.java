/*
This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.
 
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
 
You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 
Copyright (C) 2005 Marco Aurelio Graciotto Silva <magsilva@gmail.com>
 */

package com.ironiacorp.io;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Methods useful for file manipulations (what a shame Java doesn't have them in
 * its standard library).
 */
public final class IoUtil
{
	/**
	 * We really don't want an instance of this class, so we create this private
	 * constructor.
	 */
	private IoUtil()
	{
	}

	public static int BUFFER_SIZE = 8192;


	/**
	 * Dump a file content to an array of bytes.
	 * 
	 * @param filename
	 *            The name of the file to be dumped.
	 */
	public static byte[] dumpFile(String filename) throws IOException
	{
		return dumpFile(new File(filename));
	}

	/**
	 * Dump a file content to an array of bytes.
	 * 
	 * @param file
	 *            The file to be dumped.
	 */
	public static byte[] dumpFile(File file) throws IOException
	{
		FileInputStream stream = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		stream.read(data, 0, (int) file.length());
		stream.close();
		return data;
	}

	public static boolean compare(File f1, File f2)
	{
		if (f1.length() != f2.length()) {
			return false;
		}

		
		try (
			FileInputStream f1Stream = new FileInputStream(f1);
			FileInputStream f2Stream = new FileInputStream(f2);
		) {
			byte[] buffer1 = new byte[BUFFER_SIZE];
			byte[] buffer2 = new byte[BUFFER_SIZE];
			int bytesRead1 = 0;
			int bytesRead2 = 0;
			boolean result = true;

			do {
				bytesRead1 = f1Stream.read(buffer1, 0, buffer1.length);
				bytesRead2 = f2Stream.read(buffer2, 0, buffer2.length);
				if (bytesRead1 != bytesRead2) {
					result = false;
					break;
				}
				if (!Arrays.equals(buffer1, buffer2)) {
					result = false;
					break;
				}
			} while (bytesRead1 != -1 && bytesRead2 != -1);

			return result;
		
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Files do not exist");
		} catch (IOException e) {
			throw new IllegalArgumentException("Error reading from files");
		} 
	}

	public static String dumpAsString(InputStream is) throws IOException
	{
		return dumpAsString(is, Charset.defaultCharset());
	}

	public static String dumpAsString(InputStream is, Charset encoding) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		BufferedReader in = new BufferedReader(new InputStreamReader(is, encoding));
		char[] buf = new char[IoUtil.BUFFER_SIZE];
		int numRead = 0;
		while ((numRead = in.read(buf)) != -1) {
			sb.append(buf, 0, numRead);
		}
		in.close();
		return sb.toString();
	}

	public static String dumpAsString(File file) throws IOException
	{
		return dumpAsString(file, Charset.defaultCharset());
	}
	
	public static String dumpAsString(File file, Charset encoding) throws IOException
	{
		Path path = Paths.get(file.getAbsolutePath());
		byte[] encodedData = Files.readAllBytes(path);
		CharBuffer data = encoding.decode(ByteBuffer.wrap(encodedData));
		return data.toString();
	}
	
	
	public static void copyStream(InputStream in, OutputStream out) throws IOException
	{
		if (in == null || out == null) {
			throw new IllegalArgumentException(new NullPointerException());
		}
		
		int readBytes = 0;
		byte[] buffer = new byte[IoUtil.BUFFER_SIZE];
		while ((readBytes = in.read(buffer, 0, buffer.length)) != -1) {
			out.write(buffer, 0, readBytes);
		}
	}
	
	public static byte[] toByteArray(InputStream is)
	{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		try {
			copyStream(is, outputStream);
		} catch (IOException e) {
			return null;
		}
		return outputStream.toByteArray();
	}
	
	public static File toFile(InputStream is, File file)
	{
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(file);
			copyStream(is, outputStream);
			outputStream.close();
		} catch (IOException e) {
			return null;
		}
		return file;
	}
}
