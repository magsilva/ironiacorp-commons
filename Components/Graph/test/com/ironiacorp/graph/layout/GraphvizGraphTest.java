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

package com.ironiacorp.graph.layout;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.ironiacorp.graph.model.DirectedEdge;
import com.ironiacorp.graph.model.Graph;
import com.ironiacorp.graph.model.Node;
import com.ironiacorp.graph.model.DirectedEdge.NodeType;
import com.ironiacorp.graph.model.Graph.GraphType;

public class GraphvizGraphTest
{
	private GraphvizGraph ggraph;
	
	@Before
	public void setUp() throws Exception {
		ggraph = new GraphvizGraph();
	}

	@Test
	public void testConvert()
	{
		String result;
		Graph graph;
		Node srcNode;
		Node dstNode;
		DirectedEdge edge;
		
		graph = new Graph();
		graph.setType(GraphType.DIRECTED);
		
		// A
		srcNode = new Node();
		srcNode.setId(0);
		srcNode.setLabel("A");
		graph.addElement(srcNode);

		// B
		dstNode = new Node();
		dstNode.setId(1);
		dstNode.setLabel("B");
		graph.addElement(dstNode);
		
		// A -> B
		edge = new DirectedEdge();
		edge.setId(2);
		edge.addNode(srcNode, NodeType.SOURCE);
		edge.addNode(dstNode, NodeType.DEST);
		graph.addElement(edge);

		// C
		dstNode = new Node();
		dstNode.setId(3);
		dstNode.setLabel("C");
		graph.addElement(dstNode);

		// A -> C
		edge = new DirectedEdge();
		edge.setId(4);
		edge.addNode(srcNode, NodeType.SOURCE);
		edge.addNode(dstNode, NodeType.DEST);
		graph.addElement(edge);

		result = ggraph.convert(graph);
		assertEquals("digraph G {\n" +
				"\t{ 0 } -> { 3 };\n" +
				"\t{ 0 } -> { 1 };\n" +
				"\t1 [label=\"B\"];\n" +
				"\t0 [label=\"A\"];\n" +
				"\t3 [label=\"C\"];\n" +
				"}", result);
	}

}
