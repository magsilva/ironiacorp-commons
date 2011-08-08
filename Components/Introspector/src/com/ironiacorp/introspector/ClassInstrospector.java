/*
 * Copyright (C) 2007 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>
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

package com.ironiacorp.introspector;

import java.io.File;
import java.io.InputStream;

import com.ironiacorp.io.IoUtil;

/**
 * Analyze and change class properties.
 */
public class ClassInstrospector
{
	public static final String FILE_EXTENSION = ".class";
	
	private byte[] bytecode;
	
	private Class<?> clazz;
	
	public byte[] getBytecode()
	{
		return bytecode;
	}

	public void setBytecode(byte[] bytecode)
	{
		this.bytecode = bytecode;
	}

	public Class<?> getClazz()
	{
		return clazz;
	}

	public void setClazz(Class<?> clazz)
	{
		String className = clazz.getName();
		String classAsPath = className.replace('.', '/') + FILE_EXTENSION;
		InputStream is = clazz.getClassLoader().getResourceAsStream(classAsPath);

		this.clazz = clazz;
		if (is != null) {
			setBytecode(IoUtil.toByteArray(is));
		}
	}

	/**
	 * Modify a class and make it public. This is used for the "main" classes of applications.
	 */
	public void forcePublic()
	{
		int constant_pool_count = ((bytecode[8] & 0xff) << 8) | (bytecode[9] & 0xff);

		int currOffset = 10;

		// seek through everything in the way of the access modifiers
		for (int i = 1; i < constant_pool_count; i++) {
			switch (bytecode[currOffset] & 0xff) {
				case 7:
				case 8: // CONSTANT_Class, CONSTANT_String
					currOffset += 3;
					break;
				case 9:
				case 10:
				case 11:
				case 12:
				case 3:
				case 4: // CONSTANT_Fieldref, CONSTANT_Methodref
					currOffset += 5; // CONSTANT_InterfaceMethodref,
					// CONSTANT_NameAndType
					break; // CONSTANT_Integer, CONSTANT_Float
				case 5:
				case 6: // CONSTANT_Long, CONSTANT_Double
					currOffset += 9;
					i++;
					break;
				case 1:
					int length = ((bytecode[++currOffset] & 0xff) << 8) | (bytecode[++currOffset] & 0xff);
					currOffset += length + 1;
					break;
				default:
					return;
			}
		}

		// add PUBLIC flag
		bytecode[currOffset + 1] |= 1;
	}
	
	
	public String toString(File dir, File filename)
	{
		return toString(dir.getName(), filename.getName());
	}
	
	public String toString(String dir, String filename)
	{
		int baseNameSize = dir.length();
		String className = dir.substring(baseNameSize);
		className = className.replaceAll(File.separator, ReflectionUtil.PACKAGE_DELIMITER);
		if (className.endsWith(ReflectionUtil.CLASS_FILE_EXTENSION)) {
			className = className.substring(0, className.lastIndexOf(ReflectionUtil.CLASS_FILE_EXTENSION));
		}
		if (className.endsWith(ReflectionUtil.JAVA_FILE_EXTENSION)) {
			className = className.substring(0, className.lastIndexOf(ReflectionUtil.JAVA_FILE_EXTENSION));
		}
		
		return className;
	}
}