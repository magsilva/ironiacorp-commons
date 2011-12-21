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

import java.util.concurrent.atomic.AtomicInteger;

/**
 * The IdFactory generate unique ids for transactions.
 * 
 * @author Marco Aurélio Graciotto Silva
 */
public final class TransactionIdFactory
{
	/**
	 * We really don't want an instance of this class, so we create this
	 * private constructor.
	 */
	private TransactionIdFactory()
	{
	}
	
	/**
	 * Transactions counter.
	 */
	private static final AtomicInteger idFactory = new AtomicInteger( 0 );
	
	/**
	 * Return an unique id for the transaction.
	 */
	public static int nextId()
	{
		return idFactory.incrementAndGet();
	}
}