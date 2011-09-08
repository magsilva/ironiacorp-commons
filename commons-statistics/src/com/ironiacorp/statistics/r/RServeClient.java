/*
 * The baseCode project
 * 
 * Copyright (c) 2006 University of British Columbia
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

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.rosuda.REngine.REXP;
import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;
import org.rosuda.REngine.RList;
import org.rosuda.REngine.Rserve.RConnection;
import org.rosuda.REngine.Rserve.RserveException;

import com.ironiacorp.statistics.r.type.DenseDoubleMatrix;
import com.ironiacorp.statistics.r.type.DoubleMatrix;

/**
 * @author pavlidis
 * @version $Id: RServeClient.java,v 1.10 2010/05/26 17:51:42 paul Exp $
 */
public class RServeClient extends AbstractRClient
{
	/**
	 * Default hostname to connect to.
	 */
	public static final String DEFAULT_HOSTNAME = "localhost";
	
	/**
     * Default port for RServe.
     */
    public static final int DEFAULT_PORT = 6311;
    
    /**
     * Maximum connections to try before giving up (and throw an exception).
     */
    private static final int MAX_CONNECT_TRIES = 10;

    /**
     * Maximum tries to perform an evaluation (and throw an exception).
     */
    private static final int MAX_EVAL_TRIES = 3;

    /**
     * Hostname to which we are connected to.
     */
	private String hostname = DEFAULT_HOSTNAME;
	
	/**
	 * Default port to connect to.
	 */
	private int port = DEFAULT_PORT;
	
	/**
	 * Connection established to the specified hostname.
	 */
    private RConnection connection = null;

    /**
     * @param host
     * @throws IOException
     */
    protected RServeClient(String host)
    {
    	setHostname(host);
    	connect();
    }

    /**
     * Gets connection on default host (localhost) and port (6311)
     * 
     * @throws IOException
     */
    protected RServeClient() throws IOException
    {
    	connect();
    }

    public String getHostname()
	{
		return hostname;
	}

    /**
     * Defines a different hostname.
     * 
     * @param hostname Hostname or IP address of the R server.
     * 
     * @throws UnsupportedOperationException if it is already connected to a server (must disconnect
     * before configuring a new hostname).
     */
	public void setHostname(String hostname)
	{
		if (isConnected()) {
			throw new UnsupportedOperationException("You must disconnect before configuring a new hostname");
		}
		this.hostname = hostname;
	}
    
	public int getPort()
	{
		return port;
	}

	/**
	 * Define a new port for the R server that this object will connect to.
	 * @param port Port number
	 * 
	 * @throws IllegalArgumentException if it is an invalid port.
     * @throws UnsupportedOperationException if it is already connected to a server (must disconnect
     * before configuring a new port).
	 */
	public void setPort(int port)
	{
		if (isConnected()) {
			throw new UnsupportedOperationException("You must disconnect before configuring a new port address");
		}
		
		if (port < 0 || port > 65535) {
			throw new IllegalArgumentException("Invalid port: " + port);
		}
		this.port = port;
	}

	
    /**
     * Check if it is connected to an R server and, if not, try to establish a
     * connection.
     * 
     * @throws UnsupportedOperationException if cannot connect.
     */
    private void checkConnection()
    {
        if (! isConnected()) {
        	connect();
        }
    }

    /**
     * Check if it is connected (but do not try to connect). 
     */
    public boolean isConnected()
    {
        if (connection != null && connection.isConnected()) {
        	return true;
        }
        return false;
    }

    /**
     * Disconnect from the server.
     */
    public void disconnect()
    {
        if (isConnected()) {
        	// Attempt to release all memory used by this connection.
            voidEval("rm(list=ls())");
            
        	connection.close();
        }
        connection = null;
    }

    @Override
    public void finalize()
    {
        disconnect();
    }
    
    /**
     * Establish a connection to an R server.
     * 
     * @throws UnsupportedOperationException if cannot connect.
     * 
     * @return True if a new connection has established and False if it was
     * already connected.
     */
    public boolean connect()
    {
    	RserveException exception = null;
    	
        if (isConnected()) {
            return false;
        }
        
        for (int i = 0; i < MAX_CONNECT_TRIES && ! isConnected(); i++) {
        	try {
        		Thread.sleep( 200 );
        	} catch (InterruptedException e) {
        	}
        	
            try {
                connection = new RConnection(hostname, port);
            } catch (RserveException e) {
                exception = e;
            }
        }
        
        if (! isConnected()) {
        	throw new IllegalArgumentException("Cannot connect to the R server at " + hostname, exception);
        }
        return true;
    }

	
	/*
     * (non-Javadoc)
     * 
     * @see ubic.basecode.util.RClient#assign(java.lang.String, double[])
     */
    public void assign( String argName, double[] arg )
    {
        checkConnection();

        try {
            connection.assign( argName, arg );
        } catch ( REngineException e ) {
            throw new RuntimeException( e );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see ubic.basecode.util.RClient#assign(java.lang.String, int[])
     */
    public void assign( String arg0, int[] arg1 )
    {
        if ( StringUtils.isBlank( arg0 ) ) {
            throw new IllegalArgumentException( "Must supply valid variable name" );
        }
        checkConnection();
        try {
            connection.assign( arg0, arg1 );
        } catch ( REngineException e ) {
            throw new RuntimeException( e );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.rosuda.JRclient.Rconnection#assign(java.lang.String, java.lang.String)
     */
    /*
     * (non-Javadoc)
     * 
     * @see ubic.basecode.util.RClient#assign(java.lang.String, java.lang.String)
     */
    public void assign( String sym, String ct )
    {
        if ( StringUtils.isBlank( sym ) ) {
            throw new IllegalArgumentException( "Must supply valid variable name" );
        }
        try {
            this.checkConnection();
            connection.assign( sym, ct );
        } catch ( RserveException e ) {
            throw new RuntimeException( "Assignment failed: " + sym + " value " + ct, e );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see ubic.basecode.util.RClient#assign(java.lang.String, java.lang.String[])
     */
    public void assign( String argName, String[] array ) {
        if ( array == null || array.length == 0 ) {
            throw new IllegalArgumentException( "Array must not be null or empty" );
        }
        if ( StringUtils.isBlank( argName ) ) {
            throw new IllegalArgumentException( "Must supply valid variable name" );
        }
        try {
            this.checkConnection();
            connection.assign( argName, array );
        } catch ( REngineException e ) {
            throw new RuntimeException( "Failure with assignment: " + argName + "<-" + array.length + " strings." + e );
        }
    }

    /**
     * Get last error message from R.
     */
    public String getLastError()
    {
        return connection.getLastError();
    }

   
    /*
     * (non-Javadoc)
     * 
     * @see ubic.basecode.util.RClient#retrieveMatrix(java.lang.String)
     */
    public DoubleMatrix<String, String> retrieveMatrix( String variableName )
    {
        try {
            // REXP clr = this.eval( "class(" + variableName + ")" );
            // log.info( clr.asString() );

            // note that for some reason, asDoubleMatrix is returning a 1-d array. So I do this.
            REXP r = this.eval( "data.frame(t(" + variableName + "))" );
            if ( r == null )
            	throw new IllegalArgumentException( variableName + " not found in R context" );

            RList dataframe = r.asList();
            int numrows = dataframe.size();
            double[][] results = new double[numrows][];
            int i = 0;
            for ( Iterator<?> it = dataframe.iterator(); it.hasNext(); ) {
                REXP next = ( REXP ) it.next();
                double[] row = next.asDoubles();
                results[i] = row;
                i++;
            }

            DoubleMatrix<String, String> resultObject = new DenseDoubleMatrix<String, String>( results );
            retrieveRowAndColumnNames( variableName, resultObject );
            return resultObject;
        } catch ( REXPMismatchException e ) {
            throw new RuntimeException( "Failed to get back matrix for variable " + variableName, e );
        }
    }
    

    /**
     * @param variableName
     * @param resultObject
     * @throws REXPMismatchException
     */
    protected void retrieveRowAndColumnNames( String variableName, DoubleMatrix<String, String> resultObject ) {
        List<String> rowNames = stringListEval( "dimnames(" + variableName + ")[1][[1]]" );

        if ( rowNames.size() == resultObject.rows() ) {
            resultObject.setRowNames( rowNames );
        }

        List<String> colNames = this.stringListEval( "dimnames(" + variableName + ")[2][[1]]" );

        if ( colNames.size() == resultObject.columns() ) {
            resultObject.setColumnNames( colNames );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see ubic.basecode.util.RClient#voidEval(java.lang.String)
     */
    public void voidEval( String command )
    {
        if (command == null)
        	throw new IllegalArgumentException( "Null command" );
        this.checkConnection();

        eval( command );

    }

    /*
     * (non-Javadoc)
     * 
     * @see ubic.basecode.util.r.RClient#eval(java.lang.String)
     */
    public REXP eval( String command ) {
        int lockValue = 0;
        try {

            /*
             * Failures due to communication? try repeatedly.
             */
            for ( int i = 0; i < MAX_EVAL_TRIES + 1; i++ ) {
                RuntimeException ex = null;
                try {
                    checkConnection();
                    lockValue = connection.lock();
                    REXP r = connection.eval( "try(" + command + ", silent=T)" );
                    if ( r == null ) return null;

                    if ( r.inherits( "try-error" ) ) {
                        /*
                         * This is not an eval error that would warrant a retry.
                         */
                        throw new RuntimeException( "Error from R: " + r.asString() );
                    }
                    return r;

                } catch ( RserveException e ) {
                    ex = new RuntimeException( "Error excecuting " + command + ": " + e.getMessage(), e );
                } catch ( REXPMismatchException e ) {
                    throw new RuntimeException( "Error processing apparent error object returned by " + command + ": "  + e.getMessage(), e );
                }

                if ( i == MAX_EVAL_TRIES ) {
                    throw ex;
                }

                try {
                    Thread.sleep( 200 );
                } catch ( InterruptedException e ) {
                    return null;
                }

            }

            throw new RuntimeException( "Evaluation failed! No details available" );
        } finally {
            if ( lockValue != 0 ) connection.unlock( lockValue );
        }
    }
}
