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

package com.ironiacorp.commons;

import static org.junit.Assert.*;

import java.util.Arrays;


import org.junit.Before;
import org.junit.Test;

import com.ironiacorp.commons.ArrayUtil;


public class ArrayUtilTest
{
	private Object[] emptyArray = {};
	
	private Object[] arrayWithNull = {1, 3, null, 5, 10, "a", "cde", null};
	private Object[] arrayWithNullMixed = {"a", 1, null, 3, 5, 10,  "cde", null};

	private Object[] arrayWithoutNull = {1, 3, 5, 10, "a", "cde"};;
	private String arrayWithoutNullasStringNewLine = "1\n3\n5\n10\na\ncde";
	private String arrayWithoutNullasStringComma = "1, 3, 5, 10, a, cde";

	private String[] stringArray = {"a", "b", "c"};
	
	private Object objectInArray = "a";

	private Object objectNotInArray = "abc";

	
	@Before
	public void setUp() throws Exception
	{
	}
	
	@Test
	public void testHas1()
	{
		assertTrue(ArrayUtil.has(arrayWithNull, objectInArray));
	}

	@Test
	public void testHas2()
	{
		assertFalse(ArrayUtil.has(arrayWithNull, objectNotInArray));
	}

	
	@Test
	public void testHasNullArray()
	{
		assertFalse(ArrayUtil.has(null, this));
	}

	@Test
	public void testHasNullObject()
	{
		assertTrue(ArrayUtil.has(arrayWithNull, null));
	}

	
	@Test
	public void testDup1()
	{
		Object[] dupArray = ArrayUtil.dup(arrayWithNull);
		assertTrue(Arrays.equals(arrayWithNull, dupArray));
		assertFalse(dupArray == arrayWithNull);
	}

	@Test
	public void testDup2()
	{
		Object[] dupArray = ArrayUtil.dup(arrayWithNull);
		assertFalse(Arrays.equals(arrayWithoutNull, dupArray));
	}

	@Test
	public void testDup3()
	{
		Object[] dupArray = ArrayUtil.dup(emptyArray);
		assertTrue(Arrays.equals(emptyArray, dupArray));
		assertFalse(dupArray == emptyArray);
	}

	
	@Test
	public void testDupNull()
	{
		Object[] dupArray = ArrayUtil.dup(null);
		assertTrue(Arrays.equals(null, dupArray));
		assertTrue(dupArray == null);
	}

	
	@Test
	public void testEqualIgnoreOrder1()
	{
		assertTrue(ArrayUtil.equalIgnoreOrder(arrayWithNull, arrayWithNullMixed));
	}

	@Test
	public void testEqualIgnoreOrder2()
	{
		assertFalse(ArrayUtil.equalIgnoreOrder(arrayWithNull, arrayWithoutNull));
	}

	@Test
	public void testEqualIgnoreOrder3()
	{
		assertFalse(ArrayUtil.equalIgnoreOrder(null, arrayWithNull));
	}

	@Test
	public void testEqualIgnoreOrder4()
	{
		assertFalse(ArrayUtil.equalIgnoreOrder(arrayWithNull, null));
	}

	@Test
	public void testEqualIgnoreOrder5()
	{
		assertTrue(ArrayUtil.equalIgnoreOrder(null, null));
	}

	
	@Test
	public void testFindObjectArrayClass1()
	{
		assertNotNull(ArrayUtil.find(arrayWithNull, String.class));
		assertTrue(ArrayUtil.find(arrayWithoutNull, String.class).equals(objectInArray));
	}

	@Test
	public void testFindObjectArrayClass2()
	{
		assertNull(ArrayUtil.find(arrayWithNull, Long.class));
	}

	@Test
	public void testFindObjectArrayClass3()
	{
		assertNull(ArrayUtil.find(arrayWithNull, null));
	}

	@Test
	public void testFindObjectArrayClass4()
	{
		assertNull(ArrayUtil.find(null, String.class));
	}

	@Test
	public void testFindObjectArrayClass5()
	{
		assertNull(ArrayUtil.find((Object[]) null, (Class) null));
	}


	@Test
	public void testFindStringArrayClass1()
	{
		assertTrue(ArrayUtil.find(stringArray, "a") == 0);
	}

	@Test
	public void testFindStringArrayClass2()
	{
		assertFalse(ArrayUtil.find(stringArray, "a") == 1);
	}

	@Test
	public void testFindStringArrayClass3()
	{
		assertTrue(ArrayUtil.find(stringArray, (String) null) == -1);
	}

	@Test
	public void testFindStringArrayClass4()
	{
		assertTrue(ArrayUtil.find(null, "a") == -1);
	}

	@Test
	public void testFindStringArrayClass5()
	{
		assertTrue(ArrayUtil.find((String[]) null, (String) null) == -1);
	}

	
	@Test
	public void testToStringObjectArray1()
	{
		assertTrue(ArrayUtil.toString(arrayWithoutNull).equals(arrayWithoutNullasStringNewLine));
	}

	@Test
	public void testToStringObjectArray2()
	{
		assertTrue(ArrayUtil.toString(arrayWithNull).equals(arrayWithoutNullasStringNewLine));
	}

	
	@Test
	public void testToStringObjectArray3()
	{
		assertFalse(ArrayUtil.toString(arrayWithoutNull, ",").equals(arrayWithoutNullasStringComma));
	}

	@Test
	public void testToStringObjectArray4()
	{
		assertFalse(ArrayUtil.toString(arrayWithNull, ",").equals(arrayWithoutNullasStringComma));
	}

	@Test(expected=IllegalArgumentException.class)
	public void testToStringObjectArray5()
	{
		assertFalse(ArrayUtil.toString(arrayWithNull, null).equals(arrayWithoutNullasStringComma));
	}
	
	@Test
	public void testClean1()
	{
		assertTrue(Arrays.equals(ArrayUtil.clean(arrayWithNull), arrayWithoutNull));
	}

	@Test
	public void testClean2()
	{
		assertFalse(Arrays.equals(null, arrayWithoutNull));
	}
	
	@Test
	public void testClean3()
	{
		assertFalse(Arrays.equals(ArrayUtil.clean(arrayWithNull), null));
	}
	@Test
	public void testClean4()
	{
		assertTrue(Arrays.equals(ArrayUtil.clean(null), null));
	}

}
