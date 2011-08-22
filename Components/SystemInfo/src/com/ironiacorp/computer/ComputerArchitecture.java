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

import java.util.regex.Pattern;

public enum ComputerArchitecture
{
	x86("x86", "^(x86|i386|i486|i586|i686)"),
	x86_64("x86-64", "^(AMD64|IA-32e|EM64T|Intel 64|x86-64|x64)");
	
	public final String prettyName;
	
	public final Pattern pattern;
	
	private ComputerArchitecture(String prettyName, String regex)
	{
		this.prettyName = prettyName;
		this.pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
	}
}
