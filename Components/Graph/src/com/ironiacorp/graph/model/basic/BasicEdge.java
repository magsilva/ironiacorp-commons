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

import java.util.HashSet;
import java.util.Set;

import com.ironiacorp.graph.model.GraphUtil;
import com.ironiacorp.graph.model.Node;

public class BasicEdge extends BasicGraphElement implements com.ironiacorp.graph.model.Edge
{
	protected Set<Node> nodes;
	
	public BasicEdge()
	{
		nodes = new HashSet<Node>();
	}

	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.Edge2#getNodes()
	 */
	@Override
	public Set<Node> getNodes()
	{
		return nodes;
	}

	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.Edge2#setNodes(java.util.Set)
	 */
	@Override
	public void setNodes(Set<Node> nodes)
	{
		this.nodes = nodes;
	}
	
	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.Edge2#addNode(com.ironiacorp.graph.model.basic.Node)
	 */
	@Override
	public boolean addNode(Node node)
	{
		return nodes.add(node);
	}
	
	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.Edge2#removeNode(com.ironiacorp.graph.model.basic.Node)
	 */
	@Override
	public boolean removeNode(Node node)
	{
		return nodes.remove(node);
	}
	
	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.Edge2#findNode(int)
	 */
	@Override
	public Node findNode(int id)
	{
		for (Node node : nodes) {
			if (node.getId() == id) {
				return node;
			}
		}
		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see com.ironiacorp.graph.model.basic.Edge2#contains(com.ironiacorp.graph.model.basic.Node)
	 */
	@Override
	public boolean contains(Node node)
	{
		return nodes.contains(node);
	}
	
	@Override
	public String toString()
	{
		return GraphUtil.toString(this);
	}
}
