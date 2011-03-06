package com.ironiacorp.datastructure.bst;

import java.util.NoSuchElementException;

/**
 * Implements a binary search tree with a findKth method. Note that all
 * "matching" is based on the compares method.
 * 
 * @author Mark Allen Weiss
 */
public class BinarySearchTreeWithRank<T extends Comparable<T>> extends BinarySearchTree<T> {
	/**
	 * Find the kth smallest item in the tree.
	 * 
	 * @param k
	 *            the desired rank (1 is the smallest item).
	 * @return the kth smallest item in the tree.
	 * @exception NoSuchElementException
	 *                if k is less than 1 or more than the size of the tree.
	 */
	public T findKth(int k) throws NoSuchElementException {
		return findKth(k, root).element;
	}

	/**
	 * Internal method to insert into a subtree, adjusting Size fields as
	 * appropriate.
	 * 
	 * @param x
	 *            the item to insert.
	 * @param t
	 *            the node that roots the tree.
	 * @return the new root.
	 * @exception DuplicateItem
	 *                if item that matches x is already in the subtree rooted at
	 *                t.
	 */
	protected BinaryNode<T> insert(T x, BinaryNode<T> t) throws DuplicateItem {
		if (t == null)
			return new BinaryNode<T>(x, null, null);
		else if (x.compareTo(t.element) < 0)
			t.left = insert(x, t.left);
		else if (x.compareTo(t.element) > 0)
			t.right = insert(x, t.right);
		else
			throw new DuplicateItem("BSTWithRank insert");

		t.size++;
		return t;
	}

	/**
	 * Internal method to remove from a subtree, adjusting Size fields as
	 * appropriate.
	 * 
	 * @param x
	 *            the item to remove.
	 * @param t
	 *            the node that roots the tree.
	 * @return the new root.
	 * @exception NoSuchElementException
	 *                no item that matches x is in the subtree rooted at t.
	 */
	protected BinaryNode<T> remove(T x, BinaryNode<T> t) throws NoSuchElementException {
		if (t == null)
			throw new NoSuchElementException("BSTWithRank remove");
		if (x.compareTo(t.element) < 0)
			t.left = remove(x, t.left);
		else if (x.compareTo(t.element) > 0)
			t.right = remove(x, t.right);
		else if (t.left != null && t.right != null) // Two children
		{
			t.element = findMin(t.right).element;
			t.right = removeMin(t.right);
		} else
			return (t.left != null) ? t.left : t.right;
		t.size--;
		return t;
	}

	/**
	 * Internal method to remove the smallest item from a subtree, adjusting
	 * Size fields as appropriate.
	 * 
	 * @param t
	 *            the node that roots the tree.
	 * @return the new root.
	 * @exception NoSuchElementException
	 *                the subtree is empty.
	 */
	protected BinaryNode<T> removeMin(BinaryNode<T> t) throws NoSuchElementException {
		if (t == null)
			throw new NoSuchElementException("BSTWithRank removeMin");
		if (t.left == null)
			return t.right;
		t.left = removeMin(t.left);
		t.size--;
		return t;
	}

	/**
	 * Internal method to find kth smallest item in a subtree.
	 * 
	 * @param k
	 *            the desired rank (1 is the smallest item).
	 * @return the node containing the kth smallest item in the subtree.
	 * @exception NoSuchElementException
	 *                if k is less than 1 or more than the size of the subtree.
	 */
	protected BinaryNode<T> findKth(int k, BinaryNode<T> t) throws NoSuchElementException {
		if (t == null)
			throw new NoSuchElementException("BSTWithRank findKth");
		int leftSize = (t.left != null) ? t.left.size : 0;

		if (k <= leftSize)
			return findKth(k, t.left);
		if (k == leftSize + 1)
			return t;
		return findKth(k - leftSize - 1, t.right);
	}
}