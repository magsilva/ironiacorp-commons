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

package com.ironiacorp.graph.layout;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Test;

public class GraphvizTest
{
	@Test
	public void testFindGraphvizPath()
	{
		Graphviz graphviz = new Graphviz();
		assertEquals("/usr/bin", graphviz.getBinaryBasedir().getAbsolutePath());
	}
	
	@Test
	public void testRun()
	{
		Graphviz graphviz = new Graphviz();
		String graph = "digraph test123 {a -> b -> c; b -> c; }";
		File output = graphviz.run(graph);
		assertTrue(output.exists());
	}
}
