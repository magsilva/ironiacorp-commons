package com.ironiacorp.io;
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

Copyright (C) 2009 Marco Aurelio Graciotto Silva <magsilva@ironiacorp.com>
*/



import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

public class FileLocker
{
	public FileLock getSharedLock(File file)
	{
		return getLock(file, true, 0, file.length());
	}
	
	public FileLock getExclusiveLock(File file)
	{
		return getLock(file, false, 0, file.length());
	}
	
	public FileLock getLock(File file, boolean shared, long initPosition, long length)
	{
		if (! file.exists()) {
			throw new IllegalArgumentException("File does not exist");
		}
		
		if (initPosition >= file.length()) {
			throw new IllegalArgumentException("Initial position out of range");
		}
		
		if ((initPosition + length) > file.length()) {
			throw new IllegalArgumentException("Final position out of range");
		}
		
		
		RandomAccessFile randomFile = null;
		FileChannel channel = null;
		FileLock lock = null;
		
		try {
			if (shared) {
				randomFile = new RandomAccessFile(file, "r");
			} else {
				randomFile = new RandomAccessFile(file, "rw");
			}
		} catch (FileNotFoundException e) {
			assert false : "File does not exist";
		}
		
		channel = randomFile.getChannel();
		
		try {
			lock = channel.lock(initPosition, length, shared);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return lock;
	}
}
