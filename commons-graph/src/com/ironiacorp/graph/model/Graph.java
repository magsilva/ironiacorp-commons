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

import java.util.Collection;
import java.util.Set;

public interface Graph extends GraphElement
{
	/**
	 * A graph can be either directed or undirected.
	 */
	public enum GraphType
	{
		DIRECTED,
		UNDIRECTED
	}
	
	GraphType getType();

	void setType(GraphType type);

	Set<GraphElement> getElements();

	void setElements(Set<GraphElement> elements);

	void addElement(GraphElement element);

	void removeElement(GraphElement element);

	Collection<? extends GraphElement> getElements(Class<? extends GraphElement> elementType);

	Collection<Node> getNodes();

	Collection<Edge> getEdges();

	GraphElement findElement(int id);

	Collection<GraphElement> findElementByProperty(String propertyName, Object value);

	Collection<GraphElement> findElementByLabel(String label);

	String toString();
}
