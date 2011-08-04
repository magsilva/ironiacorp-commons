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

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.ironiacorp.graph.model.basic.BasicEdge;
import com.ironiacorp.graph.model.basic.BasicNode;

public class EdgeTest extends ElementTest
{
	protected Edge edge;
	
	protected Edge edge2;
	
	@Before
	public void setUp() throws Exception {
		element = new BasicEdge();
		element2 = new BasicEdge();
		
		edge = (BasicEdge) element;
		edge2 = (BasicEdge) element2;
	}

	@Test
	public void testGetNodes_Initialization()
	{
		Set<Node> nodes = edge.getNodes();
		assertNotNull(nodes);
		assertTrue(nodes.isEmpty());
	}
	
	
	@Test
	public void testGetNodes_Initialized()
	{
		Set<Node> nodesBefore;
		Set<Node> nodesAfter;
		Node node = new BasicNode();

		nodesBefore = edge.getNodes();
		assertTrue(edge.addNode(node));
		nodesAfter = edge.getNodes();
		assertSame(nodesBefore, nodesAfter);
		assertEquals(1, nodesBefore.size());
		assertTrue(edge.contains(node));
	}
	
	@Test
	public void testSetNodes_NotNull()
	{
		Set<Node> nodesBefore;
		Set<Node> nodes = new HashSet<Node>();

		nodesBefore = edge.getNodes();
		assertTrue(nodes.add(new BasicNode()));
		edge.setNodes(nodes);
		assertNotSame(nodesBefore, edge.getNodes());
		assertSame(nodes, edge.getNodes());
	}
	
	@Test
	public void testRemoveNode()
	{
		BasicNode node = new BasicNode();
		node.setId(3);
		assertTrue(edge.addNode(node));
		assertTrue(edge.contains(node));
		assertTrue(edge.removeNode(node));
		assertFalse(edge.contains(node));
	}
	
	@Test
	public void testAddNode()
	{
		BasicNode node = new BasicNode();
		node.setId(3);
		assertTrue(edge.addNode(node));
		assertTrue(edge.contains(node));
	}
	
	@Test
	public void testAddNode_DoubleAdd()
	{
		BasicNode node = new BasicNode();
		node.setId(3);
		assertTrue(edge.addNode(node));
		assertFalse(edge.addNode(node));
		assertTrue(edge.contains(node));
	}
	
	@Test
	public void testFindNode()
	{
		BasicNode node = new BasicNode();
		node.setId(3);
		assertTrue(edge.addNode(node));
		assertSame(node, edge.findNode(3));
	}
	
	@Test
	public void testFindNode_NotFound()
	{
		assertNull(edge.findNode(3));
	}
}
