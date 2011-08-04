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

package com.ironiacorp.graph.model.jgrapht;

import java.util.Map;
import java.util.Set;

import org.jgrapht.graph.DefaultEdge;

import com.ironiacorp.graph.model.Edge;
import com.ironiacorp.graph.model.Node;
import com.ironiacorp.graph.model.Property;

public class JGraphtEdge extends DefaultEdge implements Edge
{
	public Node getSource()
	{
		return (Node) super.getSource();
	}

	public Node getDestination()
	{
		return (Node) getDestination();
	}

	@Override
	public int getId()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setId(int id)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setId(String id)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Map<String, Object> getAttributes()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean containsAttribute(String name)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Object getAttribute(String name)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setAttributes(Map<String, Object> attributes)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Object setAttribute(String name, Object value)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Object setAttribute(Property property)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean equals(Object obj, boolean ignoreAttributes)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setNodes(Set<Node> nodes)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean addNode(Node node)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean removeNode(Node node)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean contains(Node node)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Set<Node> getNodes()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Node findNode(int id)
	{
		throw new UnsupportedOperationException();
	}
}
