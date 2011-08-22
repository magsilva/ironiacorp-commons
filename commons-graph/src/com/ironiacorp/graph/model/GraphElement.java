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

package com.ironiacorp.graph.model;

import java.util.Map;


public interface GraphElement
{

	/**
	 * Get the element id.
	 * 
	 * @return Element id.
	 */
	int getId();

	/**
	 * Configure the element id.
	 * 
	 * @param id Element id.
	 */
	void setId(int id);
	
	/**
	 * Configure the element id. It will derive a number using the
	 * String.
	 * 
	 * @param id Element id.
	 */
	void setId(String id);


	/**
	 * Get all the attributes of the element.
	 * 
	 * @return Attributes of the element.
	 */
	Map<String, Object> getAttributes();

	/**
	 * Get one specific attribute of the element.
	 * 
	 * @param name Attribute name.
	 * @return Attribute value.
	 */
	boolean containsAttribute(String name);

	/**
	 * Get one specific attribute of the element.
	 * 
	 * @param name Attribute name.
	 * @return Attribute value.
	 */
	Object getAttribute(String name);

	/**
	 * Set a whole set of attributes for the element (completely replacing the previous
	 * set).
	 * 
	 * @param attributes New set of attributes.
	 */
	void setAttributes(Map<String, Object> attributes);

	/**
	 * Configure one specific attribute of the element.
	 * 
	 * @param name Attribute name.
	 * @param value Attribute value
	 * @return Previous attribute value (null in not previously set).
	 */
	Object setAttribute(String name, Object value);

	/**
	 * Configure one specific attribute of the element.
	 * 
	 * @param property Property to be set.
	
	 * @return Previous attribute value (null in not previously set).
	 */
	Object setAttribute(Property property);


	/**
	 * Compares an object with an element, but ignoring the attributes if it
	 * is an graph element.
	 * 
	 * @param obj Object to be compared to.
	 * @param ignoreAttributes Whether to ignore the attributes.
	 * @return True if equal, false otherwise.
	 */
	boolean equals(Object obj, boolean ignoreAttributes);
}
