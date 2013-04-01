package com.ironiacorp.datastructure.tree;

public class BinaryTree<T extends Comparable<T>>
{
	class Node<T extends Comparable<T>> {
		T data = null;
		Node<T> left = null;
		Node<T> right = null;
	}

	private Node<T> root;

	public BinaryTree()
	{
		root = null;
	}

	public void insert(T data)
	{
		insert(root, data);

	}

	private Node<T> insert(Node<T> root, T data)
	{
		if (root == null) {
			Node<T> node = new Node<T>();
			node.data = data;
			root = node;
			return node;
		} else {
			Node<T> node = new Node<T>();
			if (root.data.compareTo(data) < 0) {
				node = insert(root.left, data);
				if (root.left == null) {
					root.left = node;
				}
			} else {
				node = insert(root.right, data);
				if (root.right == null) {
					root.right = null;
				}
			}
			return node;
		}
	}

	public boolean has(T data)
	{
		return has(root, data);
	}

	public boolean has(Node<T> root, T data)
	{
		if (root == null) {
			return false;
		}

		if (root.data.compareTo(data) == 0) {
			return true;
		} else {
			if (root.data.compareTo(data) < 0) {
				return has(root.left, data);
			} else {
				return has(root.right, data);
			}
		}
	}

	public void remove(T data)
	{

	}
}