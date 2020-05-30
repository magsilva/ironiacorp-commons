/*
 * The baseCode project
 * 
 * Copyright (c) 2008 University of British Columbia
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
 
import java.util.ArrayList;
import java.util.List;


import org.rosuda.REngine.REXPMismatchException;
import org.rosuda.REngine.REngineException;
// import org.rosuda.REngine.REXP;

import org.rosuda.JRI.REXP;
import org.rosuda.JRI.Rengine;
import org.rosuda.JRI.RList;

import com.ironiacorp.statistics.r.type.DoubleMatrix;
import com.ironiacorp.statistics.r.type.DoubleMatrixFactory;


/**
 * R connection implementation that uses the dynamic library interface
 * JRI (http://www.rforge.net/rJava/).
 * 
 * @author paul
 * @version $Id: JRIClient.java,v 1.3 2010/05/26 18:52:54 paul Exp $
 * @see RClientFactory
 */
public class JRIClient extends AbstractRClient
{
    private static Rengine connection = null;

    @Override
    public boolean connect()
    {
    	if (! isConnected()) {
               connection = new Rengine();
        	return true;
    	}

    	return false;
    }
    
    @Override
    public boolean isConnected()
    {
    	return (connection != null);
    }

    
    @Override
    public void assign(String argName, double[] arg)
    {
            connection.assign( argName, arg );
    }

    @Override
    public void assign(String arg0, int[] arg1)
    {
            connection.assign( arg0, arg1 );
    }

    @Override
    public void assign(String sym, String ct)
    {
            connection.assign( sym, ct );
    }

    @Override
    public void assign(String argName, String[] array)
    {
            connection.assign( argName, array );
    }

    public String getLastError()
    {
        return "Sorry, no information";
    }

    public void voidEval(String command)
    {
        eval( command );
    }

    public org.rosuda.REngine.REXP eval(String command)
    {
        REXP result;
        int key = connection.rniRJavaLock();
        try {

            result = connection.eval( "try(" + command + ", silent=T)" );

            if ( result == null ) {
                throw new RuntimeException( "Error from R, could not sucessfully evaluate: " + command );
            }

	    /*
            if ( !result.isString() ) {
                return result;
            }
	    */
            String a = result.asString();

            /*
             * There is no better way to do this, apparently.
             */
            if ( a != null && a.startsWith( "Error" ) ) {
                throw new RuntimeException( "Error from R when running " + command + ": " + a );
            }

            // return result; // TODO: Create REXP from REngine using REXP from JRI
	    return null;
        } finally {
//            connection.rniRJavaUnlock( key );
            connection.rniRJavaUnlock();
        }
    }

    @Override
    public DoubleMatrix<String, String> retrieveMatrix( String variableName ) {
        org.rosuda.REngine.REXP r = eval( variableName );
        if (r == null)
        	throw new IllegalArgumentException( variableName + " not found in R context" );

        double[][] results;
        try {
            results = r.asDoubleMatrix();
        } catch ( REXPMismatchException e ) {
            throw new RuntimeException( e );
        }

        if (results == null)
        	throw new RuntimeException( "Failed to get back matrix for variable " + variableName );

        DoubleMatrix<String, String> resultObject = DoubleMatrixFactory.dense( results );

        retrieveRowAndColumnNames( variableName, resultObject );
        return resultObject;

    }

    @Override
    protected void retrieveRowAndColumnNames( String variableName, DoubleMatrix<String, String> resultObject ) {
	Object rtemp = eval( "dimnames(" + variableName + ")" );
	REXP r1;
	if (rtemp instanceof REXP) {
		r1 = (REXP) rtemp;
	} else {
		return;
	}

        RList asList;
        asList = r1.asList();
        if ( asList == null ) {
            return;
        }

        REXP rowNamesREXP = asList.at( 0 );
        REXP colNamesREXP = asList.at( 1 );

        if ( rowNamesREXP != null ) {
            String[] rowNamesAr;
            rowNamesAr = rowNamesREXP.asStringArray();
            List<String> rowNames = new ArrayList<String>();
            for ( String rowName : rowNamesAr ) {
                rowNames.add( rowName );
            }
            resultObject.setRowNames( rowNames );
        }

        // Getting the column names.
        if ( colNamesREXP != null ) {
            String[] colNamesAr;
            colNamesAr = colNamesREXP.asStringArray();
            List<String> colNames = new ArrayList<String>();
            for ( String colName : colNamesAr ) {
                colNames.add( colName );
            }
            resultObject.setColumnNames( colNames );
        }
    }
}
