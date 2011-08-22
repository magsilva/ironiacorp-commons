package com.ironiacorp.datastructure.bst;

import java.util.NoSuchElementException;

/**
 * Protocol for search trees. Note that all "matching" is based on the compares
 * method.
 * 
 * @author Mark Allen Weiss
 */
public interface SearchTree<T extends Comparable<T>> {
	/**
	 * Insert into the tree.
	 * 
	 * @param x
	 *            the item to insert.
	 * @exception DuplicateItem
	 *                if an item that matches x is already in the tree.
	 */
	void insert(T x) throws DuplicateItem;

	/**
	 * Remove from the tree.
	 * 
	 * @param x
	 *            the item to remove.
	 * @exception NoSuchElementException
	 *                if no item that matches x can be found in the tree.
	 */
	void remove(T x) throws NoSuchElementException;

	/**
	 * Remove the smallest item from the tree.
	 * 
	 * @exception NoSuchElementException
	 *                if the tree is empty.
	 */
	void removeMin() throws NoSuchElementException;

	/**
	 * Find the smallest item in the tree.
	 * 
	 * @return the smallest item.
	 * @exception NoSuchElementException
	 *                if the tree is empty.
	 */
	T findMin() throws NoSuchElementException;

	/**
	 * Find the largest item the tree.
	 * 
	 * @return the largest item.
	 * @exception NoSuchElementException
	 *                if the tree is empty.
	 */
	T findMax() throws NoSuchElementException;

	/**
	 * Find an item in the tree.
	 * 
	 * @param x
	 *            the item to search for.
	 * @return the matching item.
	 * @exception NoSuchElementException
	 *                if no item that matches x can be found in the tree.
	 */
	T find(T x) throws NoSuchElementException;

	/**
	 * Make the tree logically empty.
	 */
	void makeEmpty();

	/**
	 * Test if the tree is logically empty.
	 * 
	 * @return true if empty, false otherwise.
	 */
	boolean isEmpty();

	/**
	 * Print the tree contents in sorted order.
	 */
	void printTree();
}