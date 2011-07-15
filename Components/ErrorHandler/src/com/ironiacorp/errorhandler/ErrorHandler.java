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

package com.ironiacorp.errorhandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.lidalia.sysoutslf4j.context.LogLevel;
import uk.org.lidalia.sysoutslf4j.context.SysOutOverSLF4J;

/**
 * Error handler.
 */
public class ErrorHandler
{
	private void enableStdOutStdErr()
	{
		SysOutOverSLF4J.registerLoggingSystem("package.of.slf4j.implementation");
		SysOutOverSLF4J.sendSystemOutAndErrToSLF4J(LogLevel.INFO, LogLevel.ERROR);
	}
	
	private void disableStdOutStdErr()
	{
		SysOutOverSLF4J.stopSendingSystemOutAndErrToSLF4J();
	}
	
	public void log(Error error)
	{
		Logger logger = LoggerFactory.getLogger(error.getAccountableObject().getClass());
		logger.error(error.toString());

	}
	
	/**
	 * Enable the error handler.
	 */
	public void enable()
	{
		enableStdOutStdErr();
	}
	
	/**
	 * Disable the error handler.
	 */
	public void disable()
	{
		disableStdOutStdErr();
	}
}
