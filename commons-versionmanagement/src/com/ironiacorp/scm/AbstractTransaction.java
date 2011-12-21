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

Copyright (C) 2007 Marco Aurélio Graciotto Silva <magsilva@gmail.com>
*/

package com.ironiacorp.scm;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * Base class for new transactions.
 * 
 * @author Marco Aurélio Graciotto Silva
 */
public abstract class AbstractTransaction implements Transaction
{
	/**
	* Commons Logging instance.
	*/
	private static Log log = LogFactory.getLog( AbstractTransaction.class );

	/**
	 * The unique id for this transaction.
	 */
	private int id;
	
	/**
	 * Check if the transaction has been completed (committed or aborted).
	 */
	private boolean completed;
	
	/**
	 * Let the transaction survive till next request?
	 */
	private boolean tick;
	
	/**
	 * Create a new transaction within the session and for the choosen
	 * Project.
	 * 
	 * @param session The session this transaction belongs to.
	 * @param project The project this transaction will act upon.
	 */
	public AbstractTransaction()
	{
		this.id = TransactionIdFactory.nextId();
		log.debug( "Transaction " + this + " created" );
	}

	/**
	 * Check if an action can be made by this transaction.
	 * 
	 * @throws SafeTransactionError If the transaction has been completed (either
	 * by commiting or rolling it back).
	 */
	private void check()
	{
		if ( completed ) {
			throw new TransactionError( "exception.transaction.transactionCompleted" );
		}
	}
	
	
	/**
	 * Make the changes made within this transaction permanent.
	 */
	public void commit()
	{
		check();
		this.completed = true;
		log.debug( "Transaction " + this + ": commited" );
	}

	/**
	 * Cancel the changes made in this transaction.
	 */
	public void abort()
	{
		check();
		this.completed = true;
		log.debug( "Transaction " + this + ": aborted" );
	}

	/**
	 * Get the transaction id. This is unique among the system's transactions.
	 * 
	 * @return The transaction id.
	 */
	public int getId()
	{
		return this.id;
	}


	public String toString()
	{
		return Long.toString( getId() );
	}
}