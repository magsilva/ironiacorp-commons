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

Copyright (C) 2007 Marco Aurelio Graciotto Silva <magsilva@gmail.com>
*/

package com.ironiacorp.commons;

import java.sql.SQLException;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public final class ExceptionUtil
{
	/**
	* Commons Logging instance.
	*/
	private static Log log = LogFactory.getLog(ExceptionUtil.class);
	
	/**
	 * We really don't want an instance of this class, so we create this
	 * private constructor.
	 */
	private ExceptionUtil()
	{
	}
	
	public static void dumpStackTrace(StackTraceElement[] st)
	{
		for (StackTraceElement ste : st) {
			if (ste.isNativeMethod()) {
				log.debug(ste.getFileName() + ":" + ste.getLineNumber() + " - " + ste.getClassName() + "." + ste.getMethodName());
			} else {
				log.debug(ste.getFileName() + ":" + ste.getLineNumber() + " - " + ste.getClassName() + "." + ste.getMethodName() + "[Native method]");
			}
		}
		
	}

	public static void dumpException(Throwable e)
	{
		log.error(e.getClass().getName() + " " + e.getMessage());
		if (e instanceof SQLException) {
			SqlUtil.dumpSQLException((SQLException) e);
		}
		dumpStackTrace(e.getStackTrace());
		
		if (e.getCause() != null) {
			ExceptionUtil.dumpException(e.getCause());
		}
	}
}
