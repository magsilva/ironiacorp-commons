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

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.Transformer;
import org.rosuda.REngine.REXP;

import com.ironiacorp.statistics.r.type.DoubleMatrix;
import com.ironiacorp.statistics.r.type.LinearModelSummary;
import com.ironiacorp.statistics.r.type.ObjectMatrix;
import com.ironiacorp.statistics.r.type.OneWayAnovaResult;
import com.ironiacorp.statistics.r.type.TwoWayAnovaResult;

/**
 * Abstraction of a connection to R
 * 
 * @author Paul
 * @version $Id: RClient.java,v 1.2 2010/05/01 02:45:59 paul Exp $
 */
public interface RClient {

    /**
     * @param argName
     * @param arg
     */
    void assign( String argName, double[] arg );

    List<String> stringListEval( String command );

    /**
     * Evaluates two way anova commands of the form
     * <p>
     * apply(matrix,1,function(x){anova(aov(x~farea+ftreat))}
     * </p>
     * and
     * <p>
     * apply(matrix,1,function(x){anova(aov(x~farea+ftreat+farea*ftreat))}
     * </p>
     * where farea and ftreat have already been transposed and had factor called on them.
     * 
     * @param command
     * @return
     */
    Map<String, TwoWayAnovaResult> twoWayAnovaEval(String command, boolean withInteractions);

    Map<String, OneWayAnovaResult> oneWayAnovaEval(String command);


    void assign(String arg0, int[] arg1);

    void assign(String argName, String[] array);

    /**
     * @param strings
     * @return the name of the factor generated.
     */
    String assignFactor(List<String> strings);

    /**
     * @param factorName
     * @param list
     * @return the factor name
     */
    String assignFactor(String factorName, List<String> list);

    /*
     * (non-Javadoc)
     * @see org.rosuda.JRclient.Rconnection#assign(java.lang.String, java.lang.String)
     */
    void assign(String sym, String ct);

    /**
     * Assign a 2-d matrix.
     * 
     * @param matrix
     * @return the name of the variable by which the R matrix can be referred.
     */
    String assignMatrix(DoubleMatrix<?, ?> matrix);

    /**
     * Assign a 2-d matrix.
     * 
     * @param matrix
     * @param rowNameExtractor
     * @return the name of the variable by which the R matrix can be referred.
     */
    String assignMatrix(DoubleMatrix<?, ?> matrix, Transformer rowNameExtractor);

    /**
     * Define a variable corresponding to a character array in the R context, given a List of Strings.
     * 
     * @param objects, which will be stringified if they are not strings.
     * @return the name of the variable in the R context.
     */
    String assignStringList(List<?> objects);

    /**
     * Assign a 2-d matrix.
     * 
     * @param matrix
     * @return the name of the variable by which the R matrix can be referred.
     */
    String assignMatrix(double[][] matrix);

    /**
     * Run a command that takes a double array as an argument and returns a boolean.
     * 
     * @param command
     * @param argName
     * @param arg
     * @return
     */
    boolean booleanDoubleArrayEval(String command, String argName, double[] arg);

    /**
     * Run a command that has a single double array parameter, and returns a double array.
     * 
     * @param command
     * @param argName
     * @param arg
     * @return
     */
    double[] doubleArrayDoubleArrayEval(String command, String argName, double[] arg);

    /**
     * Run a command that returns a double array with no arguments.
     * 
     * @param command
     * @return
     */
    double[] doubleArrayEval(String command);

    /**
     * Run a command that takes two double array arguments and returns a double array.
     * 
     * @param command
     * @param argName
     * @param arg
     * @param argName2
     * @param arg2
     * @return
     */
    double[] doubleArrayTwoDoubleArrayEval(String command, String argName, double[] arg, String argName2, double[] arg2);

    /**
     * Run a command that takes two double arrays as arguments and returns a double value.
     * 
     * @param command
     * @param argName
     * @param arg
     * @param argName2
     * @param arg2
     * @return
     */
    double doubleTwoDoubleArrayEval(String command, String argName, double[] arg, String argName2, double[] arg2);

    /**
     * Evaluate any command and return a string
     * 
     * @param command
     * @return string
     */
    String stringEval(String command);

    int[] intArrayEval(String command);

    boolean loadLibrary(String libraryName);

    /*
     * (non-Javadoc)
     * @see org.rosuda.JRclient.Rconnection#getLastError()
     */
    String getLastError();

    /**
     * Remove a variable from the R namespace
     * 
     * @param variableName
     */
    void remove(String variableName);

    /**
     * Convert an object matrix into an R data frame. Columns that look numeric are treated as numbers. Booleans and
     * Strings are treated as factors.
     * 
     * @param matrix
     * @return variable name in R-land.
     */
    String dataFrame(ObjectMatrix<String, String, Object> matrix);

    /**
     * Evaluate a command that returns a dataFrame
     * 
     * @param command
     * @return an ObjectMatrix representation of the data frame.
     */
    ObjectMatrix<String, String, Object> dataFrameEval(String command);

    /**
     * Get a matrix back out of the R context. Row and Column names are filled in for the resulting object, if they are
     * present.
     * 
     * @param variableName
     * @return
     */
    DoubleMatrix<String, String> retrieveMatrix(String variableName);

    void voidEval(String command);

    boolean connect();
    
    boolean isConnected();

    /**
     * Run lm with anova on all the rows of a matrix
     * 
     * @param dataMatrixVarName from an assignment of a matrix
     * @param modelFormula and other options that will be passed as the argument to 'lm(...)', that refers to factor
     *        variables that have already been assigned, using x as the outcome. Example might be x ~ f1 + f2.
     * @param names of the factors like {"f1", "f2"}.
     * @return map of row identifiers to populated LinearModelSummaries.
     */
    Map<String, LinearModelSummary> rowApplyLinearModel(String dataMatrixVarName, String modelFormula, String[] factorNames);

    /**
     * Lower-level access to two-way ANOVA
     * 
     * @param data
     * @param factor1
     * @param factor2
     * @param includeInteraction
     * @return result with interaction term information null if includeInteraction = false
     */
    TwoWayAnovaResult twoWayAnova(double[] data, List<String> factor1, List<String> factor2, boolean includeInteraction);

    /**
     * Lower level access to linear model. Fairly simple. Factors are assigned in turn.
     * 
     * @param data
     * @param factors Map of factorNames to factors (which can be expressed as Strings or Doubles). If you care about
     *        the order the factors are introduced into the model, use a LinkedHashMap.
     */
    LinearModelSummary linearModel(double[] data, Map<String, List<?>> factors);

    /**
     * @param data
     * @param design which will be converted to factors or continuous covariates depending on whether the columns are
     *        booleans, strings or numerical. Names of factors are the column names of the design matrix, and the rows
     *        are assumed to be in the same order as the data.
     * @return
     */
    LinearModelSummary linearModel(double[] data, ObjectMatrix<String, String, Object> design);

    /**
     * Evaluate the given command
     * 
     * @param command
     * @return
     */
    REXP eval(String command);

    /**
     * Lower-level access to a simple one-way ANOVA
     * 
     * @param data
     * @param factor
     * @return
     */
    OneWayAnovaResult oneWayAnova(double[] data, List<String> factor);

    /**
     * @param listEntryType a hint about what type of object you want the list to contain. If you set this to be null,
     *        the method will try to guess, but caution is advised.
     * @param command R command
     * @return
     */
    List<?> listEval(Class<?> listEntryType, String command);
}