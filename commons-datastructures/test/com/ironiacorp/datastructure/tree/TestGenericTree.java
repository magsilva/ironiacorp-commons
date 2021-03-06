package com.ironiacorp.datastructure.tree;
/*
 Copyright 2010 Vivin Suresh Paliath
 Distributed under the BSD License
*/



import com.ironiacorp.datastructure.tree.GenericTree;
import com.ironiacorp.datastructure.tree.GenericTreeNode;
import com.ironiacorp.datastructure.tree.GenericTreeTraversalOrderEnum;

import java.util.*;

import org.junit.Test;
import static org.junit.Assert.*;


public class TestGenericTree {
    @Test
    public void testRootIsNullOnNewTreeCreation() {
        GenericTree<String> tree = new GenericTree<String>();
        assertNull(tree.getRoot());
    }

    @Test
    public void testNumberOfNodesIsZeroOnNewTreeCreation() {
        GenericTree<String> tree = new GenericTree<String>();
        assertEquals(tree.getNumberOfNodes(), 0);
    }

    @Test
    public void testIsEmptyIsTrueOnNewTreeCreation() {
        GenericTree<String> tree = new GenericTree<String>();
        assertTrue(tree.isEmpty());
    }

    @Test
    public void testExistsIsFalseOnNewTreeCreation() {
        GenericTree<String> tree = new GenericTree<String>();
        GenericTreeNode<String> nodeToFind = new GenericTreeNode<String>();

        assertFalse(tree.exists(nodeToFind));
    }

    @Test
    public void testFindReturnsNullOnNewTreeCreation() {
        GenericTree<String> tree = new GenericTree<String>();
        GenericTreeNode<String> nodeToFind = new GenericTreeNode<String>();

        assertNull(tree.find(nodeToFind));
    }

    @Test
    public void testPreOrderBuildReturnsNullListOnNewTreeCreation() {
        GenericTree<String> tree = new GenericTree<String>();

        assertNull(tree.build(GenericTreeTraversalOrderEnum.PRE_ORDER));
    }

    @Test
    public void testPostOrderBuildReturnsNullListOnNewTreeCreation() {
        GenericTree<String> tree = new GenericTree<String>();

        assertNull(tree.build(GenericTreeTraversalOrderEnum.POST_ORDER));
    }

    @Test
    public void testPreOrderBuildWithDepthReturnsNullMapOnNewTreeCreation() {
        GenericTree<String> tree = new GenericTree<String>();

        assertNull(tree.buildWithDepth(GenericTreeTraversalOrderEnum.PRE_ORDER));
    }

    @Test
    public void testPostOrderBuildWithDepthReturnsNullMapOnNewTreeCreation() {
        GenericTree<String> tree = new GenericTree<String>();

        assertNull(tree.buildWithDepth(GenericTreeTraversalOrderEnum.POST_ORDER));
    }

    @Test
    public void testToStringReturnsEmptyStringOnNewTreeCreation() {
        GenericTree<String> tree = new GenericTree<String>();

        assertEquals(tree.toString(), "");
    }

    @Test
    public void testToStringWithDepthReturnsEmptyStringOnNewTreeCreation() {
        GenericTree<String> tree = new GenericTree<String>();

        assertEquals(tree.toStringWithDepth(), "");
    }

    @Test
    public void testSetRootGetRoot() {
        GenericTree<String> tree = new GenericTree<String>();
        GenericTreeNode<String> root = new GenericTreeNode<String>();
        tree.setRoot(root);

        assertNotNull(tree.getRoot());
    }

    @Test
    public void testNumberOfNodesIsOneWithNonNullRoot() {
        GenericTree<String> tree = new GenericTree<String>();
        GenericTreeNode<String> root = new GenericTreeNode<String>();
        tree.setRoot(root);

        assertEquals(tree.getNumberOfNodes(), 1);
    }

    @Test
    public void testEmptyIsFalseWithNonNullRoot() {
        GenericTree<String> tree = new GenericTree<String>();
        GenericTreeNode<String> root = new GenericTreeNode<String>();
        tree.setRoot(root);

        assertFalse(tree.isEmpty());
    }

    @Test
    public void testPreOrderBuildListSizeIsOneWithNonNullRoot() {
        GenericTree<String> tree = new GenericTree<String>();
        GenericTreeNode<String> root = new GenericTreeNode<String>("root");
        tree.setRoot(root);

        assertEquals(tree.build(GenericTreeTraversalOrderEnum.PRE_ORDER).size(), 1);
    }

    @Test
    public void testPostOrderBuildListSizeIsOneWithNonNullRoot() {
        GenericTree<String> tree = new GenericTree<String>();
        GenericTreeNode<String> root = new GenericTreeNode<String>("root");
        tree.setRoot(root);

        assertEquals(tree.build(GenericTreeTraversalOrderEnum.POST_ORDER).size(), 1);
    }

    @Test
    public void testPreOrderBuildWithDepthSizeIsOneWithNonNullRoot() {
        GenericTree<String> tree = new GenericTree<String>();
        GenericTreeNode<String> root = new GenericTreeNode<String>("root");
        tree.setRoot(root);

        assertEquals(tree.buildWithDepth(GenericTreeTraversalOrderEnum.PRE_ORDER).size(), 1);
    }

    @Test
    public void testPostOrderBuildWithDepthSizeIsOneWithNonNullRoot() {
        GenericTree<String> tree = new GenericTree<String>();
        GenericTreeNode<String> root = new GenericTreeNode<String>("root");
        tree.setRoot(root);

        assertEquals(tree.buildWithDepth(GenericTreeTraversalOrderEnum.POST_ORDER).size(), 1);
    }

    /*
      Tree looks like:
          A
         / \
        B  C
            \
             D

      For the following tests

     */
    @Test
    public void testNumberOfNodes() {
        GenericTree<String> tree = new GenericTree<String>();

        GenericTreeNode<String> rootA = new GenericTreeNode<String>("A");
        GenericTreeNode<String> childB = new GenericTreeNode<String>("B");
        GenericTreeNode<String> childC = new GenericTreeNode<String>("C");
        GenericTreeNode<String> childD = new GenericTreeNode<String>("D");

        childC.addChild(childD);
        rootA.addChild(childB);
        rootA.addChild(childC);

        tree.setRoot(rootA);

        assertEquals(tree.getNumberOfNodes(), 4);
    }

    @Test
    public void testExistsReturnsTrue() {
        GenericTree<String> tree = new GenericTree<String>();
        GenericTreeNode<String> rootA = new GenericTreeNode<String>("A");
        GenericTreeNode<String> childB = new GenericTreeNode<String>("B");
        GenericTreeNode<String> childC = new GenericTreeNode<String>("C");
        GenericTreeNode<String> childD = new GenericTreeNode<String>("D");

        childC.addChild(childD);
        rootA.addChild(childB);
        rootA.addChild(childC);

        tree.setRoot(rootA);

        GenericTreeNode<String> nodeToFindD = new GenericTreeNode<String>("D");

        assertTrue(tree.exists(nodeToFindD));
    }

    @Test
    public void testFindReturnsNonNull() {
        GenericTree<String> tree = new GenericTree<String>();

        GenericTreeNode<String> rootA = new GenericTreeNode<String>("A");
        GenericTreeNode<String> childB = new GenericTreeNode<String>("B");
        GenericTreeNode<String> childC = new GenericTreeNode<String>("C");
        GenericTreeNode<String> childD = new GenericTreeNode<String>("D");

        childC.addChild(childD);
        rootA.addChild(childB);
        rootA.addChild(childC);

        tree.setRoot(rootA);

        GenericTreeNode<String> nodeToFindD = new GenericTreeNode<String>("D");

        assertNotNull(tree.find(nodeToFindD));
    }

    @Test
    public void testExistsReturnsFalse() {
        GenericTree<String> tree = new GenericTree<String>();

        GenericTreeNode<String> rootA = new GenericTreeNode<String>("A");
        GenericTreeNode<String> childB = new GenericTreeNode<String>("B");
        GenericTreeNode<String> childC = new GenericTreeNode<String>("C");
        GenericTreeNode<String> childD = new GenericTreeNode<String>("D");

        childC.addChild(childD);
        rootA.addChild(childB);
        rootA.addChild(childC);

        tree.setRoot(rootA);

        GenericTreeNode<String> nodeToFindE = new GenericTreeNode<String>("E");

        assertFalse(tree.exists(nodeToFindE));
    }

    @Test
    public void testFindReturnsNull() {
        GenericTree<String> tree = new GenericTree<String>();

        GenericTreeNode<String> rootA = new GenericTreeNode<String>("A");
        GenericTreeNode<String> childB = new GenericTreeNode<String>("B");
        GenericTreeNode<String> childC = new GenericTreeNode<String>("C");
        GenericTreeNode<String> childD = new GenericTreeNode<String>("D");

        childC.addChild(childD);
        rootA.addChild(childB);
        rootA.addChild(childC);

        tree.setRoot(rootA);

        GenericTreeNode<String> nodeToFindE = new GenericTreeNode<String>("E");

        assertNull(tree.find(nodeToFindE));
    }

    // Pre-order traversal will give us A B C D
    @Test
    public void testPreOrderBuild() {
        GenericTree<String> tree = new GenericTree<String>();

        GenericTreeNode<String> rootA = new GenericTreeNode<String>("A");
        GenericTreeNode<String> childB = new GenericTreeNode<String>("B");
        GenericTreeNode<String> childC = new GenericTreeNode<String>("C");
        GenericTreeNode<String> childD = new GenericTreeNode<String>("D");

        childC.addChild(childD);
        rootA.addChild(childB);
        rootA.addChild(childC);

        tree.setRoot(rootA);

        List<GenericTreeNode<String>> preOrderList = new ArrayList<GenericTreeNode<String>>();
        preOrderList.add(new GenericTreeNode<String>("A"));
        preOrderList.add(new GenericTreeNode<String>("B"));
        preOrderList.add(new GenericTreeNode<String>("C"));
        preOrderList.add(new GenericTreeNode<String>("D"));

        // Instead of checking equalities on the lists themselves, we can check equality on the toString's
        // they should generate the same toString's

        assertEquals(tree.build(GenericTreeTraversalOrderEnum.PRE_ORDER).toString(), preOrderList.toString());
    }

    //Post-order traversal will give us B D C A
    @Test
    public void testPostOrderBuild() {
        GenericTree<String> tree = new GenericTree<String>();

        GenericTreeNode<String> rootA = new GenericTreeNode<String>("A");
        GenericTreeNode<String> childB = new GenericTreeNode<String>("B");
        GenericTreeNode<String> childC = new GenericTreeNode<String>("C");
        GenericTreeNode<String> childD = new GenericTreeNode<String>("D");

        childC.addChild(childD);
        rootA.addChild(childB);
        rootA.addChild(childC);

        tree.setRoot(rootA);

        List<GenericTreeNode<String>> postOrderList = new ArrayList<GenericTreeNode<String>>();
        postOrderList.add(new GenericTreeNode<String>("B"));
        postOrderList.add(new GenericTreeNode<String>("D"));
        postOrderList.add(new GenericTreeNode<String>("C"));
        postOrderList.add(new GenericTreeNode<String>("A"));

        // Instead of checking equalities on the lists themselves, we can check equality on the toString's
        // they should generate the same toString's

        assertEquals(tree.build(GenericTreeTraversalOrderEnum.POST_ORDER).toString(), postOrderList.toString());
     }

    //Pre-order traversal with depth will give us A:0, B:1, C:1, D:2
    @Test
    public void testPreOrderBuildWithDepth() {
        GenericTree<String> tree = new GenericTree<String>();

        GenericTreeNode<String> rootA = new GenericTreeNode<String>("A");
        GenericTreeNode<String> childB = new GenericTreeNode<String>("B");
        GenericTreeNode<String> childC = new GenericTreeNode<String>("C");
        GenericTreeNode<String> childD = new GenericTreeNode<String>("D");

        childC.addChild(childD);
        rootA.addChild(childB);
        rootA.addChild(childC);

        tree.setRoot(rootA);

        Map<GenericTreeNode<String>, Integer> preOrderMapWithDepth = new LinkedHashMap<GenericTreeNode<String>, Integer>();
        preOrderMapWithDepth.put(new GenericTreeNode<String>("A"), 0);
        preOrderMapWithDepth.put(new GenericTreeNode<String>("B"), 1);
        preOrderMapWithDepth.put(new GenericTreeNode<String>("C"), 1);
        preOrderMapWithDepth.put(new GenericTreeNode<String>("D"), 2);

        // Instead of checking equalities on the maps themselves, we can check equality on the toString's
        // they should generate the same toString's

        assertEquals(tree.buildWithDepth(GenericTreeTraversalOrderEnum.PRE_ORDER).toString(), preOrderMapWithDepth.toString());
     }

     //Post-order traversal with depth will give us B:1, D:2, C:1, A:0
    @Test
    public void testPostOrderBuildWithDepth() {
        GenericTree<String> tree = new GenericTree<String>();

        GenericTreeNode<String> rootA = new GenericTreeNode<String>("A");
        GenericTreeNode<String> childB = new GenericTreeNode<String>("B");
        GenericTreeNode<String> childC = new GenericTreeNode<String>("C");
        GenericTreeNode<String> childD = new GenericTreeNode<String>("D");

        childC.addChild(childD);
        rootA.addChild(childB);
        rootA.addChild(childC);

        tree.setRoot(rootA);

        Map<GenericTreeNode<String>, Integer> postOrderMapWithDepth = new LinkedHashMap<GenericTreeNode<String>, Integer>();
        postOrderMapWithDepth.put(new GenericTreeNode<String>("B"), 1);
        postOrderMapWithDepth.put(new GenericTreeNode<String>("D"), 2);
        postOrderMapWithDepth.put(new GenericTreeNode<String>("C"), 1);
        postOrderMapWithDepth.put(new GenericTreeNode<String>("A"), 0);

        // Instead of checking equalities on the maps themselves, we can check equality on the toString's
        // they should generate the same toString's

        assertEquals(tree.buildWithDepth(GenericTreeTraversalOrderEnum.POST_ORDER).toString(), postOrderMapWithDepth.toString());
    }

    //toString and toStringWithDepth both use pre-order traversal
    @Test
    public void testToString() {
        GenericTree<String> tree = new GenericTree<String>();

        GenericTreeNode<String> rootA = new GenericTreeNode<String>("A");
        GenericTreeNode<String> childB = new GenericTreeNode<String>("B");
        GenericTreeNode<String> childC = new GenericTreeNode<String>("C");
        GenericTreeNode<String> childD = new GenericTreeNode<String>("D");

        childC.addChild(childD);
        rootA.addChild(childB);
        rootA.addChild(childC);

        tree.setRoot(rootA);

        List<GenericTreeNode<String>> preOrderList = new ArrayList<GenericTreeNode<String>>();
        preOrderList.add(new GenericTreeNode<String>("A"));
        preOrderList.add(new GenericTreeNode<String>("B"));
        preOrderList.add(new GenericTreeNode<String>("C"));
        preOrderList.add(new GenericTreeNode<String>("D"));

        assertEquals(tree.toString(), preOrderList.toString());
    }

    @Test
    public void testToStringWithDepth() {
        GenericTree<String> tree = new GenericTree<String>();

        GenericTreeNode<String> rootA = new GenericTreeNode<String>("A");
        GenericTreeNode<String> childB = new GenericTreeNode<String>("B");
        GenericTreeNode<String> childC = new GenericTreeNode<String>("C");
        GenericTreeNode<String> childD = new GenericTreeNode<String>("D");

        childC.addChild(childD);
        rootA.addChild(childB);
        rootA.addChild(childC);

        tree.setRoot(rootA);

        Map<GenericTreeNode<String>, Integer> preOrderMapWithDepth = new LinkedHashMap<GenericTreeNode<String>, Integer>();
        preOrderMapWithDepth.put(new GenericTreeNode<String>("A"), 0);
        preOrderMapWithDepth.put(new GenericTreeNode<String>("B"), 1);
        preOrderMapWithDepth.put(new GenericTreeNode<String>("C"), 1);
        preOrderMapWithDepth.put(new GenericTreeNode<String>("D"), 2);

        assertEquals(tree.toStringWithDepth(), preOrderMapWithDepth.toString());
    }
}
