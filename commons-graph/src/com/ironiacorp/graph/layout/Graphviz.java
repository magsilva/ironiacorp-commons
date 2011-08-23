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

import java.io.*;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

import org.jinterop.dcom.common.IJIAuthInfo;
import org.jinterop.dcom.common.JIDefaultAuthInfoImpl;
import org.jinterop.dcom.common.JIException;
import org.jinterop.winreg.IJIWinReg;
import org.jinterop.winreg.JIPolicyHandle;
import org.jinterop.winreg.JIWinRegFactory;

import com.ironiacorp.io.IoUtil;
import com.ironiacorp.computer.OperationalSystem;
import com.ironiacorp.computer.OperationalSystemDetector;

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
public class Graphviz
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
	 * Default path for GraphViz in Unix systems.
	 */
	public static final String[] DEFAULT_UNIX_PATH = {
		"/usr/bin",
		"/usr/local/bin",
	};
	
	/**
	 * Default path for GraphViz in Windows systems.
	 */
	public static final String[] DEFAULT_WINDOWS_PATH = {
		"C:\\Program Files\\ATT\\Graphviz\\bin\\",
		"C:\\Program Files\\Graphviz2.24\\bin\\",
		"C:\\Program Files\\Graphviz2.26\\bin\\",
		"C:\\Program Files\\Graphviz2.28\\bin\\",
	};
	
	/**
	 * Default extension of executable file in Windows.
	 */
	public static final String DEFAULT_WINDOWS_EXTENSION = ".exe";
	
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
	 * Find GraphViz on Windows systems.
	 * 
	 * @return Directory where GraphViz has been installed.
	 */
    private String getGraphVizPathOnWindows()
    {
        String path = null;
        
        // Try to access Windows's registry using native access.
        try {
        	path = getGraphVizPathOnWindowsUsingNativeAccess();
        	if (path != null) {
        		return path;
        	}
        } catch (Exception e) {
        }

        // Try to access Windows's registry using RegEdit.exe
        try {
        	path = getGraphVizPathOnWindowsUsingRegEdit();
        	if (path != null) {
        		return path;
        	}
        } catch (Exception e) {
        }

        // Last resort: try to find GraphViz in the usual places.
       	path = getGraphVizPathOnWindowsUsingLuckyCharm();
        
        return path;
    }


    /**
     * Read GraphViz installation path using native access.
     * 
     * @return Directory where GraphViz has been installed (and null if not found).
     */
    // TODO: Refactor and move this to IroniaCorp-Commons-SystemInfo
    private String getGraphVizPathOnWindowsUsingNativeAccess()
    {
            String domain = "";
            String username = "";
            String password = "";
            String key = "Software\\Software\\AT&T Research Labs\\Graphviz\\";
            String dir = null;

            // IJIWinReg winReg = JIWinRegFactory.getSingleTon().getWinreg(hostInfo, hostInfo.getHost(), true);
            IJIAuthInfo authInfo = new JIDefaultAuthInfoImpl(domain, username, password);
            try {
                    IJIWinReg registry = JIWinRegFactory.getSingleTon().getWinreg(authInfo, domain, true);
                    JIPolicyHandle policyHandle1 = registry.winreg_OpenHKLM();
                    JIPolicyHandle policyHandle2 = registry.winreg_OpenKey(policyHandle1, key, IJIWinReg.KEY_READ);
                    Object[] value = registry.winreg_QueryValue(policyHandle2, "InstallPath", 4096);
                    dir = (String) value[0];
                    registry.winreg_CloseKey(policyHandle2);
                    registry.winreg_CloseKey(policyHandle1);
            } catch (JIException e) {
            } catch (UnknownHostException JavaDoc) {
            }

            return dir;
    }

    /**
     * Read GraphViz installation path using RegEdit.exe
     * 
     * @return Directory where GraphViz has been installed (and null if not found).
     */
    // TODO: Refactor and move this to IroniaCorp-Commons-SystemInfo
    private String getGraphVizPathOnWindowsUsingRegEdit()
    {
            final String REGQUERY_UTIL = "reg query ";
            final String REGSTR_TOKEN = "REG_EXPAND_SZ";
            final String COMPUTER_WINDOWS_GRAPHVIZ_FOLDER = REGQUERY_UTIL + "\"HKLM\\SOFTWARE\\AT&T Research Labs\\Graphviz\" /v InstallPath";

            try {
                    Process process = Runtime.getRuntime().exec(COMPUTER_WINDOWS_GRAPHVIZ_FOLDER);
                    BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                    process.waitFor();
                    String result = reader.readLine();
                    int p = result.indexOf(REGSTR_TOKEN);
                    if (p == -1) {
                            return null;
                    } else {
                            return result.substring(p + REGSTR_TOKEN.length()).trim();
                    }
            } catch (Exception e) {
                    return null;
            }
    }

    /**
     * Try to find GraphViz in the usual places.
     * 
     * @return Directory where GraphViz has been installed (and null if not found).
     */
    private String getGraphVizPathOnWindowsUsingLuckyCharm()
    {
    	for (String dir : DEFAULT_WINDOWS_PATH) {
    		File file = new File(dir, DEFAULT_FILTER.name + DEFAULT_WINDOWS_EXTENSION);
    		if (file.exists()) {
    			return dir;
    		}
    	}
    	return null;
    }

    
    /**
	 * Find GraphViz on Linux systems.
	 * 
	 * @return Directory where GraphViz has been installed.
	 */
    private String getGraphVizPathOnLinux()
    {
    	for (String dir : DEFAULT_UNIX_PATH) {
    		File file = new File(dir, DEFAULT_FILTER.name);
    		if (file.exists()) {
    			return dir;
    		}
    	}
    	return null;
    }
	
    /**
	 * Find GraphViz on MacOS systems.
	 * 
	 * @return Directory where GraphViz has been installed.
	 */
    private String getGraphVizPathOnMacOS()
    {
    	return getGraphVizPathOnLinux();
    }
    
    /**
	 * Find GraphViz.
	 * 
	 * @return Directory where GraphViz has been installed.
	 */
    private String findGraphVizPath()
    {
    	OperationalSystemDetector detector = new OperationalSystemDetector();
    	OperationalSystem os = detector.detectCurrentOS();
    	String path = null;
    	switch (os) {
    		case Windows:
    			path = getGraphVizPathOnWindows();
    			break;
    		case Solaris:
    		case Linux:
    			path = getGraphVizPathOnLinux();
    			break;
    		case MacOS:
    			path = getGraphVizPathOnMacOS();
    			break;    			
    	}

    	return path;
    }
    
    /**
     * Set the directory where GraphViz has been installed.
     * 
     * @param file Directory where GraphViz has been installed.
     */
	private void setBinaryBasedir(File file)
	{
		if (file != null && file.exists() && file.isDirectory()) {
			binaryBasedir = file;
		} else {
			throw new IllegalArgumentException("Invalid file: " + file.getAbsolutePath());
		}
	}

    
    /**
	 * Create the GraphViz runner. We will try to find where GraphViz has been installed in the
	 * system automatically.
	 * 
	 * @throws UnsupportedOperationException if GraphViz could not be found.
	 */
	public Graphviz()
	{
		String path = findGraphVizPath();
    	if (path == null) {
    		throw new UnsupportedOperationException(new FileNotFoundException("Cannot find GraphViz."));
    	}
    	
    	setBinaryBasedir(new File(path));
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
		OperationalSystemDetector detector = new OperationalSystemDetector();
    	OperationalSystem os = detector.detectCurrentOS();
    	File binary;
    	File inputFile;
    	ArrayList<String> parameters = new ArrayList<String>();
    	
    	if (filter == null || graphDescription == null || format == null || outputFile == null) {
    		throw new IllegalArgumentException(new NullPointerException());
    	}
    	
		switch (os) {
			case Windows:
				binary = new File(binaryBasedir, filter.name + DEFAULT_WINDOWS_EXTENSION);
				break;
			default:
				binary = new File(binaryBasedir, filter.name);
		}
		
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
