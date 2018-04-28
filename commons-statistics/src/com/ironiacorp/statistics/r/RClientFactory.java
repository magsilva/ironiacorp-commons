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

import com.ironiacorp.computer.ComputerSystem;
import com.ironiacorp.computer.OperationalSystem;

/**
 * Get a connection to R, somehow (if possible).
 * 
 * @author Paul
 * @version $Id: RConnectionFactory.java,v 1.2 2010/05/26 18:52:54 paul Exp $
 */
public class RClientFactory
{
	/**
     * @param hostName The host to use for rserve connections, used only for RServe
     * @return
     */
    public RClient getClient()
    {
        RClient rc;
        
        try {
	        // Most reports on the Internet states that RServe is more reliable
	        // than JRI, thus we try the former first.
	        rc = getRServeClient();
	        if (rc == null) {
	        	rc = getJRIClient();
	        }
        } catch (Exception e) {
        	rc = null;
        }
        
        return rc;
    }

	
    /**
     * @param hostName The host to use for rserve connections, used only for RServe
     * @return
     */
    public RClient getRServeClient()
    {
    	RServeClient rc = null;
        try {
        	rc = new RServeClient();
        	rc.connect();
        } catch (Exception e) {
        	// Try once again, now starting our own RServe
       		try {
       			OperationalSystem os = ComputerSystem.getCurrentOperationalSystem();
			os.addExecutableSearchPath(new File("/usr/lib/R/site-library/Rserve/R/Rserve"); // Debian 
			os.addExecutableSearchPath(new File("/usr/lib/R/site-library/Rserve")); // Fedora
       			os.addExecutableSearchPath(new File("/usr/lib64/R/library/Rserve"));
       			os.addExecutableSearchPath(new File("/usr/lib/R/site-library/Rserve"));
       			os.addExecutableSearchPath(new File("/usr/lib64/R/library/Rserve"));
       			RServe rserve = new RServe();
       			rserve.start();
       			rc = new RServeClient();
       			rc.connect();
       		} catch (Exception e2) {
       			rc = null;
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
    	OperationalSystem os = ComputerSystem.getCurrentOperationalSystem();
    	File library;
    	
    	os.addLibrarySearchPath(new File("/usr/lib/R/site-library/rJava/jri/")); // Debian and Fedora
    	os.addLibrarySearchPath(new File("/usr/lib/R/library/rJava/jri/"));
    	os.addLibrarySearchPath(new File("/usr/lib64/R/site-library/rJava/jri/"));
    	os.addLibrarySearchPath(new File("/usr/lib64/R/library/rJava/jri/"));
    	library = os.findLibrary("jri");
	try {
	    	os.loadLibrary(library);
	} catch (Exception e) {
		throw new UnsupportedOperationException("Could not load library for JRI");
	}
        RClient j = new JRIClient();
        
        return j;
    }
}
