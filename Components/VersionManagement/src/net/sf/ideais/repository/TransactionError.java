/*
Wiki/RE - A requirements engineering wiki
Copyright (C) 2005 Marco Aurélio Graciotto Silva

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
*/

package net.sf.ideais.repository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Exception launched when a anomaly is detected when creating or executing
 * a transaction.
 * 
 * This exception should encapsulate the exception that triggered the real
 * transaction error (if any).
 * 
 * @author Marco Aurélio Graciotto Silva
 */
public class TransactionError extends RuntimeException
{
	/**
	* Commons Logging instance.
	*/
	private static Log log = LogFactory.getLog( TransactionError.class );
	
	/**
	 * The versionUID for the TransactionError exception.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param message The key for the message about this error class.
	 * @param cause The exception that triggered the transaction error.
	 */
	public TransactionError( String message )
	{
		super( message );
		log.error( message );
	}

	/**
	 * @param message The key for the message about this error class.
	 * @param cause The exception that triggered the transaction error.
	 */
	public TransactionError( String message, Throwable cause )
	{
		super( message, cause );
		log.error( message, cause );
	}
}