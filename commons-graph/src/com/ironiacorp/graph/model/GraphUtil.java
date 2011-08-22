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
import java.util.Iterator;

import com.ironiacorp.graph.model.DirectedEdge.NodeType;

public class GraphUtil
{
	private GraphUtil()
	{
	}
	
	/**
	 * Convert a Graph to a string.
	 * 
	 * @param graph
	 * @return
	 */
	public static String toString(Graph graph)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		
		Collection<Node> nodes = graph.getNodes();
		Iterator<Node> i = nodes.iterator();
		sb.append("{");
		while (i.hasNext()) {
			sb.append(i.next().toString());
			if (i.hasNext()) {
				sb.append(",");
			}
		}
		sb.append("}");
		
		sb.append(", ");
		
		Collection<Edge> edges =  graph.getEdges();
		Iterator<Edge> j = edges.iterator();
		sb.append("{");
		while (j.hasNext()) {
			sb.append(j.next().toString());
			if (j.hasNext()) {
				sb.append(",");
			}
		}
		sb.append("})");
		
		return sb.toString();
	}

	public static String toString(Edge edge)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		for (Node node : edge.getNodes()) {
			sb.append(node.toString());
			sb.append(" - ");
		}
		sb.append(")");
		
		return sb.toString();
	}
	
	public static String toString(DirectedEdge edge)
	{
		StringBuilder sb = new StringBuilder();
		Iterator<? extends Node> i;
		
		sb.append("(");
		i = edge.getNodes(NodeType.SOURCE).iterator();
		while (i.hasNext()) {
			Node node = i.next();
			sb.append(node.toString());
			if (i.hasNext()) {
				sb.append(", ");
			}
		}
		sb.append("->");
		i = edge.getNodes(NodeType.DEST).iterator();
		while (i.hasNext()) {
			Node node = i.next();
			sb.append(node.toString());
			if (i.hasNext()) {
				sb.append(", ");
			}
		}
		sb.append(")");
		
		return sb.toString();
	}

}
