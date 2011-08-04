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

import java.util.Set;

public interface DirectedEdge extends Edge
{
	public enum NodeType
	{
		SOURCE,
		DEST,
		SOURCE_DEST,
	}
	
	boolean addNode(Node node, NodeType type);

	boolean removeNode(Node node, NodeType type);

	Set<Node> getNodes(NodeType type);
	
	Set<Node> getSourceNodes(NodeType type);
	
	Set<Node> getDestinationNodes(NodeType type);

	NodeType getType(Node node);
}
