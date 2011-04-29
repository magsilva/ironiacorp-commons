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

package com.ironiacorp.string;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.*;
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
	
	@Test
	public void testCapitalize_Null()
	{
		assertNull(StringUtil.capitaliseFirstLetter(null));
	}
	
	@Test
	public void testCapitalize_Empty()
	{
		assertEquals("", StringUtil.capitaliseFirstLetter(""));
	}

	@Test
	public void testCapitalize_OneLetter()
	{
		assertEquals("A", StringUtil.capitaliseFirstLetter("a"));
	}

	@Test
	public void testCapitalize_TwoLetters()
	{
		assertEquals("Ab", StringUtil.capitaliseFirstLetter("ab"));
	}
	
	@Test
	public void testCapitalize_Word()
	{
		assertEquals("Test", StringUtil.capitaliseFirstLetter("test"));
	}

	@Test
	public void testCapitalize_CapitalizedWord()
	{
		assertEquals("Test", StringUtil.capitaliseFirstLetter("Test"));
	}

	@Test
	public void testIsEmpty_NullWord()
	{
		assertTrue(StringUtil.isEmpty(null));
	}

	@Test
	public void testIsEmpty_EmptyWord()
	{
		assertTrue(StringUtil.isEmpty(""));
	}

	@Test
	public void testIsEmpty_OnlySpaces()
	{
		assertTrue(StringUtil.isEmpty("  "));
	}

	@Test
	public void testIsEmpty_OnlySpaceTabNewline()
	{
		assertTrue(StringUtil.isEmpty(" \t\n "));
	}

	@Test
	public void testIsEmpty_Word()
	{
		assertFalse(StringUtil.isEmpty("test"));
	}
	
	@Test
	public void testIsEmptyWithoutSpaces_Word()
	{
		assertFalse(StringUtil.isEmpty(" ", false));
	}
	
	@Test
	public void testIsInt_Int()
	{
		assertTrue(StringUtil.isInt("10"));
	}
	
	@Test
	public void testIsInt_IntWithSpaces()
	{
		assertTrue(StringUtil.isInt(" 10\n"));
	}

	@Test
	public void testIsInt_Word()
	{
		assertFalse(StringUtil.isInt("ten"));
	}

	
	@Test
	public void testIsFloat_Int()
	{
		assertTrue(StringUtil.isFloat("10"));
	}

	@Test
	public void testIsFloat_Float()
	{
		assertTrue(StringUtil.isFloat("10.0"));
	}

	@Test
	public void testIsFloat_FloatWithSpaces()
	{
		assertTrue(StringUtil.isFloat(" 10.0\n"));
	}

	@Test
	public void testIsFloat_Word()
	{
		assertFalse(StringUtil.isFloat("ten"));
	}

	@Test
	public void testIsFloat_BuggyDouble()
	{
		assertTrue(StringUtil.isFloat("2.2250738585072011e-308"));
	}

	
	@Test
	public void testIsDouble_Int()
	{
		assertTrue(StringUtil.isDouble("10"));
	}

	@Test
	public void testIsDouble_Double()
	{
		assertTrue(StringUtil.isDouble("10.0"));
	}

	@Test
	public void testIsDouble_DoubleWithSpaces()
	{
		assertTrue(StringUtil.isDouble(" 10.0\n"));
	}

	@Test
	public void testIsDouble_Word()
	{
		assertFalse(StringUtil.isDouble("ten"));
	}

	@Test
	public void testIsDouble_BuggyDouble()
	{
		assertTrue(StringUtil.isDouble("2.2250738585072011e-308"));
	}
	
	
	@Test(expected=IllegalArgumentException.class)
	public void testMD5_Nul()
	{
		StringUtil.digestMD5(null);
	}

	@Test
	public void testMD5_EmptyWord()
	{
		assertEquals("d41d8cd98f00b204e9800998ecf8427e", StringUtil.digestMD5(""));
	}
	
	@Test
	public void testMD5_Word()
	{
		assertEquals("098f6bcd4621d373cade4e832627b4f6", StringUtil.digestMD5("test"));
	}

	@Test
	public void testMD5_LongText()
	{
		assertEquals("be666f48526de8cafd77bf02c27e1123", StringUtil.digestMD5("The book is on the table, the sky is blue, the flowers are red. What about the birds on the rooftop?"));
	}

	
	@Test(expected=IllegalArgumentException.class)
	public void testSHA1_Nul()
	{
		StringUtil.digestSHA1(null);
	}

	@Test
	public void testSHA1_EmptyWord()
	{
		assertEquals("da39a3ee5e6b4b0d3255bfef95601890afd80709", StringUtil.digestSHA1(""));
	}
	
	@Test
	public void testSHA1_Word()
	{
		assertEquals("a94a8fe5ccb19ba61c4c0873d391e987982fbbd3", StringUtil.digestSHA1("test"));
	}

	@Test
	public void testSHA1_LongText()
	{
		assertEquals("402b044e36706f63cecc372e5c8529a85311fcb5", StringUtil.digestSHA1("The book is on the table, the sky is blue, the flowers are red. What about the birds on the rooftop?"));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testDigest_InvalidDigest()
	{
		StringUtil.digest("test", "IroniaCorp");
	}
	
	
	@Test
	public void testCountUpCaseLetters_0()
	{
		assertEquals(0, StringUtil.countUpCaseLetters("test"));
	}
	
	@Test
	public void testCountUpCaseLetters_1_First()
	{
		assertEquals(1, StringUtil.countUpCaseLetters("Test"));
	}
	
	@Test
	public void testCountUpCaseLetters_1_Last()
	{
		assertEquals(1, StringUtil.countUpCaseLetters("tesT"));
	}

	@Test
	public void testCountUpCaseLetters_2_FirstLast()
	{
		assertEquals(2, StringUtil.countUpCaseLetters("TesT"));
	}

	@Test
	public void testCountUpCaseLetters_3()
	{
		assertEquals(3, StringUtil.countUpCaseLetters("TeST"));
	}
	
	@Test
	public void testCountUpCaseLetters_All()
	{
		assertEquals(4, StringUtil.countUpCaseLetters("TEST"));
	}
	
	@Test
	public void testCountUpCaseLetters_AllWithSpaces()
	{
		assertEquals(4, StringUtil.countUpCaseLetters(" TEST\n"));
	}

	@Test
	public void testCountUpCaseLetters_NoText()
	{
		assertEquals(0, StringUtil.countUpCaseLetters(""));
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCountUpCaseLetters_Null()
	{
		StringUtil.countUpCaseLetters(null);
	}
	
	@Test
	public void testCountUpCaseLetters_TwoWords()
	{
		assertEquals(2, StringUtil.countUpCaseLetters("January February"));
	}


}
