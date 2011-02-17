package com.ironiacorp.datastructure.trie;

/**
 * Implements very fast dictionary storage and retrieval. Only depends upon the
 * core String class.
 * 
 * Implementation of a trie tree. (see http://en.wikipedia.org/wiki/Trie) though
 * I made it faster and more compact for long key strings by building tree nodes
 * only as needed to resolve collisions. Each letter of a key is the index into
 * the following array. Values stored in the array are either a Leaf containing
 * the user's value or another Trie node if more than one key shares the key
 * prefix up to that point. Null elements indicate unused, I.E. available slots.
 * 
 * 
 * @author Melinda Green - © 2010 Superliminal Software. Free for all uses with
 *         attribution.
 */
public class StringTrie {
	
	private Object[] mChars = new Object[256];
	
	private Object mPrefixVal; // Used only for values of prefix keys.
	
	private int size;

	// Simple container for a string-value pair.
	private static class Leaf {
		public String mStr;
		public Object mVal;

		public Leaf(String str, Object val) {
			mStr = str;
			mVal = val;
		}
	}

	public StringTrie() {
	}

	public boolean isEmpty() {
		if (mPrefixVal != null) {
			return false;
		}
		for (Object o : mChars) {
			if (o != null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Inserts a key/value pair.
	 * 
	 * @param key
	 *            may be empty or contain low-order chars 0..255 but must not be
	 *            null.
	 * @param val
	 *            Your data. Any data class except another Trie. Null values
	 *            erase entries.
	 */
	public void put(String key, Object val) {
		if (key == null) {
			throw new IllegalArgumentException("Invalid key");
		}
		if (val instanceof StringTrie) {
			throw new IllegalArgumentException("Value cannot be of the the StringTrie");
		}

		// All of the original key's chars have been nibbled away which means
		// this node will store this key as a prefix of other keys.
		if (key.length() == 0) {
			mPrefixVal = val; // Note: possibly removes or updates an item.
			size++;
			return;
		}
		
		char c = key.charAt(0);
		Object cObj = mChars[c];
		
		// Unused slot means no collision so just store and return;
		if (cObj == null) {
			// Don't create a leaf to store a null value.
			if (val == null) {
				return;
			}
			mChars[c] = new Leaf(key, val);
			size++;
			return;
		}
		
		if (cObj instanceof StringTrie) {
			// Collided with an existing sub-branch so nibble a char and recurse.
			StringTrie childTrie = (StringTrie) cObj;
			childTrie.put(key.substring(1), val);
			size++;
			// put() must have erased final entry so prune branch.
			if (val == null) {
				size--;
				size--;
				if (childTrie.isEmpty()) {
					mChars[c] = null;
				}
			}
			return;
		}
		
		// Sprout a new branch to hold the colliding items.
		if (cObj instanceof Leaf) {
			// Collided with a leaf
			if (val == null) {
				// Null value means to remove any previously stored value.
				mChars[c] = null;
				size--;
				return;
			}
			
			Leaf cLeaf = (Leaf) cObj;
			StringTrie branch = new StringTrie();
			branch.put(key.substring(1), val); // Store new value in new subtree.
			size++;
			branch.put(cLeaf.mStr.substring(1), cLeaf.mVal); // Plus the one we collided with.
			mChars[c] = branch;
		}
	}

	/**
	 * Retrieve a value for a given key or null if not found.
	 */
	public Object get(String key) {
		if (key == null) {
			throw new IllegalArgumentException("Invalid key");
		}

		if (key.length() == 0) {
			// All of the original key's chars have been nibbled away
			// which means this key is a prefix of another.
			return mPrefixVal;
		}
		char c = key.charAt(0);
		Object cVal = mChars[c];
		if (cVal == null) {
			return null; // Not found.
		}

		if (cVal instanceof StringTrie) {
			// Hash collision. Nibble first char, and recurse.
			return ((StringTrie) cVal).get(key.substring(1));
		}
		
		if (cVal instanceof Leaf) {
			// cVal contains a user datum, but does the key match its substring?
			Leaf cPair = (Leaf) cVal;
			if (key.equals(cPair.mStr)) {
				return cPair.mVal; // Return user's data value.
			}
		}
		
		throw new IllegalStateException("Internal error");
	}
	
	public int size()
	{
		return size;
	}
}
