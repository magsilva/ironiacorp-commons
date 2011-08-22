package com.ironiacorp.datastructure.bst;

import java.util.NoSuchElementException;

/**
 * Implements an unbalanced binary search tree.
 * 
 * @author Mark Allen Weiss
 */
public class BinarySearchTree<T extends Comparable<T>> implements SearchTree<T>
{
	/**
	 * The tree root.
	 */
	protected BinaryNode<T> root;

	/**
	 * Construct the tree.
	 */
	public BinarySearchTree() {
		root = null;
	}

	/**
	 * Insert into the tree.
	 * 
	 * @param x
	 *            the item to insert.
	 * @exception DuplicateItem
	 *                if an item that matches x is already in the tree.
	 */
	public void insert(T x) throws DuplicateItem {
		root = insert(x, root);
	}

	/**
	 * Remove from the tree.
	 * 
	 * @param x
	 *            the item to remove.
	 * @exception NoSuchElementException
	 *                if no item that matches x can be found in the tree.
	 */
	public void remove(T x) throws NoSuchElementException {
		root = remove(x, root);
	}

	/**
	 * Remove the smallest item from the tree.
	 * 
	 * @exception NoSuchElementException
	 *                if the tree is empty.
	 */
	public void removeMin() throws NoSuchElementException {
		root = removeMin(root);
	}

	/**
	 * Find the smallest item in the tree.
	 * 
	 * @return smallest item.
	 * @exception NoSuchElementException
	 *                if the tree is empty.
	 */
	public T findMin() throws NoSuchElementException {
		return findMin(root).element;
	}

	/**
	 * Find the largest item in the tree.
	 * 
	 * @return the largest item.
	 * @exception NoSuchElementException
	 *                if tree is empty.
	 */
	public T findMax() throws NoSuchElementException {
		return findMax(root).element;
	}

	/**
	 * Find an item in the tree.
	 * 
	 * @param x
	 *            the item to search for.
	 * @return the matching item.
	 * @exception NoSuchElementException
	 *                if no item that matches x can be found in the tree.
	 */
	public T find(T x) throws NoSuchElementException {
		return find(x, root).element;
	}

	/**
	 * Make the tree logically empty.
	 */
	public void makeEmpty() {
		root = null;
	}

	/**
	 * Test if the tree is logically empty.
	 * 
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		return root == null;
	}

	/**
	 * Print the tree contents in sorted order.
	 */
	public void printTree() {
		if (root == null)
			System.out.println("Empty tree");
		else
			printTree(root);
	}

	/**
	 * Internal method to insert into a subtree.
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
			t = new BinaryNode<T>(x, null, null);
		else if (x.compareTo(t.element) < 0)
			t.left = insert(x, t.left);
		else if (x.compareTo(t.element) > 0)
			t.right = insert(x, t.right);
		else
			throw new DuplicateItem("SearchTree insert");
		return t;
	}

	/**
	 * Internal method to remove from a subtree.
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
			throw new NoSuchElementException("SearchTree remove");
		if (x.compareTo(t.element) < 0)
			t.left = remove(x, t.left);
		else if (x.compareTo(t.element) > 0)
			t.right = remove(x, t.right);
		else if (t.left != null && t.right != null) // Two children
		{
			t.element = findMin(t.right).element;
			t.right = removeMin(t.right);
		} else
			t = (t.left != null) ? t.left : t.right;
		return t;
	}

	/**
	 * Internal method to remove the smallest item from a subtree.
	 * 
	 * @param t
	 *            the node that roots the tree.
	 * @return the new root.
	 * @exception NoSuchElementException
	 *                the subtree is empty.
	 */
	protected BinaryNode<T> removeMin(BinaryNode<T> t) throws NoSuchElementException {
		if (t == null)
			throw new NoSuchElementException("SearchTree removeMin");
		if (t.left != null)
			t.left = removeMin(t.left);
		else
			t = t.right;
		return t;
	}

	/**
	 * Internal method to find the smallest item in a subtree.
	 * 
	 * @param t
	 *            the node that roots the tree.
	 * @return node containing the smallest item.
	 * @exception NoSuchElementException
	 *                the subtree is empty.
	 */
	protected BinaryNode<T> findMin(BinaryNode<T> t) throws NoSuchElementException {
		if (t == null)
			throw new NoSuchElementException("SearchTree findMin");

		while (t.left != null)
			t = t.left;
		return t;
	}

	/**
	 * Internal method to find the largest item in a subtree.
	 * 
	 * @param t
	 *            the node that roots the tree.
	 * @return node containing the largest item.
	 * @exception NoSuchElementException
	 *                the subtree is empty.
	 */
	protected BinaryNode<T> findMax(BinaryNode<T> t) throws NoSuchElementException {
		if (t == null)
			throw new NoSuchElementException("SearchTree findMax");

		while (t.right != null)
			t = t.right;
		return t;
	}

	/**
	 * Internal method to find an item in a subtree.
	 * 
	 * @param x
	 *            is item to search for.
	 * @param t
	 *            the node that roots the tree.
	 * @return node containing the matched item.
	 * @exception NoSuchElementException
	 *                the item is not in the subtree.
	 */
	protected BinaryNode<T> find(T x, BinaryNode<T> t) throws NoSuchElementException {
		while (t != null)
			if (x.compareTo(t.element) < 0)
				t = t.left;
			else if (x.compareTo(t.element) > 0)
				t = t.right;
			else
				return t; // Match

		throw new NoSuchElementException("SearchTree find");
	}

	/**
	 * Internal method to print a subtree in sorted order.
	 * 
	 * @param t
	 *            the node that roots the tree.
	 */
	protected void printTree(BinaryNode<T> t) {
		if (t != null) {
			printTree(t.left);
			System.out.println(t.element.toString());
			printTree(t.right);
		}
	}
}