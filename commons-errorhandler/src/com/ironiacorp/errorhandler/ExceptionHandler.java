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

import java.lang.Thread.UncaughtExceptionHandler;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Exception handler.
 */
public class ExceptionHandler implements UncaughtExceptionHandler
{
	private UncaughtExceptionHandler previousHandler;
	
	/**
	 * Initialize the exception handler.
	 */
	public ExceptionHandler()
	{
	}
	
	/**
	 * Log exception.
	 * 
	 * @param exception Uncaught exception.
	 */
	private void dumpException(Throwable exception)
	{
		Logger logger = LoggerFactory.getLogger(exception.getClass());
		logger.error(exception.getMessage());
		if (exception instanceof SQLException) {
			SQLException sqle = (SQLException) exception;
			logger.error("SQLState: " + sqle.getSQLState());
			logger.error("VendorError: " + sqle.getErrorCode());
		}
		for (StackTraceElement ste : exception.getStackTrace()) {
			if (ste.isNativeMethod()) {
				logger.debug(ste.getFileName() + ":" + ste.getLineNumber() + " - " + ste.getClassName() + "." + ste.getMethodName());
			} else {
				logger.debug(ste.getFileName() + ":" + ste.getLineNumber() + " - " + ste.getClassName() + "." + ste.getMethodName() + "[Native method]");
			}
		}
		
		if (exception.getCause() != null) {
			dumpException(exception.getCause());
		}
	}

	/**
	 * Handle exception.
	 * 
	 * @param thread Thread from where the exception was thrown.
	 * @param exception Exception to be handled.
	 */
	public void uncaughtException(Thread thread, Throwable exception)
	{
		dumpException(exception);
	}
	
	public void enable()
	{
		previousHandler = Thread.getDefaultUncaughtExceptionHandler(); 
		Thread.setDefaultUncaughtExceptionHandler(this);
	}
	
	public void disable()
	{
		Thread.setDefaultUncaughtExceptionHandler(previousHandler);
	}
}
