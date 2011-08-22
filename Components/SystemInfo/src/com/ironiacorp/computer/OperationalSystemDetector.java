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


package com.ironiacorp.computer;

public class OperationalSystemDetector
{
	public boolean isWindows(final String os)
	{
		return OperationalSystem.Windows.pattern.matcher(os).find();
	}

	public boolean isMac(final String os)
	{
		return OperationalSystem.MacOS.pattern.matcher(os).find();
	}

	public boolean isLinux(final String os)
	{
		return OperationalSystem.Linux.pattern.matcher(os).find();
	}
	
	public OperationalSystem detectOS(final String osName)
	{
		for (OperationalSystem os : OperationalSystem.values()) {
			if (os.pattern.matcher(osName).find()) {
				return os;
			}
		}
		
		return null;
	}
	
	public OperationalSystem detectCurrentOS()
	{
		final String osName = System.getProperty("os.name").toLowerCase();
		return detectOS(osName);
	}
}