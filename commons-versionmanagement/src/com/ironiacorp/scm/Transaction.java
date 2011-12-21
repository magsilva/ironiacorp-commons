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

Copyright (C) 2005 Marco Aurélio Graciotto Silva <magsilva@gmail.com>
*/

package com.ironiacorp.scm;

/**
 * TransactionIF interface. It's an abstraction for an application transaction
 * that can span across several (but not too many) requests.

 */
public interface Transaction
{
	/**
	 * Make the changes made within this transaction permanent.
	 */
	void commit();
	
	/**
	 * Cancel the changes made in this transaction.
	 */
	void abort();

	/**
	 * Get the transaction id. This is unique among the system's transactions.
	 * 
	 * @return The transaction id.
	 */
	int getId();

	/**
	 * Check if the transaction has completed (committed or aborted).
	 * 
	 * @return True if the transaction has completed, false otherwise.
	 */
	TransactionStatus getStatus();
}