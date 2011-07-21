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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class DirectedEdge extends Edge
{
	public enum NodeType
	{
		SOURCE,
		DEST,
		SOURCE_DEST,
	}
	
	private Map<Node, NodeType> directions;
	
	public DirectedEdge()
	{
		directions = new HashMap<Node, DirectedEdge.NodeType>();
	}

	@Override
	public void setNodes(Set<Node> nodes)
	{
		directions.clear();
		super.setNodes(nodes);
		for (Node node : nodes) {
			directions.put(node, NodeType.SOURCE_DEST);
		}
		
	}

	@Override
	public boolean addNode(Node node)
	{
		boolean result = super.addNode(node);
		directions.put(node, NodeType.SOURCE_DEST);
		
		return result;
	}

	public boolean addNode(Node node, NodeType type)
	{
		if (node == null || type == null) {
			throw new IllegalArgumentException(new NullPointerException());
		}
		
		NodeType originalType = directions.get(node);
		boolean found = super.contains(node);
		boolean result = super.addNode(node);
		if (originalType != NodeType.SOURCE_DEST) {
			if (found == true) {
				if (originalType != type) {
					type = NodeType.SOURCE_DEST;
					directions.put(node, type);
					result |= true;
				}
			} else {
				directions.put(node, type);
				result |= true;
			}
		}

		return result;
	}
	
	@Override
	public boolean removeNode(Node node)
	{
		directions.remove(node);
		return super.removeNode(node);
	}
	
	public boolean removeNode(Node node, NodeType type)
	{
		NodeType originalType = directions.get(node);
		boolean result = false;
		
		if (type == NodeType.SOURCE_DEST || originalType == type) {
			 result = removeNode(node);
		} else {
			if (originalType == NodeType.SOURCE_DEST) {
				if (type == NodeType.SOURCE) {
					directions.put(node,  NodeType.DEST);
				} else {
					directions.put(node,  NodeType.SOURCE);
				}
				result = true;
			} 
		}
		
		return result;
	}
	
	public Set<Node> getNodes(NodeType type)
	{
		Set<Node> result = new HashSet<Node>();
		for (Node node : directions.keySet()) {
			NodeType nodeType = directions.get(node);
			if (nodeType == type || nodeType == NodeType.SOURCE_DEST) {
				result.add(node);
			}
		}
		return result;
	}
	
	public NodeType getType(Node node)
	{
		return directions.get(node);
	}
}
