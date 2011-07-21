/*
 * Copyright (C) 2011 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ironiacorp.graph.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.ironiacorp.graph.rendering.RenderingDescription;

public abstract class ElementTest {

	protected Element element;
	
	protected Element element2;

	@Test
	public void testHashCode_NotSameAndDifferentObjects() {
		element.setId(1);
		element2.setId(2);
		assertFalse(element.hashCode() == element2.hashCode());
	}

	@Test
	public void testHashCode_NotSameButEqualObjects() {
		element.setId(1);
		element2.setId(1);
		assertEquals(element.hashCode(), element2.hashCode());
	}

	@Test
	public void testHashCode_NotSameButEqualComplexObjects() {
		element.setId(1);
		element.setLabel("test");
		element.setAttribute("class", Element.class);
		element2.setId(1);
		element2.setLabel("test");
		element2.setAttribute("class", Element.class);
		assertEquals(element.hashCode(), element2.hashCode());
	}

	@Test
	public void testHashCode_NotSameButDifferentComplexObjectsYetSameHashCode() {
		element.setId(1);
		element.setLabel("test");
		element.setAttribute("color", "blue");
		element2.setId(1);
		element2.setLabel("test");
		element2.setAttribute("class", Element.class);
		assertEquals(element.hashCode(), element2.hashCode());
	}

	@Test
	public void testEquals_Null() {
		element.setId(1);
		element.setLabel("test");
		element.setAttribute("color", "blue");
		assertFalse(element.equals(null));
	}

	@Test
	public void testEquals_Same() {
		element.setId(1);
		element.setLabel("test");
		element.setAttribute("color", "blue");
		assertEquals(element, element);
	}

	@Test
	public void testEquals_ObjectDifferentClass() {
		element.setId(1);
		element.setLabel("test");
		element.setAttribute("color", "blue");
		assertFalse(element.equals("test123"));
	}

	@Test
	public void testEquals_Different_Id() {
		element.setId(1);
		element.setLabel("test");
		element.setAttribute("color", "blue");
		element2.setId(2);
		assertFalse(element.equals(element2));
	}

	@Test
	public void testEquals_Different_Label() {
		element.setId(1);
		element.setLabel("123");
		element.setAttribute("color", "blue");
		element2.setId(1);
		element2.setLabel("test");
		assertFalse(element.equals(element2));
	}

	@Test
	public void testEquals_Different_Label_Null() {
		element.setId(1);
		element.setLabel(null);
		element.setAttribute("color", "blue");
		element2.setId(1);
		element2.setLabel("test");
		assertFalse(element.equals(element2));
	}

	@Test
	public void testEquals_Different_Attributes() {
		element.setId(1);
		element.setLabel(null);
		element.setAttributes(null);
		element2.setId(1);
		element2.setLabel(null);
		assertFalse(element.equals(element2));
	}

	@Test
	public void testEquals_NotSameButDifferentComplexObjectsYetSameHashCode() {
		element.setId(1);
		element.setLabel("test");
		element.setAttribute("color", "blue");
		element2.setId(1);
		element2.setLabel("test");
		element2.setAttribute("class", Element.class);
		assertFalse(element.equals(element2));
		assertTrue(element.equals(element2, true));
	}

	@Test
	public void testGetId_InitialValue() {
		assertEquals(0, element.getId());
	}

	@Test
	public void testGetId() {
		element.setId(1);
		assertEquals(1, element.getId());
	}

	@Test
	public void testSetId() {
		element.setId(1);
		assertEquals(1, element.getId());
		element.setId(3);
		assertEquals(3, element.getId());
	}

	@Test
	public void testGetLabel_InitialValue() {
		assertNull(element.getLabel());
	}

	@Test
	public void testGetLabel() {
		element.setLabel("test");
		assertEquals("test", element.getLabel());
	}

	@Test
	public void testSetLabel() {
		element.setLabel("test");
		assertEquals("test", element.getLabel());
		element.setLabel("123");
		assertEquals("123", element.getLabel());
	}

	@Test
	public void testGetAttributes() {
		Map<String, Object> map = element.getAttributes();
		assertNotNull(map);
		assertEquals(0, map.size());
	}

	@Test
	public void testGetAttribute() {
		Object result = element.getAttribute("test");
		assertNull(result);
	}

	@Test
	public void testSetAttributes_Null() {
		element.setAttributes(null);
		assertNull(element.getAttributes());
	}

	@Test
	public void testSetAttributes() {
		Map<String, Object> map = element.getAttributes();
		Map<String, Object> newMap = new HashMap<String, Object>();
		element.setAttributes(newMap);
		assertSame(newMap, element.getAttributes());
		assertNotSame(map, element.getAttributes());
	}

	@Test
	public void testSetAttribute() {
		element.setAttribute("test", "123");
		Object result = element.getAttribute("test");
		assertEquals("123", result);
	}

	@Test
	public void testGetRenderingDescription_Initial() {
		assertNull(element.getRenderingDescription());
	}

	@Test
	public void testSetRenderingDescription() {
		RenderingDescription rd = new RenderingDescription();
		element.setRenderingDescription(rd);
		assertNotNull(element.getRenderingDescription());
		assertSame(rd, element.getRenderingDescription());
	}
}