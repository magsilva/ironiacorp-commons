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

package com.ironiacorp.graph.model.basic;

import java.util.HashMap;
import java.util.Map;

import com.ironiacorp.graph.model.Property;
import com.ironiacorp.graph.model.Property.PropertyType;

/**
 * Graph element. Any element in a graph (nodes, edges, and subgraphs) must
 * derive this class.
 */
public abstract class BasicGraphElement implements com.ironiacorp.graph.model.GraphElement
{
	/**
	 * Unique element id.
	 */
	private int id;
	
	/**
	 * Attributes associated with this element. This attributes are related just to
	 * data. Anything related to the presentation/rendering of the graph (position,
	 * color, shape, etc) must be defined using rendering description.
	 */
	private Map<String, Object> attributes;
	
	/**
	 * Creates a new element. The default id is zero (it is responsibility of the
	 * application to set an unique id).
	 */
	public BasicGraphElement()
	{
		attributes = new HashMap<String, Object>();
	}
	
	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.GraphElement#getId()
	 */
	@Override
	public int getId()
	{
		return id;
	}

	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.GraphElement#setId(int)
	 */
	@Override
	public void setId(int id)
	{
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.GraphElement#setId(int)
	 */
	@Override
	public void setId(String id)
	{
		this.id = id.hashCode();
	}
	
	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.GraphElement#getAttributes()
	 */
	@Override
	public Map<String, Object> getAttributes()
	{
		return attributes;
	}
	
	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.GraphElement#containsAttribute(java.lang.String)
	 */
	@Override
	public boolean containsAttribute(String name)
	{
		return attributes.containsKey(name);
	}

	
	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.GraphElement#getAttribute(java.lang.String)
	 */
	@Override
	public Object getAttribute(String name)
	{
		if (attributes == null) {
			return null;
		}
		return attributes.get(name);
	}

	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.GraphElement#setAttributes(java.util.Map)
	 */
	@Override
	public void setAttributes(Map<String, Object> attributes)
	{
		this.attributes = attributes;
	}
	
	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.GraphElement#setAttribute(java.lang.String, java.lang.Object)
	 */
	@Override
	public Object setAttribute(String name, Object value)
	{
		return attributes.put(name, value);
	}

	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.GraphElement#setAttribute(com.ironiacorp.graph.model.basic.Property)
	 */
	@Override
	public Object setAttribute(Property property)
	{
		return attributes.put(property.getName(), property.getValue());
	}
	
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		String label = (String) getAttribute(PropertyType.LABEL.name);
		
		result = prime * result + id;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.GraphElement#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		return equals(obj, false);
	}
	
	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.GraphElement#equals(java.lang.Object, boolean)
	 */
	@Override
	public boolean equals(Object obj, boolean ignoreAttributes)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicGraphElement other = (BasicGraphElement) obj;

		if (id != other.id)
			return false;

		String label = (String) getAttribute(PropertyType.LABEL.name);
		String objlabel = (String) getAttribute(PropertyType.LABEL.name);

		if (label == null) {
			if (objlabel != null)
				return false;
		} else if (!label.equals(objlabel))
			return false;

		if (! ignoreAttributes) {
			if (attributes == null) {
				if (other.attributes != null)
					return false;
			} else if (!attributes.equals(other.attributes))
				return false;
		}
		
		return true;
	}
}
