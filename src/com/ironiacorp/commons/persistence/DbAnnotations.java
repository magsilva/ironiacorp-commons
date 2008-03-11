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

package com.ironiacorp.commons.persistence;

import java.lang.annotation.Annotation;


public final class DbAnnotations
{
	/**
	 * We really don't want an instance of this class, so we create this
	 * private constructor.
	 */
	private DbAnnotations()
	{
	}
	
	public static Class<? extends Annotation> IDENTIFICATOR_ANNOTATION = Identificator.class;
	
	public static Class<? extends Annotation> PROPERTY_ANNOTATION = Property.class;
	
	public static Class<? extends Annotation> TABLE_ANNOTATION = Table.class;
}
