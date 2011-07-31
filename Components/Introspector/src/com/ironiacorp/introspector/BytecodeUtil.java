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

public final class BytecodeUtil
{
	/**
	 * Take a byte array of class data and modify it to ensure that the class is
	 * public. This is used for the "main" classes of applications.
	 */
	public static void forcePublic(byte[] theClass)
	{
		int constant_pool_count = ((theClass[8] & 0xff) << 8) | (theClass[9] & 0xff);

		int currOffset = 10;

		// seek through everything in the way of the access modifiers
		for (int i = 1; i < constant_pool_count; i++) {
			switch (theClass[currOffset] & 0xff) {
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
					int length = ((theClass[++currOffset] & 0xff) << 8) | (theClass[++currOffset] & 0xff);
					currOffset += length + 1;
					break;
				default:
					return;
			}
		}

		// add PUBLIC flag
		theClass[currOffset + 1] |= 1;
	}

}
