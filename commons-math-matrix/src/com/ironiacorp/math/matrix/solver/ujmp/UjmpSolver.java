package com.ironiacorp.math.matrix.solver.ujmp;

import org.ujmp.core.doublematrix.calculation.general.decomposition.Solve;

import com.ironiacorp.math.matrix.solver.Matrix;
import com.ironiacorp.math.matrix.solver.Solver;


public class UjmpSolver<T extends Number> implements Solver<T>
{
	private Solve<org.ujmp.core.Matrix> solver = Solve.MATRIXSQUARELARGEMULTITHREADED;

	private Matrix<T> matrixA;

	private Matrix<T> matrixB;

	@Override
	public Matrix<T> getMatrixA()
	{
		return matrixA;
	}

	@Override
	public void setMatrixA(Matrix<T> matrix)
	{
		this.matrixA = matrix;
	}

	@Override
	public Matrix<T> getMatrixB()
	{
		return matrixB;
	}

	@Override
	public void setMatrixB(Matrix<T> matrix)
	{
		this.matrixB = matrix;
	}

	@Override
	public Matrix<T> solve()
	{
		org.ujmp.core.Matrix result = solver.calc( (org.ujmp.core.Matrix) matrixA.getRealImplementation(), (org.ujmp.core.Matrix) matrixB.getRealImplementation());
		return (Matrix<T>) new UjmpMatrixFloat(result);
	}

}
