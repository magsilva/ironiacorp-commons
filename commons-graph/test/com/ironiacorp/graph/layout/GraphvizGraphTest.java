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
import org.junit.Ignore;
import org.junit.Test;

import com.ironiacorp.graph.model.Graph;
import com.ironiacorp.graph.model.DirectedEdge.NodeType;
import com.ironiacorp.graph.model.Graph.GraphType;
import com.ironiacorp.graph.model.Property.PropertyType;
import com.ironiacorp.graph.model.basic.BasicDirectedEdge;
import com.ironiacorp.graph.model.basic.BasicGraph;
import com.ironiacorp.graph.model.basic.BasicNode;

public class GraphvizGraphTest
{
	private GraphvizGraph ggraph;
	
	@Before
	public void setUp() throws Exception {
		ggraph = new GraphvizGraph();
	}

	@Ignore
	@Test
	public void testConvert()
	{
		String result;
		Graph graph;
		BasicNode srcNode;
		BasicNode dstNode;
		BasicDirectedEdge edge;
		
		graph = new BasicGraph();
		graph.setType(GraphType.DIRECTED);
		
		// A
		srcNode = new BasicNode();
		srcNode.setId(0);
		srcNode.setAttribute(PropertyType.LABEL.name, "A");
		graph.addElement(srcNode);

		// B
		dstNode = new BasicNode();
		dstNode.setId(1);
		dstNode.setAttribute(PropertyType.LABEL.name, "B");
		graph.addElement(dstNode);
		
		// A -> B
		edge = new BasicDirectedEdge();
		edge.setId(2);
		edge.addNode(srcNode, NodeType.SOURCE);
		edge.addNode(dstNode, NodeType.DEST);
		graph.addElement(edge);

		// C
		dstNode = new BasicNode();
		dstNode.setId(3);
		dstNode.setAttribute(PropertyType.LABEL.name, "C");
		graph.addElement(dstNode);

		// A -> C
		edge = new BasicDirectedEdge();
		edge.setId(4);
		edge.addNode(srcNode, NodeType.SOURCE);
		edge.addNode(dstNode, NodeType.DEST);
		graph.addElement(edge);

		result = ggraph.convert(graph);
		// TODO: Convert those DOT representations to graphs and compare them as such (so that syntatic differences wouldn't fail the assertion)
		// TODO: Reenable this test case after handling that
		assertEquals("digraph G {\n" +
				"\t{ 0 } -> { 3 };\n" +
				"\t{ 0 } -> { 1 };\n" +
				"\t1 [label=\"B\"];\n" +
				"\t0 [label=\"A\"];\n" +
				"\t3 [label=\"C\"];\n" +
				"}", result);
	}

}
