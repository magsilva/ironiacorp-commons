/*

 Copyright 2010 Vivin Suresh Paliath
 Distributed under the BSD License
*/

package com.ironiacorp.datastructure.tree;


import com.ironiacorp.datastructure.tree.GenericTreeNode;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestGenericTreeNode {
    @Test
    public void testNodeDataIsNullOnNewNodeCreation() {
        GenericTreeNode<String> node = new GenericTreeNode<String>();
        assertNull(node.getData());
    }

    @Test
    public void testNodeHasNonNullChildrenListOnNewNodeCreation() {
        GenericTreeNode<String> node = new GenericTreeNode<String>();
        assertNotNull(node.getChildren());
    }

    @Test
    public void testNodeHasZeroChildrenOnNewNodeCreation() {
        GenericTreeNode<String> node = new GenericTreeNode<String>();
        assertEquals(node.getNumberOfChildren(), 0);
    }

    @Test
    public void testNodeHasChildrenReturnsFalseOnNewNodeCreation() {
        GenericTreeNode<String> node = new GenericTreeNode<String>();
        assertFalse(node.hasChildren());
    }

    @Test
    public void testNodeDataIsNonNullWithParameterizedConstructor() {
        GenericTreeNode<String> node = new GenericTreeNode<String>("I haz data");
        assertNotNull(node.getData());
    }

    @Test
    public void testNodeSetAndGetData() {
        GenericTreeNode<String> node = new GenericTreeNode<String>();
        String data = "data";
        node.setData(data);
        assertEquals(node.getData(), data);
    }

    @Test
    public void testNodeSetAndGetChildren() {
        GenericTreeNode<String> node = new GenericTreeNode<String>();
        GenericTreeNode<String> child = new GenericTreeNode<String>();

        List<GenericTreeNode<String>> children = new ArrayList<GenericTreeNode<String>>();
        children.add(child);

        node.setChildren(children);
        assertEquals(node.getChildren(), children);
    }

    @Test
    public void testNodeRemoveChildren() {
        GenericTreeNode<String> node = new GenericTreeNode<String>();
        GenericTreeNode<String> child = new GenericTreeNode<String>();

        List<GenericTreeNode<String>> children = new ArrayList<GenericTreeNode<String>>();
        children.add(child);

        node.setChildren(children);
        node.removeChildren();
        assertEquals(node.getChildren().size(), 0);
    }

    @Test
    public void testNodeAddChildHasOneChild() {
        GenericTreeNode<String> node = new GenericTreeNode<String>();
        GenericTreeNode<String> child = new GenericTreeNode<String>();

        node.addChild(child);
        assertEquals(node.getNumberOfChildren(), 1);
    }

    @Test
    public void testNodeAddChildHasChildrenIsTrue() {
        GenericTreeNode<String> node = new GenericTreeNode<String>();
        GenericTreeNode<String> child = new GenericTreeNode<String>();

        node.addChild(child);
        assertTrue(node.hasChildren());

    }

    @Test
    public void testNodeAddAndGetChildAt() {
        GenericTreeNode<String> node = new GenericTreeNode<String>("root");
        GenericTreeNode<String> child1 = new GenericTreeNode<String>("child1");
        GenericTreeNode<String> child2 = new GenericTreeNode<String>("child2");

        node.addChild(child1);
        node.addChildAt(1, child2);

        assertEquals(node.getChildAt(1).getData(), child2.getData());
    }

    @Test
    public void testNodeAddAndRemoveChildAt() {
        GenericTreeNode<String> node = new GenericTreeNode<String>("root");
        GenericTreeNode<String> child1 = new GenericTreeNode<String>("child1");
        GenericTreeNode<String> child2 = new GenericTreeNode<String>("child2");

        node.addChild(child1);
        node.addChildAt(1, child2);

        node.removeChildAt(0);

        assertEquals(node.getNumberOfChildren(), 1);
    }

    @Test(expected=java.lang.IndexOutOfBoundsException.class)
    public void testNodeAddChildAtThrowsException() {
        GenericTreeNode<String> node = new GenericTreeNode<String>();
        GenericTreeNode<String> child = new GenericTreeNode<String>();

        node.addChildAt(5, child);
    }

    @Test(expected=java.lang.IndexOutOfBoundsException.class)
    public void testNodeRemoveChildAtThrowsException() {
        GenericTreeNode<String> node = new GenericTreeNode<String>();
        node.removeChildAt(1);
    }

    @Test
    public void testNodeToString() {
        GenericTreeNode<String> node = new GenericTreeNode<String>();
        node.setData("data");
        assertEquals(node.toString(), "data");
    }

    @Test
    public void testNodeToStringVerboseNoChildren() {
        GenericTreeNode<String> node = new GenericTreeNode<String>();
        node.setData("data");
        assertEquals(node.toStringVerbose(), "data:[]");
    }

    @Test
    public void testNodeToStringVerboseOneChild() {
        GenericTreeNode<String> node = new GenericTreeNode<String>();
        node.setData("data");

        GenericTreeNode<String> child = new GenericTreeNode<String>();
        child.setData("child");

        node.addChild(child);
        assertEquals(node.toStringVerbose(), "data:[child]");
    }

    @Test
    public void testNodeToStringVerboseMoreThanOneChild() {
        GenericTreeNode<String> node = new GenericTreeNode<String>();
        node.setData("data");

        GenericTreeNode<String> child1 = new GenericTreeNode<String>();
        child1.setData("child1");

        GenericTreeNode<String> child2 = new GenericTreeNode<String>();
        child2.setData("child2");

        node.addChild(child1);
        node.addChild(child2);

        assertEquals(node.toStringVerbose(), "data:[child1, child2]");
    }
}
