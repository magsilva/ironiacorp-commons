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
public class RServeClient extends AbstractRClient {

    /**
     * 
     */
    private static final int DEFAULT_PORT = 6311;

    private static final int MAX_CONNECT_TRIES = 10;

    private static final int MAX_EVAL_TRIES = 3;

    private RConnection connection = null;

    /**
     * @param host
     * @throws IOException
     */
    protected RServeClient( String host ) throws IOException {
        if ( !connect( host, DEFAULT_PORT ) ) {
            throw new IOException( "Could not connect to Rserve" );
        }
    }

    /**
     * Gets connection on default host (localhost) and port (6311)
     * 
     * @throws IOException
     */
    protected RServeClient() throws IOException {
        if ( !connect() ) {
            throw new IOException( "Could not connect to Rserve" );
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see ubic.basecode.util.RClient#assign(java.lang.String, double[])
     */
    public void assign( String argName, double[] arg ) {
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
    public void assign( String arg0, int[] arg1 ) {
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
    public void assign( String sym, String ct ) {
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
     * 
     *
     */
    public boolean connect() {
        return connect( true );
    }

    public void disconnect() {
        if ( connection != null && connection.isConnected() ) connection.close();
        connection = null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#finalize()
     */
    @Override
    public void finalize() {
        this.disconnect();
    }

    /*
     * (non-Javadoc)
     * 
     * @see ubic.basecode.util.RClient#getLastError()
     */
    public String getLastError() {
        return connection.getLastError();
    }

    /**
     * 
     */
    public boolean isConnected() {
        if ( connection != null && connection.isConnected() ) return true;
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ubic.basecode.util.RClient#retrieveMatrix(java.lang.String)
     */
    public DoubleMatrix<String, String> retrieveMatrix( String variableName ) {
        try {
            // REXP clr = this.eval( "class(" + variableName + ")" );
            // log.info( clr.asString() );

            // note that for some reason, asDoubleMatrix is returning a 1-d array. So I do this.
            REXP r = this.eval( "data.frame(t(" + variableName + "))" );
            if ( r == null ) throw new IllegalArgumentException( variableName + " not found in R context" );

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

    /*
     * (non-Javadoc)
     * 
     * @see ubic.basecode.util.RClient#voidEval(java.lang.String)
     */
    public void voidEval( String command ) {
        if (command == null)
        	throw new IllegalArgumentException( "Null command" );
        this.checkConnection();

        eval( command );

    }

    /**
     * 
     */
    private void checkConnection() {
        if (!this.isConnected() ) {

            /*
             * This often won't work. Even if we reconnect, state will have been lost. However, under normal
             * circumstances (over a local network) connection loss should be very rare.
             */
            boolean ok = false;
            for ( int i = 0; i < MAX_CONNECT_TRIES; i++ ) {
                try {
                    Thread.sleep( 200 );
                } catch ( InterruptedException e ) {
                    return;
                }
                ok = this.connect();
                if ( ok ) break;
            }
            if ( !ok ) {
                throw new RuntimeException( "Not connected" );
            }
        }
    }

    /**
     * @param host
     * @param port
     * @return
     */
    private boolean connect( String host, int port ) {
        if ( connection != null && connection.isConnected() ) {
            return true;
        }
        try {
            connection = new RConnection( host, port );
        } catch ( RserveException e ) {
            return false;
        }
        return true;
    }

    /**
     * @param beQuiet
     */
    private boolean connect( boolean beQuiet ) {
        if ( connection != null && connection.isConnected() ) {
            return true;
        }
        int tries = 3;
        for ( int i = 0; i < tries; i++ ) {
            try {
                connection = new RConnection();
                return true;
            } catch ( RserveException e ) {
                try {
                    Thread.sleep( 100 );
                } catch ( InterruptedException e1 ) {
                    return false;
                }
            }
        }
        return false;
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

    /**
     * @param variableName
     * @param resultObject
     * @throws REXPMismatchException
     */
    private void retrieveRowAndColumnNames( String variableName, DoubleMatrix<String, String> resultObject ) {
        List<String> rowNames = this.stringListEval( "dimnames(" + variableName + ")[1][[1]]" );

        if ( rowNames.size() == resultObject.rows() ) {
            resultObject.setRowNames( rowNames );
        }

        List<String> colNames = this.stringListEval( "dimnames(" + variableName + ")[2][[1]]" );

        if ( colNames.size() == resultObject.columns() ) {
            resultObject.setColumnNames( colNames );
        }
    }

}
