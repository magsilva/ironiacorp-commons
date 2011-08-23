/*
 * Copyright (c) 2011 Marco Aurélio Graciotto Silva <magsilva@ironiacorp.com>
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.ironiacorp.statistics.r;

import java.io.File;

import com.ironiacorp.computer.LibraryLoader;

/**
 * Get a connection to R, somehow (if possible).
 * 
 * @author Paul
 * @version $Id: RConnectionFactory.java,v 1.2 2010/05/26 18:52:54 paul Exp $
 */
public class RConnectionFactory
{
	public static final String DEFAULT_HOSTNAME = "localhost";
	
	private String hostname =  DEFAULT_HOSTNAME;
	
    public String getHostname()
	{
		return hostname;
	}

	public void setHostname(String hostname)
	{
		this.hostname = hostname;
	}


	/**
     * @param hostName The host to use for rserve connections, used only for RServe
     * @return
     */
    public RClient getRConnection()
    {
        RClient rc;
        
        rc = getRServeConnection();
        if (rc == null) {
        	rc = getJRIClient();
        }
        return rc;
    }

	
    /**
     * @param hostName The host to use for rserve connections, used only for RServe
     * @return
     */
    public RClient getRServeConnection()
    {
        RClient rc = null;
        try {
            rc = new RServeClient(hostname);
        } catch (Exception e) {
        	// Try once again if hostname == localhost
        	if (DEFAULT_HOSTNAME.equals(hostname) || hostname == null) {
        		try {
        			RServe rserve = new RServe();
        			rserve.start();
                    rc = new RServeClient(hostname);
        		} catch (Exception e2) {
        		}
        	}
        }
        return rc;
    }

    /**
     * Opens a connection to R using the native library.
     * @return
     */
    public RClient getJRIClient()
    {
    	LibraryLoader loader = new LibraryLoader();
    	String[] javaLibraryPath = System.getProperty("java.library.path").split(File.pathSeparator);
    	
    	loader.addDefaultLibraryPath();
    	loader.addLibraryPath(new File("/usr/lib/R/site-library/rJava/jri/"));
    	for (String path : javaLibraryPath) {
    		loader.addLibraryPath(new File(path));
    	}
    	loader.load("jri");
        RClient j = new JRIClient();
        return j;
    }
}
