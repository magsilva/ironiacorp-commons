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

public class Edge extends Element
{
	protected Set<Node> nodes;
	
	public Edge()
	{
		nodes = new HashSet<Node>();
	}

	public Set<Node> getNodes()
	{
		return nodes;
	}

	public void setNodes(Set<Node> nodes)
	{
		this.nodes = nodes;
	}
	
	public boolean addNode(Node node)
	{
		return nodes.add(node);
	}
	
	public boolean removeNode(Node node)
	{
		return nodes.remove(node);
	}
	
	public Node findNode(int id)
	{
		for (Node node : nodes) {
			if (node.getId() == id) {
				return node;
			}
		}
		
		return null;
	}
}
