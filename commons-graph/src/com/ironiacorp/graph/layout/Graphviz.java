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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.ironiacorp.io.IoUtil;
import com.ironiacorp.computer.ComputerSystem;
import com.ironiacorp.computer.OperationalSystem;
import com.ironiacorp.computer.Windows;
import com.ironiacorp.computer.WindowsRegistry;

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
public class Graphviz implements Layout
{
	/**
	 * Layouts implemented by GraphViz.
	 */
	public enum Filter
	{
		DOT("dot", "Directed graphs."),
		NEATO("neato", "Undirected graphs."),
		TWOPI("twopi", "Radial layout. One node is chosen as the center and put at the origin. The remaining nodes are placed on a sequence of concentric circles centered about the origin, each a fixed radial distance from the previous circle."),
		CIRCO("circo", "Circular layout. It identifies biconnected components and draws the nodes of the component on a circle."),
		FDP("fdp", "Undirected graphs using a spring model using a force-directed approach."),
		SFDP("sfdp", "Undirected graphs using a spring model using a multi-scale approach (suitable for large graphs).");
		
		public final String name;
		
		public final String description;
		
		Filter(String name, String description) {
			this.name = name;
			this.description = description;
		}
	}

	/**
	 * Subset of output formats supported by GraphViz. The subset is composed of
	 * the most common formats.
	 */
	public enum OutputFormat
	{
		DOT("dot"),
		GIF("gif"),
		JPG("jpg"),
		PDF("pdf"),
		PNG("png"),
		PS("ps2"),
		SVG("svg"),
		XDOT("xdot");
		
		public final String name;
		
		OutputFormat(String name) {
			this.name = name;
		}
	}
	
	/**
	 * Default path for GraphViz in Windows systems.
	 */
	public static final String[] DEFAULT_WINDOWS_PATH = {
		"C:\\Program Files\\ATT\\Graphviz\\bin\\",
		"C:\\Program Files\\Graphviz2.24\\bin\\",
		"C:\\Program Files\\Graphviz2.26\\bin\\",
		"C:\\Program Files\\Graphviz2.28\\bin\\",
	};
	
	private static final String DEFAULT_WINDOWS_REGISTRY_KEY = "Software\\Software\\AT&T Research Labs\\Graphviz\\";
	
	private static final String DEFAULT_WINDOWS_REGISTRY_VALUE = "InstallPath";

		
	/**
	 * Default filter (layout) to be used by GraphViz.
	 */
	public static final Filter DEFAULT_FILTER = Filter.DOT;

	/**
	 * Default output format to be used by GraphViz.
	 */
	public static final OutputFormat DEFAULT_FORMAT = OutputFormat.PNG;

	/**
	 * Directory where GraphViz's binaries have been installed into.
	 */
	private File binaryBasedir;
	
	/**
	 * Options parameters to run GraphViz.
	 */
	private ArrayList<String> defaultParameters;
	
    
    /**
	 * Find GraphViz.
	 * 
	 * @return Directory where GraphViz has been installed.
	 */
    private File findGraphVizExecutable()
    {
		return findGraphVizExecutable(DEFAULT_FILTER);
    }
    
    /**
	 * Find GraphViz.
	 * 
	 * @return Directory where GraphViz has been installed.
	 */
    private File findGraphVizExecutable(Filter filter)
    {
    	OperationalSystem os = ComputerSystem.getCurrentOperationalSystem();
    	os.addExecutableSearchPath(binaryBasedir);
    	
    	if (os instanceof Windows) {
    		WindowsRegistry registry = new WindowsRegistry();
    		String keyValue = registry.getString(DEFAULT_WINDOWS_REGISTRY_KEY, DEFAULT_WINDOWS_REGISTRY_VALUE);
    		if (keyValue != null && ! keyValue.trim().isEmpty()) {
    			File path = new File(keyValue);
    			if (path.isDirectory()) {
    				os.addExecutableSearchPath(path);
    			}
    		}
    	}
    	
    	return os.findExecutable(filter.name);
    }
    
    
    /**
	 * Create the GraphViz runner. We will try to find where GraphViz has been installed in the
	 * system automatically.
	 * 
	 * @throws UnsupportedOperationException if GraphViz could not be found.
	 */
	public Graphviz()
	{
		File file = findGraphVizExecutable();
		File dir = file.getParentFile();
		setBinaryBasedir(dir);
    	defaultParameters = new ArrayList<String>();
	}
	
	/**
	 * Create the GraphViz runner.
	 * 
	 * @param file Directory where GraphViz has been installed.
	 * @throws UnsupportedOperationException if GraphViz could not be found.
	 */
	public Graphviz(File file)
	{
		setBinaryBasedir(file);
    	defaultParameters = new ArrayList<String>();
	}
	
	private void setBinaryBasedir(File path)
	{
		if (path == null || ! path.isDirectory()) {
    		throw new UnsupportedOperationException(new FileNotFoundException("Cannot find GraphViz."));
    	}

    	binaryBasedir = path;
	}
	
	/**
	 * Get the directory where GraphViz has been installed.
	 * @return
	 */
	public File getBinaryBasedir()
	{
		return binaryBasedir;
	}
	
	/**
	 * Add a new parameter to the list of default parameters to be provided to Graphviz.
	 * 
	 * @param parameter GraphViz parameter (eg., -n1).
	 */
	public void addParameter(String parameter)
	{
		if (parameter != null) {
			defaultParameters.add(parameter);
		}
	}
	
	/**
	 * Get default parameters.
	 * 
	 * @return Array with default parameters.
	 */
	public String[] getParameters()
	{
		return defaultParameters.toArray(new String[0]);
	}

	/**
	 * Run GraphViz against the graph. It will use the default layout and output
	 * format.
	 * 
	 * @param graphDescription Graph definition (in the format required by GraphViz).
	 * @return Output file.
	 */
	public File run(String graphDescription)
	{
    	return run(graphDescription, DEFAULT_FILTER, DEFAULT_FORMAT);
	}

	/**
	 * Run GraphViz against the graph.
	 * 
	 * @param graphDescription Graph definition (in the format required by GraphViz).
	 * @param filter Layout engine to be used.
	 * @param format Output format.
	 *  
	 * @return Output file.
	 */
	public File run(String graphDescription, Filter filter, OutputFormat format)
	{
    	File outputFile = IoUtil.createTempFile();
    	return run(graphDescription, filter, format, outputFile);
	}
	
	/**
	 * Run GraphViz against the graph.
	 * 
	 * @param graphDescription Graph definition (in the format required by GraphViz).
	 * @param filter Layout engine to be used.
	 * @param format Output format.
	 *  
	 * @return Output file.
	 */
	public File run(String graphDescription, Filter filter, OutputFormat format, File outputFile)
	{
    	File binary;
    	File inputFile;
    	ArrayList<String> parameters = new ArrayList<String>();
    	
    	if (filter == null || graphDescription == null || format == null || outputFile == null) {
    		throw new IllegalArgumentException(new NullPointerException());
    	}
    	
    	binary = findGraphVizExecutable(filter);
		inputFile = IoUtil.createTempFile();
		parameters.add(0, binary.getAbsolutePath());
		parameters.add("-o" + outputFile.getAbsolutePath());
		parameters.add("-T" + format.name);
		parameters.addAll(defaultParameters);
		parameters.add(inputFile.getAbsolutePath());
		try {
	    	FileWriter writer = new FileWriter(inputFile);
			writer.append(graphDescription);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			throw new UnsupportedOperationException("Cannot write graph to file", e);
		}
		
		ProcessBuilder pb = new ProcessBuilder(parameters.toArray(new String[0]));
		try {
			Process process = pb.start();
			int result = process.waitFor();
			if (result != 0) {
				throw new IllegalArgumentException("Error running command: " + parameters.toArray(new String[0]));
			}
			return outputFile;
		} catch (Exception e) {
			throw new IllegalArgumentException("Error running command: " + Arrays.toString(parameters.toArray(new String[0])));
		}
    }
}
