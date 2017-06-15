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

public enum OperationalSystemType
{
	AIX("AIX", "aix", "^AIX", true, null),
	HPUX("HP-UX", "hpux", "^HP-UX", true, null),
	Irix("Irix", "irix", "^Irix", true, null),
	Linux("Linux", "linux", "^Linux", true, new Unix()),
	MacOS("MacOS", "darwin", "^(Mac|Darwin)", true, new Unix()),
	OS2("OS/2", "os2", "^OS/2", false, null),
	Solaris("Solaris", "solaris", "^(Solaris|SunOS)", true, null),
	Windows("Windows", "win", "^Windows", false, new Windows());
	
	public final String prettyName;
	
	public final String nickname;
	
	public final Pattern pattern;
	
	public final boolean unixCompatible;
	
	public final OperationalSystem os;
	
	private OperationalSystemType(String prettyName, String nickname, String regex, boolean unixCompatible, OperationalSystem os)
	{
		this.prettyName = prettyName;
		this.nickname = nickname;
		this.pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
		this.unixCompatible = unixCompatible;
		this.os = os;
	}
}