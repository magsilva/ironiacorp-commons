package com.ironiacorp.datastructure.bst;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.ironiacorp.datastructure.bst.BinarySearchTree;
import com.ironiacorp.datastructure.bst.DuplicateItem;
import com.ironiacorp.datastructure.bst.ItemNotFound;

public class TestBinarySearchTree_Functional
{
	private BinarySearchTree tree;

	@Before
	public void setUp() {
		tree = new BinarySearchTree();
	}

	@Test
	public void testCaseInsertion1()  {
		tree.root = tree.insert(Integer.valueOf(1), tree.root);
		tree.root = tree.insert(Integer.valueOf(4), tree.root);
		tree.root = tree.insert(Integer.valueOf(2), tree.root);
		tree.root = tree.insert(Integer.valueOf(3), tree.root);

		assertEquals("3", tree.root.right.left.right.element.toString());

	}

	@Test
	public void testCaseInsertion2() {
		tree.root = tree.insert(Integer.valueOf(1), tree.root);
		tree.root = tree.insert(Integer.valueOf(4), tree.root);
		tree.root = tree.insert(Integer.valueOf(2), tree.root);

		assertEquals("2", tree.root.right.left.element.toString());
	}

	@Test
	public void testCaseInsertion3() {
		tree.root = tree.insert(Integer.valueOf(1), tree.root);
		assertEquals("1", tree.root.element.toString());
	}

	@Test(expected=DuplicateItem.class)
	public void testCaseInsertion4()  {
		tree.root = tree.insert(Integer.valueOf(1), tree.root);
		tree.root = tree.insert(Integer.valueOf(4), tree.root);
		tree.root = tree.insert(Integer.valueOf(1), tree.root);
	}


	@Test
	public void testCaseDeletion1()  {
		tree.root = tree.insert(Integer.valueOf(1), tree.root);
		tree.root = tree.insert(Integer.valueOf(4), tree.root);
		tree.root = tree.insert(Integer.valueOf(2), tree.root);
		tree.root = tree.remove(Integer.valueOf(4), tree.root);

		assertEquals("1", tree.root.element.toString());
		assertEquals("2", tree.root.right.element.toString());
	}

	@Test
	public void testCaseDeletion2() {
		tree.root = tree.insert(Integer.valueOf(1), tree.root);
		tree.root = tree.insert(Integer.valueOf(4), tree.root);
		tree.root = tree.insert(Integer.valueOf(2), tree.root);
		tree.root = tree.remove(Integer.valueOf(2), tree.root);

		assertEquals("1", tree.root.element.toString());
		assertEquals("4", tree.root.right.element.toString());
	}

	@Test(expected=ItemNotFound.class)
	public void testCaseDeletion3() {
		tree.root = tree.insert(Integer.valueOf(1), tree.root);
		tree.root = tree.insert(Integer.valueOf(4), tree.root);
		tree.root = tree.remove(Integer.valueOf(2), tree.root);
	}

	@Test(expected=ItemNotFound.class)
	public void testCaseDeletion4() {
		tree.root = tree.remove(Integer.valueOf(2), tree.root);
	}


	@Test
	public void testCaseSeaching1() {
		Comparable f;
		tree.root = tree.insert(Integer.valueOf(1), tree.root);
		tree.root = tree.insert(Integer.valueOf(4), tree.root);
		tree.root = tree.insert(Integer.valueOf(2), tree.root);
		f = tree.find(Integer.valueOf(2));
		assertEquals("2", f.toString());
	}

	@Test(expected=ItemNotFound.class)
	public void testCaseSearching2() {
		tree.root = tree.insert(Integer.valueOf(1), tree.root);
		tree.root = tree.insert(Integer.valueOf(4), tree.root);
		tree.root = tree.insert(Integer.valueOf(2), tree.root);
		tree.find(Integer.valueOf(3));
	}

	@Test
	public void testCaseSearching3() {
		Comparable f;
		tree.root = tree.insert(Integer.valueOf(1), tree.root);
		tree.root = tree.insert(Integer.valueOf(4), tree.root);
		tree.root = tree.insert(Integer.valueOf(2), tree.root);
		f = tree.findMin();

		assertEquals("1", f.toString());
	}

	@Test(expected=ItemNotFound.class)
	public void testCaseSearching4() {
		tree.findMin();
	}

	@Test
	public void testCaseSearching5() {
		Comparable f;
		tree.root = tree.insert(Integer.valueOf(1), tree.root);
		tree.root = tree.insert(Integer.valueOf(4), tree.root);
		tree.root = tree.insert(Integer.valueOf(2), tree.root);
		f = tree.findMax();

		assertEquals("4", f.toString());
	}

	@Test(expected=ItemNotFound.class)
	public void testCaseSearching6() {
		tree.findMax();
	}

	@Test
	public void testCaseEmptyness1() {
		tree.root = tree.insert(Integer.valueOf(1), tree.root);
		tree.root = tree.insert(Integer.valueOf(4), tree.root);
		tree.root = tree.insert(Integer.valueOf(2), tree.root);

		assertEquals(false, tree.isEmpty());
	}

	@Test
	public void testCaseEmptyness2() {
		assertEquals(true, tree.isEmpty());
	}

	@Test
	public void testCaseEmptyness3() {
		tree.root = tree.insert(Integer.valueOf(1), tree.root);
		tree.root = tree.insert(Integer.valueOf(4), tree.root);
		tree.root = tree.insert(Integer.valueOf(2), tree.root);

		tree.makeEmpty();
		assertEquals(true, tree.isEmpty());
	}
}