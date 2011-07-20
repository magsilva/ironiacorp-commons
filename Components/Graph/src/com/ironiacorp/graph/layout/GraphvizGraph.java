/*
This program is free software; you can redistribute it and/or modify it    
under the terms of the GNU Lesser General Public License as published by   
the Free Software Foundation; either version 2.1 of the License, or        
(at your option) any later version.                                        
                                                                             
This program is distributed in the hope that it will be useful, but        
WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY 
or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public    
License for more details.                                                  
                                                                             
You should have received a copy of the GNU Lesser General Public License   
along with this program; if not, write to the Free Software Foundation,    
Inc., 675 Mass Ave, Cambridge, MA 02139, USA.                              
  
Copyright (C) 2003 Laszlo Szathmary <szathml@delfin.unideb.hu>
 */

package com.ironiacorp.graph.layout;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.Vector;

import javax.swing.JOptionPane;

import org.jinterop.dcom.common.IJIAuthInfo;
import org.jinterop.dcom.common.JIDefaultAuthInfoImpl;
import org.jinterop.dcom.common.JIException;
import org.jinterop.winreg.IJIWinReg;
import org.jinterop.winreg.JIPolicyHandle;
import org.jinterop.winreg.JIWinRegFactory;

/**
 * GraphViz Java API is a simple API to call dot from Java programs.
 * 
 * With this Java class you can simply call dot from your Java programs Example usage:
 * 
 * <pre>
 * GraphViz gv = new GraphViz();
 * gv.addln(gv.start_graph());
 * gv.addln(&quot;A -&gt; B;&quot;);
 * gv.addln(&quot;A -&gt; C;&quot;);
 * gv.addln(gv.end_graph());
 * System.out.println(gv.getDotSource());
 * 
 * File out = new File(&quot;out.gif&quot;);
 * gv.writeGraphToFile(gv.getGraph(gv.getDotSource()), out);
 * </pre>
 */
public class GraphvizGraph
{
	/**
	 * The source of the graph written in dot language.
	 */
	private StringBuffer graph = new StringBuffer();


	/**
	 * Returns the graph's source description in dot language.
	 * 
	 * @return Source of the graph in dot language.
	 */
	public String getDotSource()
	{
		return graph.toString();
	}

	public void addNode(GVFNode node)
	{
		graph.append(node.getSource() + " [width=\"0.50\", height=\"0.50\"];\n");
	}

	public void addEdge(GVFNode src, GVFNode dest)
	{
		graph.append(src.getSource() + " -> " + dest.getSource() + "\n");
	}

	private String getDotLayout(String dot_source)
	{
		File dot;
		String img_stream = null;

		try {
			dot = writeDotSourceToFile(dot_source);
			if (dot != null) {
				img_stream = runDotLayout(dot);
				if (dot.delete() == false)
					System.err.println("Warning: " + dot.getAbsolutePath()
									+ " could not be deleted!");
			}
			return img_stream;
		} catch (Exception ioe) {
			return null;
		}
	}

	/**
	 * Writes the source of the graph in a file, and returns the written file as a File object.
	 * 
	 * @param str Source of the graph (in dot language).
	 * @return The file (as a File object) that contains the source of the graph.
	 */
	private File writeDotSourceToFile(String str) throws java.io.IOException
	{
		File temp;
		try {
			temp = File.createTempFile("graph_", ".dot.tmp", new File(this.TEMP_DIR));
			FileWriter fout = new FileWriter(temp);
			fout.write(str);
			fout.close();
		} catch (Exception e) {
			System.err.println("Error: I/O error while writing the dot source to temp file!");
			return null;
		}
		return temp;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.jabuti.gvf.layout.graphviz.GraphLayout#start_graph()
	 */
	public void start_graph()
	{
		graph.append("digraph G {\n");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.jabuti.gvf.layout.graphviz.GraphLayout#end_graph()
	 */
	public void end_graph()
	{
		graph.append("}");
	}

	public void layout(Vector vNodes, Vector vLinks)
	{

		String result = getDotLayout(getDotSource());

		File f = new File(result);
		try {
			DotParser dt = new DotParser(vNodes, vLinks, new FileInputStream(f));
			dt.parse();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} finally {
			f.delete();
		}
	}
}
