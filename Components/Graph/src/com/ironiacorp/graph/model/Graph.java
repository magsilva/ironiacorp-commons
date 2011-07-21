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

import java.util.HashSet;
import java.util.Set;

/**
 * Graph.
 */
public class Graph extends Element
{
	/**
	 * A graph can be either directed or undirected.
	 */
	public enum GraphType
	{
		DIRECTED,
		UNDIRECTED
	}
	
	private GraphType type;
	
	private Set<Element> elements;

	public Graph()
	{
		elements = new HashSet<Element>();
	}
	
	public GraphType getType()
	{
		return type;
	}

	public void setType(GraphType type)
	{
		this.type = type;
	}

	public Set<Element> getElements()
	{
		return elements;
	}

	public void setElements(Set<Element> elements)
	{
		this.elements = elements;
	}
	
	public void addElement(Element element)
	{
		elements.add(element);
	}
	
	public void removeElement(Element element)
	{
		elements.remove(element);
	}

	public Element findElement(int id)
	{
		for (Element element : elements) {
			if (element.getId() == id) {
				return element;
			}
		}
		
		return null;
	}
}
