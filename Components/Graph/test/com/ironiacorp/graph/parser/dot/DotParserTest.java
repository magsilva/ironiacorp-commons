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

package com.ironiacorp.graph.parser.dot;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Collection;

import org.junit.Test;

import com.ironiacorp.graph.model.Element;
import com.ironiacorp.graph.model.Graph;

public class DotParserTest
{
	@Test
	public void testParse_Simple() throws ParseException {
		InputStream is =  Thread.currentThread().getContextClassLoader().getResourceAsStream("graphs/dot/graph_simple.dot");
		DotParser parser = new DotParser(is);
		Graph graph = parser.parse();
		Collection<Element> elements;
		Element element;
		
		elements = graph.findElementByLabel("0");
		assertTrue(elements.size() == 1);
		
		elements = graph.findElementByProperty("pos", "152,666");
		assertTrue(elements.size() == 1);
	}
	
	@Test
	public void testParse_Full() throws ParseException {
		InputStream is =  Thread.currentThread().getContextClassLoader().getResourceAsStream("graphs/dot/graph_full.dot");
		DotParser parser = new DotParser(is);
		parser.parse();
	}
	
	@Test
	public void testParse_NoEdge() throws ParseException {
		InputStream is =  Thread.currentThread().getContextClassLoader().getResourceAsStream("graphs/dot/graph_noedge.dot");
		DotParser parser = new DotParser(is);
		parser.parse();
	}
	
	@Test
	public void testParse_Empty() throws ParseException {
		InputStream is =  Thread.currentThread().getContextClassLoader().getResourceAsStream("graphs/dot/graph_empty.dot");
		DotParser parser = new DotParser(is);
		parser.parse();
	}

}
