/*
 * Copyright (c) 2011 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.ironiacorp.statistics.r;

import java.io.File;

import com.ironiacorp.computer.OperationalSystem;
import com.ironiacorp.computer.OperationalSystemDetector;
import com.ironiacorp.computer.Windows;

/**
 * Rserve (http://www.rforge.net/Rserve/) is a TCP/IP server which allows
 * other programs to use facilities of R (www.r-project.org) from various
 * languages without the need to initialize R or link against R library.
 * Every connection has a separate workspace and working directory.
 * 
 * @author magsilva
 *
 */
public class RServe
{
	private static final String[] subdirs = {"library", "site-library"};

	private File executable;
	
	public File getExecutable()
	{
		return executable;
	}

	public void setExecutable(File executable)
	{
		this.executable = executable;
	}
	
	public void start()
	{
		String rserveCmd = "R CMD Rserve";
		String rHome = System.getenv("R_HOME");
		File rserveExecutable;
		
		if (rHome != null) {
			StringBuilder sb = new StringBuilder();
			OperationalSystemDetector detector = new OperationalSystemDetector();
			OperationalSystem os = detector.detectCurrentOS();
			for (String subdir : subdirs) {
				sb.append(rHome);
				sb.append(File.separator);
				sb.append(subdir);
				sb.append(File.separator);
				sb.append("Rserve");
				sb.append(File.separator);
				sb.append("Rserve");
				if (os == OperationalSystem.Windows) {
					sb.append(Windows.EXECUTABLE_PREFIX);
				}
				rserveExecutable = new File(sb.toString());
				sb.setLength(0);
				if (rserveExecutable.exists() && rserveExecutable.isFile() && rserveExecutable.canExecute()) {
					rserveCmd = rserveExecutable.getAbsolutePath();
					break;
				}
			}
		}
		
		try {
			Runtime.getRuntime().exec(rserveCmd);
		} catch (Exception e) {}
    }
}
