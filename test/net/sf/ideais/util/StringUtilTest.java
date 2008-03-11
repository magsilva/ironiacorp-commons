/*
This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or
(at your option) any later version.
 
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
 
You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 
Copyright (C) 2007 Marco Aurelio Graciotto Silva <magsilva@gmail.com>
*/

package tests.net.sf.ideais.util;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import net.sf.ideais.util.StringUtil;

import org.junit.Before;
import org.junit.Test;


public class StringUtilTest
{
	private static final String origStr = "test";
	private static final String upcaseStr = "TEST";
	private static final String mixedcaseStr = "TeSt";

	private static final String differentStr = "abcd";
	private static final String fuzzySuffixStr = "teste";
	private static final String fuzzyPrefixStr = "etest";

	private static final String prefix = "123";
	private static final String suffix = "321";
	
	private static Map<Integer, String> prefixOption; 
	private static Map<Integer, String> suffixOption; 
	private static Map<Integer, String> invalidOption;
	
	private static Set<String> goodWordlist;
	private static Set<String> badWordlist;
	
	@Before
	public void setUp() throws Exception
	{
		prefixOption = new HashMap<Integer, String>();
		prefixOption.put(StringUtil.PREFIX, prefix);
		
		suffixOption = new HashMap<Integer, String>();
		suffixOption.put(StringUtil.SUFFIX, suffix);
		
		invalidOption = new HashMap<Integer, String>();
		invalidOption.put(-1, "lsadj");
		
		goodWordlist = new TreeSet<String>();
		goodWordlist.add(origStr);
		goodWordlist.add(upcaseStr);
		goodWordlist.add(mixedcaseStr);
		goodWordlist.add(differentStr);
		goodWordlist.add(fuzzyPrefixStr);
		goodWordlist.add(fuzzySuffixStr);
		
		badWordlist = new TreeSet<String>();
		badWordlist.add(differentStr);
		badWordlist.add(fuzzyPrefixStr);
		badWordlist.add(fuzzySuffixStr);
	}

	@Test
	public void testTransformPrefix()
	{
		String str = StringUtil.transform(origStr, prefixOption);
		assertEquals(prefix + origStr, str);
	}

	@Test
	public void testTransformSuffix()
	{
		String str = StringUtil.transform(origStr, suffixOption);
		assertEquals(origStr + suffix, str);
	}

	@Test
	public void testTransformBoth()
	{
		suffixOption.putAll(prefixOption);
		String str = StringUtil.transform(origStr, suffixOption);
		assertEquals(prefix + origStr + suffix, str);
	}

	@Test
	public void testTransformNone()
	{
		String str = StringUtil.transform(origStr, null);
		assertEquals(origStr, str);
	}

	@Test
	public void testTransformInvalid()
	{
		String str = StringUtil.transform(origStr, invalidOption);
		assertEquals(origStr, str);
	}

	@Test
	public void testIsSimilar1()
	{
		assertTrue(StringUtil.isSimilar(origStr, origStr));
	}

	@Test
	public void testIsSimilar2()
	{
		assertTrue(StringUtil.isSimilar(origStr, upcaseStr));
	}

	@Test
	public void testIsSimilar3()
	{
		assertTrue(StringUtil.isSimilar(origStr, mixedcaseStr));
	}
	
	@Test
	public void testIsSimilar4()
	{
		assertFalse(StringUtil.isSimilar(origStr, differentStr));
	}

	@Test
	public void testIsSimilar5()
	{
		assertFalse(StringUtil.isSimilar(origStr, fuzzyPrefixStr));
	}

	@Test
	public void testIsSimilar6()
	{
		assertFalse(StringUtil.isSimilar(origStr, fuzzySuffixStr));
	}
	
	@Test
	public void testIsSimilar7()
	{
		assertFalse(StringUtil.isSimilar(origStr, null));
	}
	
	@Test
	public void testFindSimilar1()
	{
		assertNotNull(StringUtil.findSimilar(goodWordlist, origStr));
	}

	@Test
	public void testFindSimilar2()
	{
		assertNull(StringUtil.findSimilar(badWordlist, origStr));
	}
}
