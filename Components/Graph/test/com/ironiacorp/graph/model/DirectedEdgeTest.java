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

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.ironiacorp.graph.model.DirectedEdge.NodeType;
import com.ironiacorp.graph.model.basic.BasicDirectedEdge;
import com.ironiacorp.graph.model.basic.BasicNode;


public class DirectedEdgeTest extends EdgeTest
{
	private DirectedEdge dedge;
	
	private DirectedEdge dedge2;
	
	
	@Before
	public void setUp() throws Exception {
		element = new BasicDirectedEdge();
		element2 = new BasicDirectedEdge();
		
		edge = (Edge) element;
		edge2 = (Edge) element2;
		
		dedge = (BasicDirectedEdge) element;
		dedge2 = (BasicDirectedEdge) element2;
	}

	@Test
	public void addNode_CheckType()
	{
		Node node = new BasicNode();
		assertTrue(dedge.addNode(node));
		assertEquals(NodeType.SOURCE_DEST, dedge.getType(node));
	}
	
	@Test
	public void addNode_SOURCE()
	{
		Node node = new BasicNode();
		assertTrue(dedge.addNode(node, NodeType.SOURCE));
		assertEquals(NodeType.SOURCE, dedge.getType(node));
	}
	
	@Test
	public void addNode_DEST()
	{
		BasicNode node = new BasicNode();
		assertTrue(dedge.addNode(node, NodeType.DEST));
		assertEquals(NodeType.DEST, dedge.getType(node));
	}

	@Test
	public void addNode_DoubleAddSameType()
	{
		BasicNode node = new BasicNode();
		assertTrue(dedge.addNode(node, NodeType.DEST));
		assertFalse(dedge.addNode(node, NodeType.DEST));
		assertEquals(NodeType.DEST, dedge.getType(node));
	}

	@Test
	public void addNode_DoubleAddDifferentType1()
	{
		BasicNode node = new BasicNode();
		assertTrue(dedge.addNode(node, NodeType.DEST));
		assertTrue(dedge.addNode(node, NodeType.SOURCE));
		assertEquals(NodeType.SOURCE_DEST, dedge.getType(node));
	}

	@Test
	public void addNode_DoubleAddDifferentType2()
	{
		BasicNode node = new BasicNode();
		assertTrue(dedge.addNode(node, NodeType.DEST));
		assertTrue(dedge.addNode(node, NodeType.SOURCE_DEST));
		assertEquals(NodeType.SOURCE_DEST, dedge.getType(node));
	}

	
	@Test
	public void addNode_DoubleAddDifferentType_NoModification1()
	{
		BasicNode node = new BasicNode();
		assertTrue(dedge.addNode(node, NodeType.SOURCE_DEST));
		assertFalse(dedge.addNode(node, NodeType.SOURCE));
		assertEquals(NodeType.SOURCE_DEST, dedge.getType(node));
	}

	@Test
	public void addNode_DoubleAddDifferentType_NoModification2()
	{
		BasicNode node = new BasicNode();
		assertTrue(dedge.addNode(node, NodeType.SOURCE_DEST));
		assertFalse(dedge.addNode(node, NodeType.SOURCE_DEST));
		assertEquals(NodeType.SOURCE_DEST, dedge.getType(node));
	}
	
	@Test
	public void addNode_SOURCE_DEST()
	{
		BasicNode node = new BasicNode();
		assertTrue(dedge.addNode(node, NodeType.SOURCE_DEST));
		assertEquals(NodeType.SOURCE_DEST, dedge.getType(node));
	}
	
	@Test
	public void addNode_SOURCE_then_DEST()
	{
		BasicNode node = new BasicNode();
		assertTrue(dedge.addNode(node, NodeType.SOURCE));
		assertTrue(dedge.addNode(node, NodeType.DEST));
		assertEquals(NodeType.SOURCE_DEST, dedge.getType(node));
	}
	
	@Test
	public void addNode_DEST_then_SOURCE()
	{
		BasicNode node = new BasicNode();
		assertTrue(dedge.addNode(node, NodeType.DEST));
		assertTrue(dedge.addNode(node, NodeType.SOURCE));
		assertEquals(NodeType.SOURCE_DEST, dedge.getType(node));
	}

	@Test
	public void addNode_SOURCE_DEST_then_DEST()
	{
		BasicNode node = new BasicNode();
		assertTrue(dedge.addNode(node, NodeType.SOURCE_DEST));
		assertFalse(dedge.addNode(node, NodeType.DEST));
		assertEquals(NodeType.SOURCE_DEST, dedge.getType(node));
	}
	
	@Test
	public void addNode_SOURCE_DEST_then_SOURCE()
	{
		BasicNode node = new BasicNode();
		assertTrue(dedge.addNode(node, NodeType.SOURCE_DEST));
		assertFalse(dedge.addNode(node, NodeType.SOURCE));
		assertEquals(NodeType.SOURCE_DEST, dedge.getType(node));
	}

	@Test
	public void removeNode_NotFound()
	{
		BasicNode node = new BasicNode();
		assertFalse(dedge.removeNode(node, NodeType.SOURCE));
	}

	@Test
	public void removeNode_SOURCE()
	{
		BasicNode node = new BasicNode();
		assertTrue(dedge.addNode(node, NodeType.SOURCE));
		assertTrue(dedge.removeNode(node, NodeType.SOURCE));
		assertFalse(dedge.contains(node));
	}
	
	@Test
	public void removeNode_DEST()
	{
		BasicNode node = new BasicNode();
		assertTrue(dedge.addNode(node, NodeType.DEST));
		assertTrue(dedge.removeNode(node, NodeType.DEST));
		assertFalse(dedge.contains(node));
	}
	
	@Test
	public void removeNode_SOURCE_DEST()
	{
		BasicNode node = new BasicNode();
		assertTrue(dedge.addNode(node, NodeType.SOURCE_DEST));
		assertTrue(dedge.removeNode(node, NodeType.SOURCE_DEST));
		assertFalse(dedge.contains(node));
	}
	
	@Test
	public void removeNode_SOURCE_DEST_from_SOURCE()
	{
		BasicNode node = new BasicNode();
		assertTrue(dedge.addNode(node, NodeType.SOURCE_DEST));
		assertTrue(dedge.removeNode(node, NodeType.SOURCE));
		assertTrue(dedge.contains(node));
		assertEquals(NodeType.DEST, dedge.getType(node));
	}
	
	@Test
	public void removeNode_SOURCE_DEST_from_DEST()
	{
		BasicNode node = new BasicNode();
		assertTrue(dedge.addNode(node, NodeType.SOURCE_DEST));
		assertTrue(dedge.removeNode(node, NodeType.DEST));
		assertTrue(dedge.contains(node));
		assertEquals(NodeType.SOURCE, dedge.getType(node));
	}
	
	@Test
	public void removeNode_SOURCE_DEST_then_SOURCE_DEST()
	{
		Node node = new BasicNode();
		assertTrue(dedge.addNode(node, NodeType.SOURCE_DEST));
		assertTrue(dedge.removeNode(node, NodeType.SOURCE_DEST));
		assertFalse(dedge.contains(node));
	}
	
	@Test
	public void getNodes_SOURCE()
	{
		Node node1 = new BasicNode();
		Node node2 = new BasicNode();
		Node node3 = new BasicNode();
		node1.setId(1);
		node2.setId(2);
		node3.setId(3);
		assertTrue(dedge.addNode(node1, NodeType.SOURCE));
		assertTrue(dedge.addNode(node2, NodeType.DEST));
		assertTrue(dedge.addNode(node3, NodeType.SOURCE_DEST));
		Set<Node> nodes = dedge.getNodes(NodeType.SOURCE);
		assertEquals(2, nodes.size());
	}
	
	@Test
	public void getNodes_NO_SOURCE()
	{
		Node node1 = new BasicNode();
		Node node2 = new BasicNode();
		Node node3 = new BasicNode();
		node1.setId(1);
		node2.setId(2);
		node3.setId(3);
		assertTrue(dedge.addNode(node2, NodeType.DEST));
		assertTrue(dedge.addNode(node3, NodeType.SOURCE_DEST));
		Set<Node> nodes = dedge.getNodes(NodeType.SOURCE);
		assertEquals(1, nodes.size());
	}

	@Test
	public void getNodes_NO_SOURCE_Only()
	{
		Node node1 = new BasicNode();
		Node node2 = new BasicNode();
		Node node3 = new BasicNode();
		node1.setId(1);
		node2.setId(2);
		node3.setId(3);
		assertTrue(dedge.addNode(node2, NodeType.DEST));
		Set<Node> nodes = dedge.getNodes(NodeType.SOURCE);
		assertEquals(0, nodes.size());
	}

	
	@Test
	public void getNodes_SOURCE_ONLY()
	{
		Node node1 = new BasicNode();
		Node node2 = new BasicNode();
		Node node3 = new BasicNode();
		node1.setId(1);
		node2.setId(2);
		node3.setId(3);
		assertTrue(dedge.addNode(node1, NodeType.SOURCE));
		assertTrue(dedge.addNode(node2, NodeType.DEST));
		Set<Node> nodes = dedge.getNodes(NodeType.SOURCE);
		assertEquals(1, nodes.size());
	}
	
	@Test
	public void getNodes_DEST()
	{
		Node node1 = new BasicNode();
		Node node2 = new BasicNode();
		Node node3 = new BasicNode();
		node1.setId(1);
		node2.setId(2);
		node3.setId(3);
		assertTrue(dedge.addNode(node1, NodeType.SOURCE));
		assertTrue(dedge.addNode(node2, NodeType.DEST));
		assertTrue(dedge.addNode(node3, NodeType.SOURCE_DEST));
		Set<Node> nodes = dedge.getNodes(NodeType.DEST);
		assertEquals(2, nodes.size());
	}
	
	@Test
	public void getNodes_DEST_ONLY()
	{
		Node node1 = new BasicNode();
		Node node2 = new BasicNode();
		Node node3 = new BasicNode();
		node1.setId(1);
		node2.setId(2);
		node3.setId(3);
		assertTrue(dedge.addNode(node1, NodeType.SOURCE));
		assertTrue(dedge.addNode(node2, NodeType.DEST));
		Set<Node> nodes = dedge.getNodes(NodeType.DEST);
		assertEquals(1, nodes.size());
	}
	
	@Test
	public void getNodes_SOURCE_DEST()
	{
		Node node1 = new BasicNode();
		Node node2 = new BasicNode();
		Node node3 = new BasicNode();
		node1.setId(1);
		node2.setId(2);
		node3.setId(3);
		assertTrue(dedge.addNode(node1, NodeType.SOURCE));
		assertTrue(dedge.addNode(node2, NodeType.DEST));
		assertTrue(dedge.addNode(node3, NodeType.SOURCE_DEST));
		Set<Node> nodes = dedge.getNodes(NodeType.SOURCE_DEST);
		assertEquals(1, nodes.size());
	}
}
