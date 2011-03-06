package com.ironiacorp.datastructure.bst;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import com.ironiacorp.datastructure.bst.BinarySearchTree;
import com.ironiacorp.datastructure.bst.BinarySearchTreeWithRank;
import com.ironiacorp.datastructure.bst.ItemNotFound;

public class TestBinarySearchTree_Structural2 {

	private BinarySearchTree tree;
	private BinarySearchTreeWithRank rankTree;

	@Before
	public void setUp() {
		tree = new BinarySearchTree();
		rankTree = new BinarySearchTreeWithRank();
	}

	@Test
	public void testCase1() { // def-uso da variável sl
		Comparable f;
		rankTree.insert(Integer.valueOf(50));
		rankTree.insert(Integer.valueOf(40));
		rankTree.insert(Integer.valueOf(60));
		rankTree.insert(Integer.valueOf(39));
		rankTree.insert(Integer.valueOf(41));
		rankTree.insert(Integer.valueOf(59));
		rankTree.insert(Integer.valueOf(61));
		rankTree.insert(Integer.valueOf(38));
		f = rankTree.findKth(6);

		assertEquals("59", f.toString());
	}

	@Test(expected=ItemNotFound.class)
	public void testCase2() { // k < 1
		rankTree.insert(Integer.valueOf(50));
		rankTree.findKth(0);
	}

	
	@Test(expected=ItemNotFound.class)
	public void testCase3() { // cobertura do método void removeMin
		rankTree.insert(Integer.valueOf(4));
		rankTree.removeMin();
		rankTree.find(Integer.valueOf(4));
	}

	@Test(expected=ItemNotFound.class)
	public void testCase4() { // cobertura do método void
								// BinarySearchTreeWithRank::removeMin
		rankTree.insert(Integer.valueOf(4));
		rankTree.insert(Integer.valueOf(3));
		rankTree.insert(Integer.valueOf(2));
		rankTree.removeMin();
		rankTree.find(Integer.valueOf(2));
	}

	@Test
	public void testCase5() { // cobertura do método void
								// BinarySearchTree::findMax
		Comparable f;
		tree.insert(Integer.valueOf(1));
		tree.insert(Integer.valueOf(2));
		tree.insert(Integer.valueOf(3));
		tree.insert(Integer.valueOf(4));
		f = tree.findMax();
		assertEquals(Integer.valueOf(4), f);
	}

	@Test
	public void testCase6() { // cobertura do método void
								// BinarySearchTree::findMax
		Comparable f;
		tree.insert(Integer.valueOf(1));
		f = tree.findMax();
		assertEquals(Integer.valueOf(1), f);
	}

	@Test(expected=ItemNotFound.class)
	public void testCase7() { // cobertura do método void
								// BinarySearchTree::remove
		tree.insert(Integer.valueOf(2));
		tree.insert(Integer.valueOf(1));
		tree.insert(Integer.valueOf(3));
		tree.insert(Integer.valueOf(4));
		tree.remove(Integer.valueOf(2));
		tree.find(Integer.valueOf(2));
	}

	@Test(expected=ItemNotFound.class)
	public void testCase8() { // cobertura do método void
								// BinarySearchTree::remove
		tree.insert(Integer.valueOf(1));
		tree.insert(Integer.valueOf(2));
		tree.remove(Integer.valueOf(2));
		tree.find(Integer.valueOf(2));
	}

	@Test(expected=ItemNotFound.class)
	public void testCase9() { // cobertura do método void
								// BinarySearchTree::remove
		tree.insert(Integer.valueOf(1));
		tree.insert(Integer.valueOf(2));
		tree.insert(Integer.valueOf(3));
		tree.remove(Integer.valueOf(2));
		tree.find(Integer.valueOf(2));
	}

	@Test(expected=ItemNotFound.class)
	public void testCase10() { // cobertura do método void
								// BinarySearchTreeWithRank::remove
		rankTree.insert(Integer.valueOf(1));
		rankTree.insert(Integer.valueOf(2));
		rankTree.insert(Integer.valueOf(3));
		rankTree.remove(Integer.valueOf(2));
		rankTree.find(Integer.valueOf(2));
	}

	@Test(expected=ItemNotFound.class)
	public void testCase11() { // cobertura do método void
								// BinarySearchTreeWithRank::remove
		rankTree.insert(Integer.valueOf(2));
		rankTree.insert(Integer.valueOf(1));
		rankTree.insert(Integer.valueOf(5));
		rankTree.insert(Integer.valueOf(4));
		rankTree.insert(Integer.valueOf(3));
		rankTree.remove(Integer.valueOf(2));
		rankTree.find(Integer.valueOf(2));
	}

	@Test(expected=ItemNotFound.class)
	public void testCase12() { // cobertura do método void
								// BinarySearchTree::remove
		tree.insert(Integer.valueOf(1));
		tree.insert(Integer.valueOf(2));
		tree.insert(Integer.valueOf(3));
		tree.remove(Integer.valueOf(2));
		tree.find(Integer.valueOf(2));
	}

	@Test(expected=ItemNotFound.class)
	public void testCase13() { // cobertura do método void
								// BinarySearchTree::remove
		tree.insert(Integer.valueOf(2));
		tree.insert(Integer.valueOf(1));
		tree.insert(Integer.valueOf(5));
		tree.insert(Integer.valueOf(4));
		tree.insert(Integer.valueOf(3));
		tree.remove(Integer.valueOf(2));
		tree.find(Integer.valueOf(2));
	}

	@Test(expected=ItemNotFound.class)
	public void testCase14() { // cobertura do método void
								// BinarySearchTree::remove
		rankTree.insert(Integer.valueOf(2));
		rankTree.insert(Integer.valueOf(1));
		rankTree.insert(Integer.valueOf(3));
		rankTree.insert(Integer.valueOf(4));
		rankTree.remove(Integer.valueOf(2));
		rankTree.find(Integer.valueOf(2));
	}

	@Test(expected=ItemNotFound.class)
	public void testCase15() { // cobertura do método void
								// BinarySearchTree::remove
		rankTree.insert(Integer.valueOf(1));
		rankTree.insert(Integer.valueOf(2));
		rankTree.remove(Integer.valueOf(2));
		rankTree.find(Integer.valueOf(2));
	}

	@Test(expected=ItemNotFound.class)
	public void testCase16() { // cobertura do método void
								// BinarySearchTree::remove
		rankTree.insert(Integer.valueOf(1));
		rankTree.insert(Integer.valueOf(2));
		rankTree.insert(Integer.valueOf(3));
		rankTree.remove(Integer.valueOf(2));
		rankTree.find(Integer.valueOf(2));
	}

	@Test(expected=ItemNotFound.class)
	public void testCase17() { // cobertura do método void
								// BinarySearchTree::remove
		tree.insert(Integer.valueOf(5));
		tree.insert(Integer.valueOf(1));
		tree.insert(Integer.valueOf(8));
		tree.insert(Integer.valueOf(6));
		tree.insert(Integer.valueOf(7));
		tree.remove(Integer.valueOf(5));
		tree.find(Integer.valueOf(5));
	}
}