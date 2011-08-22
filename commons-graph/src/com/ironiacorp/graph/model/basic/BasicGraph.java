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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.ironiacorp.graph.model.Edge;
import com.ironiacorp.graph.model.Graph;
import com.ironiacorp.graph.model.GraphElement;
import com.ironiacorp.graph.model.GraphUtil;
import com.ironiacorp.graph.model.Node;
import com.ironiacorp.graph.model.Property.PropertyType;

/**
 * Graph.
 */
public class BasicGraph extends BasicGraphElement implements Graph
{
	private GraphType type;
	
	private Set<GraphElement> elements;

	public BasicGraph()
	{
		elements = new HashSet<GraphElement>();
	}
	
	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.Graph2#getType()
	 */
	@Override
	public GraphType getType()
	{
		return type;
	}

	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.Graph2#setType(com.ironiacorp.graph.model.basic.Graph.GraphType)
	 */
	@Override
	public void setType(GraphType type)
	{
		this.type = type;
	}

	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.Graph2#getElements()
	 */
	@Override
	public Set<GraphElement> getElements()
	{
		return elements;
	}

	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.Graph2#setElements(java.util.Set)
	 */
	@Override
	public void setElements(Set<GraphElement> elements)
	{
		this.elements = elements;
	}
	
	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.Graph2#addElement(com.ironiacorp.graph.model.basic.Element)
	 */
	@Override
	public void addElement(GraphElement element)
	{
		elements.add(element);
	}
	
	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.Graph2#removeElement(com.ironiacorp.graph.model.basic.Element)
	 */
	@Override
	public void removeElement(GraphElement element)
	{
		elements.remove(element);
	}
	
	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.Graph2#getElements(java.lang.Class)
	 */
	@Override
	public Collection<? extends GraphElement> getElements(Class<? extends GraphElement> elementType)
	{
		Collection<GraphElement> result = new ArrayList<GraphElement>();
		for (GraphElement element : elements) {
			if (element.getClass().equals(elementType)) {
				result.add(element);
			}
		}

		return result;
	}
	
	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.Graph2#getNodes()
	 */
	@Override
	public Collection<Node> getNodes()
	{
		return (Collection<Node>) getElements(BasicNode.class);
	}

	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.Graph2#getEdges()
	 */
	@Override
	public Collection<Edge> getEdges()
	{
		return (Collection<Edge>) getElements(BasicEdge.class);
	}
	
	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.Graph2#findElement(int)
	 */
	@Override
	public GraphElement findElement(int id)
	{
		for (GraphElement element : elements) {
			if (element.getId() == id) {
				return element;
			}
		}
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.Graph2#findElementByProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public Collection<GraphElement> findElementByProperty(String propertyName, Object value)
	{
		Collection<GraphElement> foundElements = new ArrayList<GraphElement>();
		for (GraphElement element : elements) {
			Object elementValue = element.getAttribute(propertyName);
			if (elementValue != null && elementValue.equals(value)) {
				foundElements.add(element);
			}
		}
		
		return foundElements;
	}
	
	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.Graph2#findElementByLabel(java.lang.String)
	 */
	@Override
	public Collection<GraphElement> findElementByLabel(String label)
	{
		Collection<GraphElement> foundElements = new ArrayList<GraphElement>();
		for (GraphElement element : elements) {
			String elementLabel = (String) element.getAttribute(PropertyType.LABEL.name);
			if (label == elementLabel || (label != null && label.equals(elementLabel))) {
				foundElements.add(element);
			}
		}
		
		return foundElements;
	}
	
	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.Graph2#toString()
	 */
	@Override
	public String toString()
	{
		return GraphUtil.toString(this);
	}
}
