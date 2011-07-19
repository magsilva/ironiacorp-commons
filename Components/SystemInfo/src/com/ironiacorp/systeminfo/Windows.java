/*
 * Copyright (C) 2011 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>
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

package com.ironiacorp.systeminfo;

import java.io.File;

public class Windows
{
	public static final char UNIT_NAME_BEGIN = 'c';
    
	public static final char UNIT_NAME_END = 'z';

	
	public boolean isSystemRoot(File file)
	{
		final String[] systemDirNames = {
			"system32",
			"System32",
			"SYSTEM32",
			"system",
			"System",
			"SYSTEM",
		};

		if (file == null) {
			return false;
		}
		
		if (file.exists() && file.isDirectory()) {
			for (String dirname : systemDirNames) {
				File systemRoot = new File(file, dirname);
				if (file.exists() && file.isDirectory()) {
					return true;
				}
			}
		}
		return false;
	}
	
	public File findSystemRoot()
	{
		final String[] defaultSystemRoot = {
				"WINDOWS",
				"Windows",
				"windows",
				"WINNT",
				"Winnt",
				"winnt",
		};
		String rootFilename = System.getenv("SYSTEMROOT");
		File root;
		
		// Try some lucky shoot
		if (rootFilename != null) {
			root = new File(rootFilename);
			return root;
		}

		// Search for the system root in the available units
	    for (char drive = UNIT_NAME_BEGIN; drive < UNIT_NAME_END; drive++) {
	    	for (String dirname : defaultSystemRoot) {
	    		root = new File(drive + ":\\" + "\\WINDOWS");
	    		if (isSystemRoot(root)) {
	    			return root;	
	    		}
	    	}
	    }

	    return null;
	}
}
