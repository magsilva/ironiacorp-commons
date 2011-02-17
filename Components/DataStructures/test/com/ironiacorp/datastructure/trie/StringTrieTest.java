package com.ironiacorp.datastructure.trie;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class StringTrieTest {
	private StringTrie trie;

	@Before
	public void setUp() throws Exception {
		trie = new StringTrie();
		trie.put("123", "456");
		trie.put("Java", "rocks");
		trie.put("Melinda", "too");
		trie.put("Moo", "cow"); // Will collide with "Melinda".
		trie.put("Moon", "walk"); // Collides with "Melinda" and turns "Moo" into a prefix.
		trie.put("", "Root"); // You can store one value at the empty key if you like.
	}

	@Test
	public void testIsEmpty_EmptyTrie() {
		StringTrie emptyTrie = new StringTrie();
		assertTrue(emptyTrie.isEmpty());
	}


	@Test
	public void testIsEmpty() {
		assertFalse(trie.isEmpty());
	}

	@Test
	public void testSize_AsIs() {
		assertEquals(6, trie.size());
	}

	@Test
	public void testSize_AfterModification_Addition() {
		assertEquals(6, trie.size());
		trie.put("a123", "a456");
		trie.put("bJava", "brocks");
		trie.put("cMelinda", "ctoo");
		trie.put("d123", "d456");
		trie.put("eJava", "erocks");
		trie.put("fMelinda", "ftoo");
		assertEquals(12, trie.size());
	}

	@Test
	public void testSize_AfterModification_Removal() {
		assertEquals(6, trie.size());
		trie.put("123", null);
		trie.put("Melinda", null);
		trie.put("Java", null);
		trie.put("Melinda", null);
		assertEquals(2, trie.size());

	}

	@Test
	public void testSize_AfterModification_AdditionRemoval() {
		assertEquals(6, trie.size());
		trie.put("123", null);
		trie.put("Melinda", null);
		trie.put("Java", null);
		trie.put("Melinda", null);
		trie.put("a123", "a456");
		trie.put("bJava", "brocks");
		trie.put("cMelinda", "ctoo");
		trie.put("d123", "d456");
		trie.put("eJava", "erocks");
		trie.put("fMelinda", "ftoo");
		trie.put("gC", "grocks");
		trie.put("hHaskell", "htoo");
		assertEquals(10, trie.size());
	}
	
	@Test
	public void testPut_RemoveLeafEntry() {
		trie.put("123", null); // Removes this leaf entry.
		assertNull(trie.get("123"));
	}

	@Test
	public void testPut_RemovePrefixEntry() {
		trie.put("123", null); // Removes this leaf entry.
		assertNull(trie.get("123"));
		trie.put("Moo", null); // Removes this prefix entry. (Special case to test internal logic).
		assertNull(trie.get("Moo"));
	}

	@Test
	public void testPut_InternalTestOfBranchPruning() {
		trie.put("123", null); // Removes this leaf entry.
		assertNull(trie.get("123"));
		trie.put("Moo", null); // Removes this prefix entry. (Special case to test internal logic).
		assertNull(trie.get("Moo"));
		trie.put("Moon", null); // Internal test of branch pruning.
		assertNull(trie.get("Moon"));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testPut_NullKey() {
		trie.put(null, null); // Removes this leaf entry.
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testPut_InvalidValue() {
		trie.put("123", new StringTrie()); // Removes this leaf entry.
	}
	
	@Test
	public void testGet_Ok_Found() {
		assertEquals("456", trie.get("123"));
		assertEquals("rocks", trie.get("Java"));
		assertEquals("too", trie.get("Melinda"));
		assertEquals("cow", trie.get("Moo"));
		assertEquals("walk", trie.get("Moon"));
	}

	@Test
	public void testGet_Ok_NotFound_ButHasPrefix() {
		assertNull(trie.get("Mo"));
	}

	@Test
	public void testGet_Ok_NotFound_WithoutPrefix() {
		assertNull(trie.get("Baba"));
	}
	@Test
	public void testGet_Ok_NotExistentString() {
		assertNull(trie.get("Moose"));
		assertNull(trie.get("Nothing"));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testGet_Err_NullKey() {
		assertNull(trie.get(null));
	}
}
