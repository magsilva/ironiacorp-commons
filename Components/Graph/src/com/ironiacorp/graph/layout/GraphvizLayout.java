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

package br.jabuti.graph.layout.graphviz;

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

import br.jabuti.graph.layout.GraphLayout;
import br.jabuti.graph.view.gvf.GVFNode;

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
public class GraphvizLayout implements GraphLayout
{
	/**
	 * The dir where temporary files will be created.
	 */
	private static String TEMP_DIR = System.getProperty("java.io.tmpdir");

	/**
	 * Where is your dot program located? It will be called externally.
	 */
	private final static String DOT_W = "c:\\Arquivos de programas\\Graphviz2.22\\bin\\dot.exe";

	private final static String DOT_L = "/usr/bin/dot";

	private String DOT = null;

	/**
	 * The source of the graph written in dot language.
	 */
	private StringBuffer graph = new StringBuffer();

	private String windowsFindDot()
	{
		String s = getGraphVizPath2();
		if (s == null)
			return DOT_W;
		else
			return s += File.separator + "bin" + File.separator + "dot.exe";
	}

	/**
	 * You can set the Authentication in DCOM component to "None" , this way no authentication would
	 * be required by it. Unfortunately j-Interop does not support this. (We need security to
	 * atleast be set to "Connect").
	 * 
	 * @return
	 */
	public String getGraphVizPath()
	{
		String domain = "";
		;
		String username = "";
		String password = "";
		String key = "Software\\Software\\AT&T Research Labs\\Graphviz\\";

		String dir = null;

		// IJIWinReg winReg = JIWinRegFactory.getSingleTon().getWinreg(hostInfo, hostInfo.getHost(), true);
		IJIAuthInfo authInfo = new JIDefaultAuthInfoImpl(domain, username, password);
		try {
			IJIWinReg registry = JIWinRegFactory.getSingleTon().getWinreg(authInfo, domain, true);
			JIPolicyHandle policyHandle1 = registry.winreg_OpenHKLM();
			JIPolicyHandle policyHandle2 = registry.winreg_OpenKey(policyHandle1, key,
							IJIWinReg.KEY_READ);
			Object[] value = registry.winreg_QueryValue(policyHandle2, "InstallPath", 4096);
			dir = (String) value[0];
			System.out.println(dir);
			registry.winreg_CloseKey(policyHandle2);
			registry.winreg_CloseKey(policyHandle1);
		} catch (JIException e) {
		} catch (UnknownHostException JavaDoc) {
		}

		return dir;
	}

	public String getGraphVizPath2()
	{
		final String REGQUERY_UTIL = "reg query ";
		final String REGSTR_TOKEN = "REG_EXPAND_SZ";
		final String COMPUTER_WINDOWS_GRAPHVIZ_FOLDER = REGQUERY_UTIL
						+ "\"HKLM\\SOFTWARE\\AT&T Research Labs\\Graphviz\" /v InstallPath";

		try {
			Process process = Runtime.getRuntime().exec(COMPUTER_WINDOWS_GRAPHVIZ_FOLDER);
			BufferedReader reader = new BufferedReader(new InputStreamReader(process
							.getInputStream()));
			process.waitFor();
			String result = reader.readLine();
			int p = result.indexOf(REGSTR_TOKEN);
			if (p == -1)
				return null;
			else
				return result.substring(p + REGSTR_TOKEN.length()).trim();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Constructor: creates a new GraphViz object that will contain a graph.
	 * 
	 * @throws FileNotFoundException
	 */
	public GraphvizLayout()
	{
		if (DOT == null) {
			String s = System.getProperty("os.name").toUpperCase();
			if ("LINUX".equals(s)) {
				DOT = DOT_L;
			} else if (s != null && s.startsWith("WINDOWS")) {
				DOT = windowsFindDot();
				DOT = DOT_W;
			} else {
				DOT = JOptionPane.showInputDialog(null, "Please enter path:",
								"Cannot find GraphViz layouter (dot).", JOptionPane.ERROR_MESSAGE);

			}
			while (DOT != null) {
				File f = new File(DOT);
				if (f.isFile() && f.canRead())
					break;
				DOT = JOptionPane.showInputDialog(null, "Please enter path:",
								"Cannot find GraphViz layouter at " + DOT,
								JOptionPane.ERROR_MESSAGE);

			}
			if (DOT == null) {
				DOT = "";
				throw new RuntimeException(new FileNotFoundException("Cannot find GraphViz."));
			}
		}
	}

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

	private String runDotLayout(File dot) throws IOException, InterruptedException
	{
		File output = File.createTempFile("graph_", ".dot", new File(TEMP_DIR));

		Runtime rt = Runtime.getRuntime();
		String cmd = DOT + " -Tdot " + dot.getAbsolutePath() + " -o" + output.getAbsolutePath();
		Process p = rt.exec(cmd);
		p.waitFor();
		return output.getAbsolutePath();
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
