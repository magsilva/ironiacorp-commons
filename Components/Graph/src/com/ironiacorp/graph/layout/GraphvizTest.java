package com.ironiacorp.graph.layout;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.Before;
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
