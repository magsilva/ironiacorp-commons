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

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;

import com.ironiacorp.graph.model.DirectedEdge;
import com.ironiacorp.graph.model.Edge;
import com.ironiacorp.graph.model.Graph;
import com.ironiacorp.graph.model.Node;
import com.ironiacorp.graph.model.DirectedEdge.NodeType;
import com.ironiacorp.graph.model.GraphElement;
import com.ironiacorp.graph.model.Property;


public class JGraphtGraph implements Graph
{
	private DirectedGraph<Node, Edge> directedGraph;
	
	public JGraphtGraph()
	{
		directedGraph = new DefaultDirectedGraph(JGraphtEdge.class);
	}

	public Object getRealGraph()
	{
		return directedGraph;
	}
	
	public void addEdge(Edge e)
	{
		if (e instanceof DirectedEdge) {
			DirectedEdge dedge = (DirectedEdge) e;
			directedGraph.addEdge(
					dedge.getNodes(NodeType.SOURCE).iterator().next(),
					dedge.getNodes(NodeType.DEST).iterator().next()
			);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	public void addEdge(Node e1, Node e2)
	{
		directedGraph.addEdge(e1, e2);
	}

	public void addVertex(Node v)
	{
		 directedGraph.addVertex(v);
	}
	
	public Collection<Edge> getEdges()
	{
		return directedGraph.edgeSet();
	}

	public Set<Node> getVertexes()
	{
		return directedGraph.vertexSet();
	}

	@Override
	public GraphType getType()
	{
		return GraphType.DIRECTED;
	}

	@Override
	public void setType(GraphType type)
	{
		if (type != GraphType.DIRECTED) {
			throw new IllegalArgumentException("The graph type _must_ be directed");
		}
	}

	@Override
	public Set<GraphElement> getElements()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void setElements(Set<GraphElement> elements)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void addElement(GraphElement element)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void removeElement(GraphElement element)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<? extends GraphElement> getElements(Class<? extends GraphElement> elementType)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<Node> getNodes()
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public GraphElement findElement(int id)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<GraphElement> findElementByProperty(String propertyName, Object value)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public Collection<GraphElement> findElementByLabel(String label)
	{
		throw new UnsupportedOperationException();
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
}
