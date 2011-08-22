package com.ironiacorp.datastructure.bst;

/**
 * Basic node stored in all binary search trees. Includes fields for all
 * variations.
 */
class BinaryNode<T extends Comparable<T>>
{
	/**
	 * Data of the node.
	 */
	T element;
	
	/**
	 * Left child.
	 */
	BinaryNode<T> left;
	
	/**
	 * Right child.
	 */
	BinaryNode<T> right; // Right child

	/**
	 * Balancing information;
	 */
	int size = 1;

	/**
	 * Create a node with no child.
	 * 
	 * @param element Element to be added
	 */
	BinaryNode(T element) {
		this(element, null, null);
	}

	/**
	 * Create a node.
	 * 
	 * @param element Element to be added.
	 * @param leftNode Left child node.
	 * @param rightNode Right child node.
	 */
	BinaryNode(T element, BinaryNode<T> leftNode, BinaryNode<T> rightNode) {
		this.element = element;
		left = leftNode;
		right = rightNode;
	}
}
