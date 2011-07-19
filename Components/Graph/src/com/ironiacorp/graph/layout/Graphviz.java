package com.ironiacorp.graph.layout;

import java.io.*;
import java.net.UnknownHostException;

import org.jinterop.dcom.common.IJIAuthInfo;
import org.jinterop.dcom.common.JIDefaultAuthInfoImpl;
import org.jinterop.dcom.common.JIException;
import org.jinterop.winreg.IJIWinReg;
import org.jinterop.winreg.JIPolicyHandle;
import org.jinterop.winreg.JIWinRegFactory;

import com.ironiacorp.systeminfo.OperationalSystem;
import com.ironiacorp.systeminfo.OperationalSystemDetector;

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
	public enum Filter
	{
		DOT("dot"),
		NEATO("neato"),
		TWOPI("twopi"),
		CIRCO("circo"),
		FDP("fdp"),
		SFDP("sfdp");
		
		public final String name;
		
		Filter(String name) {
			this.name = name;
		}
	}

	public static final String[] DEFAULT_UNIX_PATH = {
		"/usr/bin",
		"/usr/local/bin",
	};
	
	public static final String[] DEFAULT_WINDOWS_PATH = {
		"C:\\Program Files\\ATT\\Graphviz\\bin\\",
		"C:\\Program Files\\Graphviz2.24\\bin\\",
		"C:\\Program Files\\Graphviz2.26\\bin\\",
		"C:\\Program Files\\Graphviz2.28\\bin\\",
	};
	
	public static final String DEFAULT_WINDOWS_EXTENSION = ".exe";
	
	public static final Filter DEFAULT_FILTER = Filter.DOT;

	public static final String DEFAULT_FLAGS = "-Tpng";

	private File binary;
	
	private String flags;
	
    private String getGraphVizPathOnWindows()
    {
        String path = null;;
        try {
        	path = getGraphVizPathOnWindowsUsingNativeAccess();
        	if (path != null) {
        		return path;
        	}
        } catch (Exception e) {
        }

        try {
        	path = getGraphVizPathOnWindowsUsingRegEdit();
        	if (path != null) {
        		return path;
        	}
        } catch (Exception e) {
        }

        
        if (path == null) {
        	path = getGraphVizPathOnWindows();
        }
        
        return path;
    }

    /**
     * You can set the Authentication in DCOM component to "None" , this way no authentication would
     * be required by it. Unfortunately j-Interop does not support this. (We need security to
     * atleast be set to "Connect").
     * 
     * @return
     */
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

    private String getGraphVizPathOnWindowsUsingLuckyCharm()
    {
    	for (String dir : DEFAULT_WINDOWS_PATH) {
    		File file = new File(dir, DEFAULT_FILTER.name() + DEFAULT_WINDOWS_EXTENSION);
    		if (file.exists()) {
    			return dir;
    		}
    	}
    }

    
    private String getGraphVizPathOnLinux()
    {
    	for (String dir : DEFAULT_UNIX_PATH) {
    		File file = new File(dir, DEFAULT_FILTER.name());
    		if (file.exists()) {
    			return dir;
    		}
    	}
    }
	
    private String getGraphVizPathOnMacOS()
    {
    	return getGraphVizPathOnLinux();
    }
    
	public Graphviz()
	{
    	OperationalSystemDetector detector = new OperationalSystemDetector();
    	OperationalSystem os = detector.detectCurrentOS();
    	String path = null;
    	
    	switch (os) {
    		case Windows:
    			path = getGraphVizPathOnWindows();
    			break;
    		case Solaris:
    		case Unix:
    		case Linux:
    			path = getGraphVizPathOnLinux();
    			break;
    		case MacOS:
    			path = getGraphVizPathOnMacOS();
    			break;    			
    	}

    	if (path == null) {
    		throw new UnsupportedOperationException(new FileNotFoundException("Cannot find GraphViz."));
    	}
    	
    	setBinary(new File(path));
	}
	
	public Graphviz(File file)
	{
		setBinary(file);
	}
	
	public void setBinary(File file)
	{
		if (file.exists() && file.isFile() && file.canExecute()) {
			binary = file;
		} else {
			throw new IllegalArgumentException("Invalid file: " + file.getAbsolutePath());
		}
	}
	
	// TODO: escolher executável pelo Layout
	public void layout(Filter filter, String graphDescription, String targetFile) {
	        Runtime command;
	        command = Runtime.getRuntime();
	        try {
	            command.exec(EXEC + " " + FLAGS + " " + dotFile + " -o " + targetFile);
	        } catch (IOException ioe) {
	            System.err.println("Error executing GraphViz:\n" + ioe.getMessage() + "\n");
	            return -1;
	        }
	        return 0;
	    }
	}
}
