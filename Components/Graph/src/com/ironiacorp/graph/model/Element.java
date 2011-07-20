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

import java.util.HashMap;
import java.util.Map;

import com.ironiacorp.graph.rendering.RenderingDescription;

/**
 * Graph element. Any element in a graph (nodes, edges, and subgraphs) must
 * derive this class.
 */
public abstract class Element
{
	/**
	 * Unique element id.
	 */
	private int id;
	
	/**
	 * Element label. It also works as an identifier, but we rely mostly on 'id' due
	 * to performance and memory usage.
	 */
	private String label;
	
	/**
	 * Attributes associated with this element. This attributes are related just to
	 * data. Anything related to the presentation/rendering of the graph (position,
	 * color, shape, etc) must be defined using rendering description.
	 */
	private Map<String, Object> attributes;
	
	/**
	 * Rendering related attributes.
	 */
	private RenderingDescription rendering;

	/**
	 * Creates a new element. The default id is zero (it is responsibility of the
	 * application to set an unique id).
	 */
	public Element()
	{
		attributes = new HashMap<String, Object>();
	}
	
	/**
	 * Get the element id.
	 * 
	 * @return Element id.
	 */
	public int getId()
	{
		return id;
	}

	/**
	 * Configure the element id.
	 * 
	 * @param id Element id.
	 */
	public void setId(int id)
	{
		this.id = id;
	}

	/**
	 * Get the label for this element.
	 * 
	 * @return Element label or null if no label has been set.
	 */
	public String getLabel()
	{
		return label;
	}

	/**
	 * Configure the label for the element.
	 * @param label
	 */
	public void setLabel(String label)
	{
		this.label = label;
	}

	/**
	 * Get all the attributes of the element.
	 * 
	 * @return Attributes of the element.
	 */
	public Map<String, Object> getAttributes()
	{
		return attributes;
	}
	
	/**
	 * Get one specific attribute of the element.
	 * 
	 * @param name Attribute name.
	 * @return Attribute value.
	 */
	public Object getAttribute(String name)
	{
		return attributes.get(name);
	}

	/**
	 * Set a whole set of attributes for the element (completely replacing the previous
	 * set).
	 * 
	 * @param attributes New set of attributes.
	 */
	public void setAttributes(Map<String, Object> attributes)
	{
		this.attributes = attributes;
	}
	
	/**
	 * Configure one specific attribute of the element.
	 * 
	 * @param name Attribute name.
	 * @param value Attribute value
	 * @return Previous attribute value (null in not previously set).
	 */
	public Object setAttribute(String name, Object value)
	{
		return attributes.put(name, value);
	}
	
	/**
	 * Get the rendering description of the element.
	 * 
	 * @return Rendering description (can be null if not set).
	 */
	public RenderingDescription getRenderingDescription()
	{
		return rendering;
	}

	/**
	 * Configure the rendering description of the element.
	 * 
	 * @param rendering Rendering description to be used for the element.
	 */
	public void setRenderingDescription(RenderingDescription rendering)
	{
		this.rendering = rendering;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		return equals(obj,false);
	}
	
	/**
	 * Compares an object with an element, but ignoring the attributes if it
	 * is an graph element.
	 * 
	 * @param obj Object to be compared to.
	 * @param ignoreAttributes Whether to ignore the attributes.
	 * @return True if equal, false otherwise.
	 */
	public boolean equals(Object obj, boolean ignoreAttributes)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Element other = (Element) obj;

		if (id != other.id)
			return false;

		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
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
